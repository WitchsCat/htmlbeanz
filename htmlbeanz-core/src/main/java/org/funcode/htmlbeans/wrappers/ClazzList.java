package org.funcode.htmlbeans.wrappers;

import java.util.ArrayList;
import java.util.List;

public class ClazzList extends Element {

    private String elementsGenericClass;

    protected List<Element> elements;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public String getElementsGenericClass() {
        return elementsGenericClass;
    }

    public void setElementsGenericClass(String elementsGenericClass) {
        this.elementsGenericClass = elementsGenericClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClazzList clazzList = (ClazzList) o;

        if (elements != null ? !elements.equals(clazzList.elements) : clazzList.elements != null) return false;
        if (elementsGenericClass != null ? !elementsGenericClass.equals(clazzList.elementsGenericClass) : clazzList.elementsGenericClass != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        result = 31 * result + (elementsGenericClass != null ? elementsGenericClass.hashCode() : 0);
        return result;
    }

}
