package org.funcode.htmlbeans.samples.foundation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class House {

	private String color;
	
	private Dimension dimension;
	
	private List<Stage> stages;

    private Map<String, Integer> objects;

    private Map<Room, Stage> roomStageMap;


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public Map<String, Integer> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, Integer> objects) {
        this.objects = objects;
    }

    public Map<Room, Stage> getRoomStageMap() {
        return roomStageMap;
    }

    public void setRoomStageMap(Map<Room, Stage> roomStageMap) {
        this.roomStageMap = roomStageMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;

        House house = (House) o;

        if (color != null ? !color.equals(house.color) : house.color != null) return false;
        if (dimension != null ? !dimension.equals(house.dimension) : house.dimension != null) return false;
        if (objects != null ? !objects.equals(house.objects) : house.objects != null) return false;
        if (roomStageMap != null ? !roomStageMap.equals(house.roomStageMap) : house.roomStageMap != null) return false;
        if (stages != null ? !stages.equals(house.stages) : house.stages != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = color != null ? color.hashCode() : 0;
        result = 31 * result + (dimension != null ? dimension.hashCode() : 0);
        result = 31 * result + (stages != null ? stages.hashCode() : 0);
        result = 31 * result + (objects != null ? objects.hashCode() : 0);
        result = 31 * result + (roomStageMap != null ? roomStageMap.hashCode() : 0);
        return result;
    }
}
