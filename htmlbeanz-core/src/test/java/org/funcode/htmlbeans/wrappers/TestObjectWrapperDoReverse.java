package org.funcode.htmlbeans.wrappers;

import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.samples.foundation.House;
import org.junit.Ignore;
import org.junit.Test;

import java.awt.*;

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
    public void testIsEmptyNull() throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        houseClazz.setEmpty(true);
        assertNull("wrapper marked isEmpty should result null", wrapper.doReverse(houseClazz));
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
    public void testUpdatingAnExistingObject() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        XStream xStream = new XStream();
        Dimension input = (Dimension) xStream.fromXML(xStream.toXML(house.getDimension()));
        input.height = -1;
        Object result = wrapper.doReverse(simpleDimensionClazz, input);
        assertNotNull("result of mapping from Clazz to object shouldn't be null", result);
        assertEquals("Changes in the wrapper are not reflected to the re instantiated object", result, house.getDimension());
    }

    @Test
    public void testCreatingANewList() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        Object result = wrapper.doReverse(simpleClazzList);
        assertNotNull("A real wrapper object resulted into a null result", result);
        assertEquals("reconstructed object isn't equal to the original one", house.getStages(), result);
    }

    @Test
    public void testUpdatingAComplexObject() throws ClassNotFoundException,
            NoSuchFieldException, InstantiationException, IllegalAccessException {
        XStream xStream = new XStream();
        House input = (House) xStream.fromXML(xStream.toXML(house));
        input.getStages().get(1).getRooms().remove(1);
        input.getDimension().height = -1;
        Object result = wrapper.doReverse(houseClazz,input);
        assertEquals("A complex object wasn't updated as intended",house,result);

    }

    //TODO write a lot of tests to see how it will work with collections in different variants.

}
