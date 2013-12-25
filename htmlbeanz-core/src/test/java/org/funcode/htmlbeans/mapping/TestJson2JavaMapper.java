/**
 *
 */
package org.funcode.htmlbeans.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.Dimension;

import com.thoughtworks.xstream.XStream;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import org.funcode.htmlbeans.samples.foundation.House;
import org.funcode.htmlbeans.wrappers.Clazz;
import org.funcode.htmlbeans.wrappers.Element;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;

/**
 * @author dmarkin
 */
public class TestJson2JavaMapper {

    private Json2JavaMapper mapper;
    private ObjectWrapper objectWrapper;
    private Gson gson;
    private House house;

    /**
     * Test method for
     * {@link org.funcode.htmlbeans.mapping.Json2JavaMapper#json2Java(java.lang.String)}
     * .
     */
    @Test
    public void testJson2JavaInputOutputNull() {
        assertNull("result isn't null for null input", mapper.json2Java(null));
        assertEquals("result isn't an empty Clazz for an empty string", mapper.json2Java(""), new Clazz());
    }

    @Test
    public void testJson2JavaDesrializeClazz() throws IllegalArgumentException,
            IllegalAccessException {
        gson = new Gson();
        Clazz originalDimensionClazz = (Clazz) objectWrapper
                .doGood(new Dimension(100, 100));
        Element temp = mapper.json2Java(gson.toJson(originalDimensionClazz));
        Clazz result = (Clazz) temp;

        assertEquals(
                "original dimension wrapper and reconstructed one are different",
                result, originalDimensionClazz);

    }

    @Test
    public void testJson2JavaDeserializeClazzList() throws IllegalArgumentException,
            IllegalAccessException {


        Clazz originalHouseClazz = (Clazz) objectWrapper
                .doGood(house);
        Element temp = mapper.json2Java(gson.toJson(originalHouseClazz));
        Clazz result = (Clazz) temp;

        assertEquals(
                "original clazz wrapper and reconstructed one are different",
                result, originalHouseClazz);

    }

    @Before
    public void init() {
        mapper = new Json2JavaMapper();
        objectWrapper = new ObjectWrapper();
        gson = new Gson();
        house = (House) new XStream().fromXML(this.getClass().getResourceAsStream("/house.init.xml"));
    }

}
