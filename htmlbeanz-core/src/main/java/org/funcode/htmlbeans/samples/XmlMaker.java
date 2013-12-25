package org.funcode.htmlbeans.samples;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.samples.foundation.House;
import org.funcode.htmlbeans.samples.foundation.Room;
import org.funcode.htmlbeans.samples.foundation.Stage;
import org.funcode.htmlbeans.wrappers.Element;
import org.funcode.htmlbeans.wrappers.ObjectWrapper;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 11/13/13
 * Time: 3:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlMaker {

    public static void main(String[] args) throws IOException, IllegalAccessException {
        XStream xStream = new XStream();
        xStream.toXML(getHouse(), new FileOutputStream("C:\\dev\\workspace\\htmlbeanz\\htmlbeanz-core\\src\\main\\resources\\house.xml"));
        xStream.toXML(getHouseWrapped(), new FileOutputStream("C:\\dev\\workspace\\htmlbeanz\\htmlbeanz-core\\src\\main\\resources\\house.wrapped.xml"));
    }

    public static House getHouse() {
        House house = new House();
        house.setColor("red");
        house.setDimension(new Dimension(100,200));
        Stage stage = new Stage();
        stage.setPerimeter(14);
        Room room1 = new Room(12,"Alex");
        Room room2 = new Room(13,"Nancy");
        Room room3 = new Room(14,"Drew");
        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);
        stage.setRooms(rooms);
        List<Stage> stages = new ArrayList<Stage>();
        stages.add(stage);
        house.setStages(stages);
        Map<String, Integer> objects = new HashMap<String, Integer>();
        objects.put("window", 6);
        objects.put("doors", 12);
        house.setObjects(objects);
        Map<Room, Stage> roomStageMap = new HashMap<Room, Stage>();
        roomStageMap.put(room1, stage);
        roomStageMap.put(room2, stage);
        roomStageMap.put(room3, stage);
        house.setRoomStageMap(roomStageMap);
        return house;
    }

    public static Element getHouseWrapped() {
        Element element = null;
        try {
            element = new ObjectWrapper().doGood(getHouse());
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return element;
    }

}
