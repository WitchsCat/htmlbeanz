package org.funcode.htmlbeans.wrappers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 12.11.12
 * Time: 19:19
 * To change this template use File | Settings | File Templates.
 */
public class TestObjectWrapperDoReverse extends TestObjectWrapperBase {

    @Test
    public void testNull() throws ClassNotFoundException {
        assertNull("Null input didn't result to null output", wrapper.doReverse(null));
    }

    @Test
    public void testClazzAttributeWrapping() throws ClassNotFoundException {
        Object result = wrapper.doReverse(colorClazzAttribute);
        assertNotNull("A real object applicable for unwrapping resulted into a null", result);
        assertEquals("A string parameter 'color', didn't result into original string", result, house.getColor());
    }
    @Test
    public void testCreatingANewObject() throws ClassNotFoundException {
        Object result = wrapper.doReverse(simpleDimensionClazz);
        assertNotNull("A real wrapper object resulted into a null result",result);

    }


}
