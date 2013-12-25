package org.funcode.htmlbeans.wrappers;

import com.thoughtworks.xstream.XStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 07.11.12
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public class TestUtil {

    public static void main(String[] args) throws FileNotFoundException, IllegalAccessException {
        XStream xStream = new XStream();
        Object house = xStream.fromXML(TestUtil.class.getResourceAsStream("/house.init.xml"));
        PrintWriter printWriter = new PrintWriter(new FileOutputStream("D://house2.init.xml"));
        printWriter.print(xStream.toXML(new ObjectWrapper().doGood(house)));
        printWriter.flush();
        printWriter.close();

    }

}
