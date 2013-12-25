package org.funcode.htmlbeans.samples.foundation;

public class Room implements Cloneable {

	private Integer number;

	private String owner;

	public Room() {
		super();
	}

	public Room(int number, String owner) {
		super();
		this.number = number;
		this.owner = owner;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		return result;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (!number.equals(room.number)) return false;
        if (!owner.equals(room.owner)) return false;

        return true;
    }

    @Override
    public Object clone() {
        Room result = new Room();
        result.setNumber(this.getNumber());
        result.setOwner(this.getOwner());
        return result;
    }
	
}
