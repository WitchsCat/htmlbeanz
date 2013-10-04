package org.funcode.htmlbeans.mvc;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.funcode.htmlbeans.mapping.Json2JavaMapper;
import org.funcode.htmlbeans.samples.foundation.House;
import org.funcode.htmlbeans.samples.foundation.Room;
import org.funcode.htmlbeans.samples.foundation.Stage;
import org.funcode.htmlbeans.wrappers.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: amatveev
 * Date: 09.01.13
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class TestModelViewController extends TestObjectWrapperBase {

    private Json2JavaMapper mapper;
    private Gson gson;
    private ModelViewController mvc;
    public static final String STAGES_ID = "TOP-stages";
    public static final String DIMENSION_ID = "TOP-dimension";

    @Test
    public void testApplyChangesPutSameObject() throws IllegalAccessException {
        XStream xStream = new XStream();
        Element originalModel = (Element) xStream.fromXML(xStream.toXML((Element) mvc.getModel()));
        Clazz originalDimensionHouseClazz = (Clazz) wrapper
                .doGood(house.getDimension());
        mvc.applyChanges(DIMENSION_ID, gson.toJson(originalDimensionHouseClazz));
        assertEquals(
                "original mvc model and model after applyChanges with the same object are equals",
                originalModel, mvc.getModel());
    }

    @Test
    public void testApplyChangesPutDifferentObject() throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        assertEquals("Original house have 2 stages", house.getStages().size(), 2);

        // changes
        final Integer NEW_PERIMETER = Integer.valueOf(123);
        final Integer NEW_NUMBER = Integer.valueOf(1234);
        final String NEW_OWNER = "OneTwoThreeFour";
        final Room NEW_ROOM = new Room();
        house.getStages().get(1).setPerimeter(NEW_PERIMETER);
        NEW_ROOM.setNumber(NEW_NUMBER);
        NEW_ROOM.setOwner(NEW_OWNER);
        house.getStages().get(1).getRooms().add(NEW_ROOM);

        Clazz originalHouseClazz = (Clazz) wrapper
                .doGood(house);
        ClazzList originalStagesClazz = (ClazzList) originalHouseClazz.getAttributes().get(2);

        mvc.applyChanges(STAGES_ID, gson.toJson(originalStagesClazz));
        House changedObject = (House) wrapper.doReverse(mvc.getModel());
        assertEquals(
                "Changed mvc model have wrong perimeter value",
                changedObject.getStages().get(1).getPerimeter(), Integer.valueOf(NEW_PERIMETER));
        assertEquals(
                "Changed mvc model doesn't have new Room",
                changedObject.getStages().get(1).getRooms().size(), 3);
        assertEquals(
                "Changed mvc model have wrong number in new Room",
                changedObject.getStages().get(1).getRooms().get(2).getNumber(), NEW_NUMBER);
        assertEquals(
                "Changed mvc model have wrong owner in new Room",
                changedObject.getStages().get(1).getRooms().get(2).getOwner(), NEW_OWNER);
    }

    @Test
    public void testDeleteElement() throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        house.getStages().remove(0);
        XStream xStream = new XStream();
        Stage leftStage = (Stage)xStream.fromXML(xStream.toXML(house.getStages().get(0)));

        ClazzList originalStagesClazz = (ClazzList) wrapper
        .doGood(house.getStages());

        mvc.applyChanges(STAGES_ID, gson.toJson(originalStagesClazz));
        House changedObject = (House) wrapper.doReverse(mvc.getModel());

        assertEquals(
                "Changed mvc model stage 1 is not deleted",
                changedObject.getStages().size(), 1);
        assertEquals(
                "Original mvc model stage 2 does not shift to stage 1",
                changedObject.getStages().get(0), leftStage);
    }

    @Before
    public void init() {
        mapper = new Json2JavaMapper();
        gson = new Gson();
        try {
            mvc = new ModelViewController(wrapper.doGood(house));
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //TODO Create an error message, for the GUI
        }

    }

}
