package org.funcode.htmlbeans.mvc;


import org.funcode.htmlbeans.mapping.Json2JavaMapper;
import org.funcode.htmlbeans.wrappers.Clazz;
import org.funcode.htmlbeans.wrappers.ClazzList;
import org.funcode.htmlbeans.wrappers.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * This controller is responsible for binding the presentation model to GUI forms and visa versa
 * when working with Object wrappers.
 * User: dmarkin
 * Date: 02.11.12
 * Time: 11:57
 */
public class ModelViewController {

    /**
     * Map that will be used for quick access to elements by their ID.
     */
    private final Map<String, Element> modelBindingMap = new HashMap<String, Element>();

    /**
     * View presentation
     */
    private final Element model;


    /**
     * Mapper used to convert json string to java
     */
    private final Json2JavaMapper json2JavaMapper = new Json2JavaMapper();

    /**
     * ModelViewController can't exist without a binding to an element model
     *
     * @param model that will be used to bind UI forms to
     */
    public ModelViewController(Element model) {
        this.model = model;
        fillModelBindingMap(model, "TOP");
    }

    /**
     * This method does binding of elements to the IDs. The same approach to ID generation is used on the user side.
     *
     * @param element  the node in the element tree to create binding for.
     * @param parentId since id isn't part of the Element, we have to pass the parent nodes ID here.
     */
    private void fillModelBindingMap(Element element, String parentId) {
        String id;
        if (element.getFieldName() != null) {
            id = parentId + "-" + element.getFieldName();
        } else {
            id = parentId;
        }
        modelBindingMap.put(id, element);
        switch (element.getType()) {
            case COMPLEX: {
                Clazz elementAsClazz = (Clazz) element;
                for (Element attribute : elementAsClazz.getAttributes()) {
                    fillModelBindingMap(attribute, id);
                }
                break;
            }
            case LIST: {
                ClazzList elementAsClazzList = (ClazzList) element;
                for (Element listElement : elementAsClazzList.getElements()) {
                    fillModelBindingMap(listElement, id);
                }
                break;
            }
            default: {
                //do nothing I guess :)
            }
        }
    }

    /**
     * This methods takes incoming attributes and maps trier values to the presentation model
     *
     * @param changedValues Map of key value pairs, that are to be reflected to the presentation model
     */
    public void applyChanges(Map<Object, Object> changedValues) {
        for (Object elementId : changedValues.keySet()) {
            applyChanges((String) elementId, ((String[]) changedValues.get(elementId))[0]);
        }
    }

    /**
     * This method applies values in the json structure to element stored under the given ID
     *
     * @param elementId
     * @param json
     */
    public void applyChanges(String elementId, String json) {
        String parentElementId = (elementId).substring(0, (elementId).lastIndexOf("-"));
        if (modelBindingMap.containsKey(parentElementId)) {
            Element parentElement = modelBindingMap.get(parentElementId);
            Element changedElement = json2JavaMapper.json2Java(json);
            fillModelBindingMap(changedElement, parentElementId);
            ViewSearchHelper.updateParent(parentElement,
                    changedElement);
        }
    }

    public void putElements(Map<String, String> map) {
        for (String id : map.keySet()) {
            String jsonElement = map.get(id);
            applyChanges(id, jsonElement);
        }
    }

    /**
     * Getter for the UI model around witch the controller is build
     *
     * @return Element that is the top of the model that is currently worked on in session.
     */
    public Element getModel() {
        return model;
    }

}


