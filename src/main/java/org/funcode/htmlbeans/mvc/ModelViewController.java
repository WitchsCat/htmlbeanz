package org.funcode.htmlbeans.mvc;


import com.google.gson.Gson;
import org.funcode.htmlbeans.mapping.ElementToElementMapper;
import org.funcode.htmlbeans.mapping.Json2JavaMapper;
import org.funcode.htmlbeans.wrappers.Clazz;
import org.funcode.htmlbeans.wrappers.ClazzAttribute;
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

    private final Json2JavaMapper json2JavaMapper = new Json2JavaMapper();

    private final ElementToElementMapper elementToElementMapper = new ElementToElementMapper();

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
     * @deprecated
     */
    public void applyChanges(Map<Object, Object> changedValues) {
        for (Object elementName : changedValues.keySet()) {
            String parentAttributeName = ((String) elementName).substring(0, ((String) elementName).lastIndexOf("-"));
            if (modelBindingMap.containsKey(parentAttributeName)) {
                Element parentElement = modelBindingMap.get(parentAttributeName);
                ViewSearchHelper.updateParent(parentElement,
                        json2JavaMapper.json2Java(
                                ((String[]) changedValues.get(elementName))[0]));
            }
        }
    }

    public void putElements(Map<String, String> map) {
        for (String id : map.keySet()) {
            String jsonElement = map.get(id);
            Element element = json2JavaMapper.json2Java(jsonElement);
            putSingleElement(id, element);
        }
    }

    /**
     * Puts element with presented id into the model or map existing.
     * @param id
     * @param element
     */
    public void putSingleElement(String id, Element element) {
        String parentElementId = id.substring(0, id.lastIndexOf("-"));
        String childElementId = id.substring(id.lastIndexOf("-") + 1, id.length());
        Element parentElement = modelBindingMap.get(parentElementId);
        if (parentElement instanceof Clazz) {
            Clazz parentClazz = ((Clazz) parentElement);
            boolean existInAttributes = false;
            for (Element attribute : parentClazz.getAttributes()) {
                if (attribute.getFieldName().equals(childElementId)) {
                    elementToElementMapper.mapElement2Element(element, attribute);
                    existInAttributes = true;
                    break;
                }
            }
            if (!existInAttributes) {
                parentClazz.getAttributes().add(element);
                // TODO update modelBindingMap
            }
        } else if (parentElement instanceof ClazzList) {
            ClazzList parentClazzList = ((ClazzList) parentElement);
            boolean existInAttributes = false;
            for (Element listElement : parentClazzList.getElements()) {
                if (listElement.getFieldName().equals(childElementId)) {
                    elementToElementMapper.mapElement2Element(element, listElement);
                    existInAttributes = true;
                    break;
                }
            }
            if (!existInAttributes) {
                parentClazzList.getElements().add(element);
                // TODO update modelBindingMap
            }
        } else {
            // stop the world exception
        }
    }

    public Map<String, Element> getModelBindingMap() {
        return modelBindingMap;
    }

    public Element getModel() {
        return model;
    }

}


