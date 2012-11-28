package org.funcode.htmlbeans.mapping;

import org.funcode.htmlbeans.wrappers.Element;

/**
 * Simple Element to Element mapper.
 * User: amatveev
 * Date: 27.11.12
 * Time: 20:58
 * To change this template use File | Settings | File Templates.
 */
public class ElementToElementMapper {

    /**
     * Map element to element.
     * @param source
     * @param dest
     */
    public void mapElement2Element(Element source, Element dest) {
        dest.setEmpty(source.isEmpty());
        dest.setFieldName(source.getFieldName());
        dest.setOriginalClass(source.getOriginalClass());
        dest.setType(source.getType());
    }

}
