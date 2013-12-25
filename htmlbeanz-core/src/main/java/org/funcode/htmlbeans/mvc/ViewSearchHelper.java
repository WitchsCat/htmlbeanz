package org.funcode.htmlbeans.mvc;

import org.funcode.htmlbeans.wrappers.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * This class is an util, that helps to navigate the UI model & apply changes to it.
 * User: dmarkin
 * Date: 22.11.12
 * Time: 17:57
 */
public class ViewSearchHelper {

    /**
     * Given element is becoming a part of the UI model
     *
     * @param parent         node that exists in the UI model on the server side
     * @param changedElement element that is to become part of the model
     */
    public static void updateParent(Element parent, Element changedElement) {
        switch (parent.getType()) {
            case COMPLEX:
                Clazz parentClazz = (Clazz) parent;
                if (parentClazz.getAttributes() != null) {
                    ListIterator<Element> attributeIterator = parentClazz.getAttributes().listIterator();
                    while (attributeIterator.hasNext()) {
                        Element nextElement = attributeIterator.next();
                        if (nextElement.getFieldName().equals(changedElement.getFieldName())) {
                            attributeIterator.set(changedElement);
                        }
                    }
                } else {
                    // changed element is new
                    ArrayList<Element> attributes = new ArrayList<Element>();
                    attributes.add(changedElement);
                    parentClazz.setAttributes(attributes);
                }
                break;
            case LIST:
                ClazzList parentClazzList = (ClazzList) parent;
                if (parentClazzList.getElements() != null) {
                    ListIterator<Element> elementIterator = parentClazzList.getElements().listIterator();
                    boolean elementExisted = false;
                    while (elementIterator.hasNext()) {
                        Element nextElement = elementIterator.next();
                        if (nextElement.getFieldName().equals(changedElement.getFieldName())) {
                            elementIterator.set(changedElement);
                            elementExisted = true;
                        }
                    }
                    if (!elementExisted) {
                        parentClazzList.getElements().add(changedElement);
                    }
                } else {
                    // first element in list
                    ArrayList<Element> elements = new ArrayList<Element>();
                    elements.add(changedElement);
                    parentClazzList.setElements(elements);
                }
                break;
            default:
                throw new IllegalArgumentException("You can update only a list or a class not " + parent.getType());
        }

    }

}
