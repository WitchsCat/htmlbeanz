package org.funcode.htmlbeans.wrappers;

import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.samples.foundation.House;
import org.junit.Before;

/**
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 01.11.12
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestObjectWrapperBase {

    protected ObjectWrapper wrapper;
    protected House house;
    protected Clazz houseClazz;

    @Before
    public void setUp() {
        wrapper = new ObjectWrapper();
        XStream xStream = new XStream();
        house = (House) xStream.fromXML(this.getClass().getResourceAsStream("/house.init.xml"));
        houseClazz = (Clazz) xStream.fromXML(this.getClass().getResourceAsStream("/houseClazz.init.xml"));
    }

}
