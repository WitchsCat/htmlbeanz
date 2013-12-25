package org.funcode.htmlbeans.wrappers;

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

    // private static final Map<Class, Element> wrapperCache = new HashMap<Class, Element>();

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
            return doGood(object, object.getClass());
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
    public Element doGood(Object object, Class objectClass) throws IllegalArgumentException,
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
            if (object != null) {
                long i = 0;
                for (Object resultElement : ((List<Object>) object)) {
                    Element element = doGood(resultElement, resultElement.getClass());
                    element.setFieldName("element_" + i++);
                    if (((ClazzList) result).getElements() == null) {
                        ((ClazzList) result).setElements(new ArrayList<Element>());
                    }
                    ((ClazzList) result).getElements().add(element);
                }
            }
        } else if (Map.class.isAssignableFrom(objectClass)) {
            result = new ClazzMap();
            result.setType(ElementType.MAP);
            if (object != null) {
                long i = 0;
                Map<Object, Object> objectMap = (Map<Object, Object>) object;
                for (Object key : objectMap.keySet()) {
                    Element keyElement = doGood(key, key.getClass());
                    Element valueElement = doGood(objectMap.get(key), objectMap.get(key).getClass());

                    keyElement.setFieldName("key_" + i);
                    valueElement.setFieldName("value_" + i++);
                    if (((ClazzMap) result).getMapElements() == null) {
                        ((ClazzMap) result).setMapElements(new HashMap<Element, Element>());
                    }
                    ((ClazzMap) result).getMapElements().put(keyElement, valueElement);
                }
            }
        } else {
            result = new Clazz();
            result.setType(ElementType.COMPLEX);
            List<Field> fields = new ArrayList<Field>();
            Class recursiveObject = objectClass;
            while (!(recursiveObject.getName().equals("java.lang.Object"))
                    && !(recursiveObject.getName().equals("java.util.Calendar"))) {
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
                    Element justCreatedElement = doGood(fieldValue, fieldType);
                    // sets the generic <> for List elements
                    if (justCreatedElement.getType() == ElementType.LIST) {
                        Type fieldGenericType = field.getGenericType();
                        if (fieldGenericType instanceof ParameterizedType) {
                            String elementsGenericClassString = ((ParameterizedType) fieldGenericType)
                                    .getActualTypeArguments()[0].toString().split(" ")[1];
                            ((ClazzList) justCreatedElement).setElementsGenericClass(elementsGenericClassString);
                        }
                    }
                    // sets the generic <> for Map key and value if exist
                    if (justCreatedElement.getType() == ElementType.MAP) {
                        Type fieldGenericType = field.getGenericType();
                        if (fieldGenericType instanceof ParameterizedType) {
                            String keyGenericClassString = ((ParameterizedType) fieldGenericType)
                                    .getActualTypeArguments()[0].toString().split(" ")[1];
                            String valueGenericClassString = ((ParameterizedType) fieldGenericType)
                                    .getActualTypeArguments()[1].toString().split(" ")[1];
                            ((ClazzMap) justCreatedElement).setKeyGenericClass(keyGenericClassString);
                            ((ClazzMap) justCreatedElement).setValueGenericClass(valueGenericClassString);
                        }
                    }
                    justCreatedElement.setFieldName(field.getName());
                    if (((Clazz) result).getAttributes() == null) {
                        ((Clazz) result).setAttributes(new ArrayList<Element>());
                    }
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
            if (((Clazz) element).getAttributes() != null) {
                for (Element attribute : ((Clazz) element).getAttributes()) {
                    Field field = null;
                    Class recursiveObject = result.getClass();
                    while (!(recursiveObject.getName().equals("java.lang.Object"))
                            && !(recursiveObject.getName().equals("java.util.Calendar"))) {
                        for (Field recursiveField : recursiveObject.getDeclaredFields()) {
                            if (recursiveField.getName().equals(attribute.getFieldName())) {
                                field = recursiveField;
                                break;
                            }
                        }
                        if (field != null) break;
                        recursiveObject = recursiveObject.getSuperclass();
                    }
                    field.setAccessible(true);
                    if (objectToUpdate != null) {
                        field.set(result, doReverse(attribute, field.get(objectToUpdate)));
                    } else {
                        field.set(result, doReverse(attribute));
                    }
                }
            }
        } else if (element.getType().equals(ElementType.LIST)) {
            Class<?> originalClass = Class.forName(element.getOriginalClass());
            if (result == null) {
                result = originalClass.newInstance();
                if (((ClazzList) element).getElements() != null) {
                    for (Element listElement : ((ClazzList) element).getElements()) {
                        ((Collection) result).add(doReverse(listElement));
                    }
                }
            } else {
                //TODO don't know what to do if the new elements aren't last in the list :(
                ArrayList<Object> tempResult = new ArrayList<Object>();
                if (((ClazzList) element).getElements() != null) {
                    Iterator<Element> elementsIterator = ((ClazzList) element).getElements().iterator();
                    Iterator<Object> originalObjects = ((Collection<Object>) result).iterator();
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


        }
        return result;

    }

}

