package org.funcode.htmlbeans.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.samples.XmlMaker;
import org.funcode.htmlbeans.samples.foundation.House;
import org.funcode.htmlbeans.samples.foundation.Stage;
import org.funcode.htmlbeans.wrappers.Element;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;
import org.junit.Test;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 12/26/13
 * Time: 12:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestGsonMapper {

    @Test
    public void testGsonMapSerializer() throws IllegalAccessException {
        HashMap<Teacher, List<Student>> map = new HashMap<Teacher, List<Student>>();
        Teacher t = new Teacher("12345", "Teacher");
        Teacher t2 = new Teacher("23456", "Teacher2");
        ArrayList<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 3; i++) {
            list.add(new Student(String.valueOf(i), "Student" + String.valueOf(i)));
        }
        map.put(t, list);
        map.put(t2, list);
        GsonBuilder builder = new GsonBuilder();

        Gson gson = builder.enableComplexMapKeySerialization().setPrettyPrinting().create();
        System.out.println(gson.toJson(map));

        House house = XmlMaker.getHouse();
        Element element = new ObjectWrapper().doGood(house);
        System.out.println(gson.toJson(element));
    }

    class Teacher {
        String id;
        String name;

        Teacher(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    class Student {
        String id;
        String name;

        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
