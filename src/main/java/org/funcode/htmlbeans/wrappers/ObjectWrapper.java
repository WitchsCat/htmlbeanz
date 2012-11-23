package org.funcode.htmlbeans.wrappers;

import com.thoughtworks.xstream.core.util.Fields;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.*;
import java.util.*;

/**
 * This class is responsible for creation of wrapper objects, over the cached
 * objects that later on can be used to perform presentation on the UI.
 *
 * @author dmarkin, matveev
 */

public class ObjectWrapper {

    public static final Map<Class<?>, ElementType> classToPrimitiveType;

    public static final Map<ElementType, Class<?>> primitiveTypeToClass;

    static {
        classToPrimitiveType = new HashMap<Class<?>, ElementType>();
        classToPrimitiveType.put(Integer.class, ElementType.INTEGER);
        classToPrimitiveType.put(Long.class, ElementType.LONG);
        classToPrimitiveType.put(Float.class, ElementType.FLOAT);
        classToPrimitiveType.put(Double.class, ElementType.DOUBLE);
        classToPrimitiveType.put(String.class, ElementType.STRING);
        classToPrimitiveType.put(Boolean.class, ElementType.BOOLEAN);
        classToPrimitiveType.put(Byte.class, ElementType.BYTE);
    }

    static {
        primitiveTypeToClass = new HashMap<ElementType, Class<?>>();
        for (Class classKey : classToPrimitiveType.keySet()) {
            primitiveTypeToClass.put(classToPrimitiveType.get(classKey), classKey);
        }
    }

    public Element doGood(Object object) throws IllegalArgumentException,
            IllegalAccessException {
        if (object == null) {
            return null;
        } else {
            return doGood(object, object.getClass(), null, null);
        }
    }

    /**
     * this method performs a recursive walk through the given object and maps
     * it to a user interface wrapper
     *
     * @param object if null will cause an IllegalArgumentExcepion
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public Element doGood(Object object, Class objectClass, Class parentClass, String parentFieldName) throws IllegalArgumentException,
            IllegalAccessException {

        Element result;
        if (classToPrimitiveType.get(objectClass) != null) {
            result = new ClazzAttribute();
            result.setType(classToPrimitiveType.get(objectClass));
            if (object != null) {
                ((ClazzAttribute) result).setOriginalValue(object);
            }
        } else if (List.class.isAssignableFrom(objectClass)) {
            result = new ClazzList();
            result.setType(ElementType.LIST);
            // getting generic class of list elements via reflection it's parent holder
            String getterName = "get" + parentFieldName.substring(0, 1).toUpperCase()
                    + parentFieldName.substring(1, parentFieldName.length());
            try {
                Method getterMethod = parentClass.getMethod(getterName);
                Type genericReturnType = getterMethod.getGenericReturnType();
                Class genericClass = null;
                if (genericReturnType instanceof ParameterizedType) {
                    genericClass = (Class) ((ParameterizedType)genericReturnType).getActualTypeArguments()[0];
                }
                ((ClazzList) result).setElementsGenericClass(genericClass.getName());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (object != null) {
                long i = 0;
                for (Object resultElement : ((List<Object>) object)) {

                    Element element = doGood(resultElement, resultElement.getClass(), null, null);
                    element.setFieldName("element_" + i++);
                    ((ClazzList) result).getElements().add(element);
                }
            }
        } else {
            result = new Clazz();
            result.setType(ElementType.COMPLEX);
            List<Field> fields = new ArrayList<Field>();
            Class recursiveObject = objectClass;
            while (!(recursiveObject.getName().equals("java.lang.Object"))) {
                fields.addAll(Arrays.asList(recursiveObject.getDeclaredFields()));
                recursiveObject = recursiveObject.getSuperclass();
            }
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object fieldValue;

                    if (object != null) {
                        fieldValue = field.get(object);
                    } else {
                        fieldValue = null;
                    }

                    Class fieldType;

                    if (fieldValue == null && field.getType().isPrimitive()) {
                        fieldType = ClassUtils.primitiveToWrapper(field.getType());
                    } else if (fieldValue == null && !field.getType().isPrimitive()) {
                        fieldType = field.getType();
                    } else {
                        fieldType = fieldValue.getClass();
                    }
                    Element justCreatedElement = doGood(fieldValue, fieldType, objectClass, field.getName());
                    justCreatedElement.setFieldName(field.getName());
                    ((Clazz) result).getAttributes().add(justCreatedElement);
                }
            }

        }
        result.setOriginalClass(objectClass.getName());
        if (object == null) {
            result.setEmpty(true);
        }
        return result;

    }

    /**
     * This method is supposed to create an unwrapped object, from the given wrapper structure
     *
     * @param element wrapping element that will serve as the source of the unwrapping algorithm
     * @return Resulting object of the initial class type.
     */
    public Object doReverse(Element element) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {

        return doReverse(element, null);
    }

    public Object doReverse(Element element, Object objectToUpdate) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchFieldException {

        if (element == null || element.isEmpty) {
            return null;
        }

        Object result = objectToUpdate;
        if (primitiveTypeToClass.get(element.getType()) != null) {
            ClazzAttribute elementAsClazzAttribute = (ClazzAttribute) element;
            result = elementAsClazzAttribute.getOriginalValue();
        } else if (element.getType().equals(ElementType.COMPLEX)) {
            Class<?> originalClass = Class.forName(element.getOriginalClass());
            if (result == null) {
                result = originalClass.newInstance();
            }
            for (Element attribute : ((Clazz) element).getAttributes()) {
                Field field = originalClass.getDeclaredField(attribute.getFieldName());
                field.setAccessible(true);
                if (objectToUpdate != null) {
                    field.set(result, doReverse(attribute, field.get(objectToUpdate)));
                } else {
                    field.set(result, doReverse(attribute));
                }
            }
        } else if (element.getType().equals(ElementType.LIST)) {
            Class<?> originalClass = Class.forName(element.getOriginalClass());
            if (result == null) {
                result = originalClass.newInstance();
                for (Element listElement : ((ClazzList) element).getElements()) {
                    ((Collection) result).add(doReverse(listElement));
                }
            } else {
                //TODO don't know what to do if the new elements aren't last in the list :(
                ArrayList tempResult = new ArrayList();
                Iterator<Element> elementsIterator = ((ClazzList) element).getElements().iterator();
                Iterator<Object> originalObjects = ((Collection) result).iterator();
                while (elementsIterator.hasNext()) {
                    if (originalObjects.hasNext()) {
                        Object nextOriginalObject = originalObjects.next();
                        Element nextElement = elementsIterator.next();
                        if (!nextElement.isEmpty()) {
                            tempResult.add(doReverse(nextElement, nextOriginalObject));
                        }
                    } else if (elementsIterator.hasNext()) {
                        tempResult.add(doReverse(elementsIterator.next()));
                    }
                }
                ((Collection) result).clear();
                ((Collection) result).addAll(tempResult);
            }


        }
        return result;

    }

}

