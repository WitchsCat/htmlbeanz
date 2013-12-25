package org.funcode.htmlbeans.wrappers;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 11/13/13
 * Time: 12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class ClazzMap extends Element {

    private String keyGenericClass;

    private String valueGenericClass;

    private Map<Element, Element> mapElements;

    public String getKeyGenericClass() {
        return keyGenericClass;
    }

    public void setKeyGenericClass(String keyGenericClass) {
        this.keyGenericClass = keyGenericClass;
    }

    public String getValueGenericClass() {
        return valueGenericClass;
    }

    public void setValueGenericClass(String valueGenericClass) {
        this.valueGenericClass = valueGenericClass;
    }

    public Map<Element, Element> getMapElements() {
        return mapElements;
    }

    public void setMapElements(Map<Element, Element> mapElements) {
        this.mapElements = mapElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClazzMap)) return false;
        if (!super.equals(o)) return false;

        ClazzMap clazzMap = (ClazzMap) o;

        if (keyGenericClass != null ? !keyGenericClass.equals(clazzMap.keyGenericClass) : clazzMap.keyGenericClass != null)
            return false;
        if (mapElements != null ? !mapElements.equals(clazzMap.mapElements) : clazzMap.mapElements != null)
            return false;
        if (valueGenericClass != null ? !valueGenericClass.equals(clazzMap.valueGenericClass) : clazzMap.valueGenericClass != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (keyGenericClass != null ? keyGenericClass.hashCode() : 0);
        result = 31 * result + (valueGenericClass != null ? valueGenericClass.hashCode() : 0);
        result = 31 * result + (mapElements != null ? mapElements.hashCode() : 0);
        return result;
    }
}
