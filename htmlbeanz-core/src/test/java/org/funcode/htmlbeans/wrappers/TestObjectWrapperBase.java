package org.funcode.htmlbeans.wrappers;

import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.samples.foundation.House;
import org.junit.Before;

/**
 * Base class for test that are done with the @ObjectWrapper
 * Primarily is used for initialization of resources used in tests
 * User: dmarkin
 * Date: 01.11.12
 * Time: 17:31
 */
public abstract class TestObjectWrapperBase {

    protected ObjectWrapper wrapper;
    protected House house;
    protected Clazz houseClazz;
    protected ClazzAttribute colorClazzAttribute;
    protected Clazz simpleDimensionClazz;
    protected ClazzList simpleClazzList;

    @Before
    public void setUp() {
        wrapper = new ObjectWrapper();
        XStream xStream = new XStream();
        house = (House) xStream.fromXML(this.getClass().getResourceAsStream("/house.init.xml"));
        houseClazz = (Clazz) xStream.fromXML(this.getClass().getResourceAsStream("/houseClazz.init.xml"));

        if (houseClazz.getAttributes() != null) {
            for (Element element : houseClazz.getAttributes()) {
                if (element.getFieldName().equals("dimension")) {
                    simpleDimensionClazz = (Clazz) element;
                } else if (element.getFieldName().equals("color")) {
                    colorClazzAttribute = (ClazzAttribute) element;
                } else if (element.getFieldName().equals("stages")) {
                    simpleClazzList = (ClazzList) element;
                }
            }
        }
    }

}
