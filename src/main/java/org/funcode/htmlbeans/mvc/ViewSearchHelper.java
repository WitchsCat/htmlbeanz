package org.funcode.htmlbeans.mvc;

import org.funcode.htmlbeans.wrappers.*;

import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: dmarkin
 * Date: 22.11.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class ViewSearchHelper {

    public static void updateParent(Element parent, Element changedElement) {
        switch (parent.getType()) {
            case COMPLEX:
                Clazz parentClazz = (Clazz) parent;
                ListIterator<Element> attibuteIterator = parentClazz.getAttributes().listIterator();
                while (attibuteIterator.hasNext()) {
                    Element nextElement = attibuteIterator.next();
                    if (nextElement.getFieldName().equals(changedElement.getFieldName())) {
                        attibuteIterator.set(changedElement);
                    }
                }
                break;
            case LIST:
                ClazzList parentClazzList = (ClazzList) parent;
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
                break;
            default:
                throw new IllegalArgumentException("You can update only a list or a class not " + parent.getType());
        }

    }

}
