package org.funcode.htmlbeans.wrappers;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a result of xml parsing. Contains calculated information for further representation
 *
 * @author dmarkin
 */
public class Clazz extends Element {

    private List<Element> attributes;

    public Clazz() {
        super();
    }

    public List<Element> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Element> attributes) {
        this.attributes = attributes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((attributes == null) ? 0 : attributes.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Clazz other = (Clazz) obj;
        if (attributes == null) {
            if (other.attributes != null)
                return false;
        } else if (!attributes.equals(other.attributes))
            return false;
        return true;
    }

}
