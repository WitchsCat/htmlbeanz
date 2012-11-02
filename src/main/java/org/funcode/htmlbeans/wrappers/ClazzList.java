package org.funcode.htmlbeans.wrappers;

import java.util.ArrayList;
import java.util.List;

public class ClazzList extends Element {

	protected List<Element> elements;

	public List<Element> getElements() {
		if(elements == null){
			elements = new ArrayList<Element>();
		}
		return elements;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClazzList other = (ClazzList) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}
	
	
}
