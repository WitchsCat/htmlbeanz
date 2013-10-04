package org.funcode.htmlbeans.wrappers;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestObjectWrapperDoGood extends TestObjectWrapperBase {

    @Test
    public void testInputNullResultsToNull() throws IllegalArgumentException,
            IllegalAccessException {
        assertNull("result is not null", wrapper.doGood(null));
    }

    @Test
    public void testClazzAttributeWrapping() throws IllegalArgumentException,
            IllegalAccessException {
        ClazzAttribute result = (ClazzAttribute) wrapper
                .doGood("stringToWrapp");
        assertNotNull("result is null", result);
        assertEquals("type class is wrong", result.getType(),
                ElementType.STRING);
        assertNull("field name not null", result.getFieldName());
    }

    @Test
    public void testSimpleClazzWrapping() throws IllegalArgumentException,
            IllegalAccessException {
        Element result = wrapper.doGood(house.getDimension());

        assertNotNull("List of attributes is null", result);
        assertTrue("istance object not correct", result instanceof Clazz);
        Clazz resultClazz = (Clazz) result;

        assertTrue("do not contains 1 attribute", resultClazz.getAttributes()
                .contains(simpleDimensionClazz.getAttributes().get(0)));
        assertTrue("do not contains 2 attribute", resultClazz.getAttributes()
                .contains(simpleDimensionClazz.getAttributes().get(1)));
        assertEquals("clazz type is not COMPLEX", resultClazz.getType(),
                ElementType.COMPLEX);
        assertTrue("has invalid attributes size", resultClazz.getAttributes()
                .size() == 2);
        assertEquals("Referential dimension clazz doesn't match the result", simpleDimensionClazz, resultClazz);
    }

    @Test
    public void testClazzWithListWrapping() throws IllegalArgumentException,
            IllegalAccessException {
        Element result = wrapper.doGood(house);

        assertNotNull("Result of wrapping is null", result);
        assertTrue("instance object not correct", result instanceof Clazz);
        Clazz resultClazz = (Clazz) result;
        assertEquals("Referential manually created object and the wrapping result aren't equal", houseClazz, resultClazz);
    }

    @Test
    public void testClazzListWrapping() throws IllegalArgumentException,
            IllegalAccessException {
        Element result = wrapper.doGood(house.getStages());

        assertNotNull("Result of wrapping is null", result);
        assertTrue("instance object not correct", result instanceof ClazzList);
        ClazzList resultClazzList = (ClazzList) result;
        assertNull("ClazzList elements generic class is not null", resultClazzList.getElementsGenericClass());
        assertNull("ClazzList field name is not null", resultClazzList.getFieldName());
        assertEquals("ClazzList elements are not equal", resultClazzList.getElements(),
                ((ClazzList) houseClazz.getAttributes().get(2)).getElements());
    }

}
