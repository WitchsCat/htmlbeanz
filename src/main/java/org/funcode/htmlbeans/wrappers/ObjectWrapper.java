package org.funcode.htmlbeans.wrappers;

import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        classToPrimitiveType = new HashMap<>();
        classToPrimitiveType.put(Integer.class, ElementType.INTEGER);
        classToPrimitiveType.put(Long.class, ElementType.LONG);
        classToPrimitiveType.put(Float.class, ElementType.FLOAT);
        classToPrimitiveType.put(Double.class, ElementType.DOUBLE);
        classToPrimitiveType.put(String.class, ElementType.STRING);
        classToPrimitiveType.put(Boolean.class, ElementType.BOOLEAN);
        classToPrimitiveType.put(Byte.class, ElementType.BYTE);
    }

    static {
        primitiveTypeToClass = new HashMap<>();
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
                    Element element = doGood(resultElement);
                    element.setFieldName("element_" + i++);
                    ((ClazzList) result).getElements().add(element);
                }
            }
        } else {
            result = new Clazz();
            result.setType(ElementType.COMPLEX);
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers())) {
                    Object fieldValue;
                    /*
                    To get the empty fields also included in the structure we, we have to avoid calling
                    field.get(null). This will cause a null pointer exception
                     */
                    if (object != null) {
                        fieldValue = field.get(object);
                    } else {
                        fieldValue = null;
                    }
                    Class fieldType;

                    if (field.getType().isPrimitive()) {
                        fieldType = ClassUtils.primitiveToWrapper(field.getType());
                    } else {
                        fieldType = field.getType();
                    }
                    Element justCreatedElement = doGood(fieldValue, fieldType);
                    justCreatedElement.setFieldName(field.getName());
                    ((Clazz) result).getAttributes().add(justCreatedElement);
                }
            }

        }
        result.setOriginalClass(objectClass.getName());
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
        if (element == null) {
            return null;
        }

        Object result = null;
        if (primitiveTypeToClass.get(element.getType()) != null) {
            ClazzAttribute elementAsClazzAttribute = (ClazzAttribute) element;
            result = elementAsClazzAttribute.getOriginalValue();
        } else if (element.getType().equals(ElementType.COMPLEX)) {
            Class<?> originalClass = Class.forName(element.getOriginalClass());
            result = originalClass.newInstance();
            for (Element attribute : ((Clazz) element).getAttributes()) {
                Field field = originalClass.getField(attribute.getFieldName());
                field.set(result, doReverse(attribute));
            }
        }
        return result;
    }

}
