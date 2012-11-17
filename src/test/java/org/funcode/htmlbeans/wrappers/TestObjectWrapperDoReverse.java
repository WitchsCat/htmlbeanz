package org.funcode.htmlbeans.wrappers;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This tests the reconstruction of the original object, by the @ObjectWrapper
 * User: dmarkin
 * Date: 12.11.12
 * Time: 19:19
 */
public class TestObjectWrapperDoReverse extends TestObjectWrapperBase {

    @Test
    public void testNull() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        assertNull("Null input didn't result to null output", wrapper.doReverse(null));
    }

    @Test
    public void testClazzAttributeWrapping() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object result = wrapper.doReverse(colorClazzAttribute);
        assertNotNull("A real object applicable for unwrapping resulted into a null", result);
        assertEquals("A string parameter 'color', didn't result into original string", result, house.getColor());
    }

    @Test
    public void testCreatingANewObject() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object result = wrapper.doReverse(simpleDimensionClazz);
        assertNotNull("A real wrapper object resulted into a null result", result);
        assertEquals("reconstructed object isn't equal to the original one", result, house.getDimension());
    }

    @Test
    @Ignore("Don't forget to write an implementation")
    public void testUpdatingAnExistingObject() {

    }

    @Test
    public void testCreatingANewList() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object result = wrapper.doReverse(simpleClazzList);
        assertNotNull("A real wrapper object resulted into a null result", result);
        assertEquals("reconstructed object isn't equal to the original one", house.getStages(), result);
    }

}
