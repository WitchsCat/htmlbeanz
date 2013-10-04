package org.funcode.htmlbeans.samples.animal;

import java.util.List;

public class Cat {
	
	private String name;
	
	final private Paw[] paws = new Paw[4];
	
	final private Eye[] eyes = new Eye[2];
	
	private List<Interior> interiors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Paw[] getPaws() {
		return paws;
	}

	public Eye[] getEyes() {
		return eyes;
	}

	public List<Interior> getInteriors() {
		return interiors;
	}

	public void setInteriors(List<Interior> interiors) {
		this.interiors = interiors;
	}  

}
