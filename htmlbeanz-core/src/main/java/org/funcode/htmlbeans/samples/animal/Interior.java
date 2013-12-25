package org.funcode.htmlbeans.samples.animal;

public class Interior {
	
	private int health;
	
	private OrganType organType;
	
	
	public int getHealth() {
		return health;
	}


	public void setHealth(int health) {
		this.health = health;
	}


	public OrganType getOrganType() {
		return organType;
	}


	public void setOrganType(OrganType organType) {
		this.organType = organType;
	}


	static enum OrganType {
		HEART, LUNG, KIDNEY
	}

}
