package org.funcode.htmlbeans.samples.foundation;

import java.util.List;

public class Stage {

	private Integer perimeter;
	
	private List<Room> rooms;

	public Integer getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Integer perimeter) {
		this.perimeter = perimeter;
	}

	public List<Room> getRooms() {
		return rooms;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stage stage = (Stage) o;

        if (perimeter != null ? !perimeter.equals(stage.perimeter) : stage.perimeter != null) return false;
        if (rooms != null ? !rooms.equals(stage.rooms) : stage.rooms != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = perimeter != null ? perimeter.hashCode() : 0;
        result = 31 * result + (rooms != null ? rooms.hashCode() : 0);
        return result;
    }

    public void setRooms(List<Room> rooms) {

		this.rooms = rooms;
	}
	
	
 	
}
