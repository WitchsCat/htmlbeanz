/**
 *
 */
package org.funcode.htmlbeans.mapping;

import com.google.gson.*;
import org.funcode.htmlbeans.wrappers.*;

import java.lang.reflect.Type;

/**
 * @author dmarkin
 */
public class Json2JavaMapper {
    /**
     * Initialized google JSON mapper, with self written deserializer
     */
    private final Gson gson;

    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Element.class,
                new ClassAttributeDeserializer());
        gson = builder.create();
    }

    public Element json2Java(String json) {
        if (json == null) {
            return null;
        }
        if (json.isEmpty()) {
            return new Clazz();
        }

        return gson.fromJson(json, Element.class);

    }

    class ClassAttributeDeserializer implements JsonDeserializer<Element> {

        @Override
        public Element deserialize(JsonElement json, Type type,
                                   JsonDeserializationContext context) throws JsonParseException {

            JsonObject unknownElement = json.getAsJsonObject();
            JsonElement typeJsonElement = unknownElement.get("type");
            ElementType elementType;
            if (typeJsonElement == null) {
                throw new IllegalArgumentException("Supplied JSON object doesn't contain 'type' field");
            } else {
                elementType = ElementType.valueOf(typeJsonElement.getAsString());
            }
            /*
            Choose the correct object type & add the specific attributes
             */
            Element result;
            switch (elementType) {
                case COMPLEX: {
                    result = new Clazz();
                    JsonElement attributes = unknownElement.get("attributes");
                    if (attributes != null) {
                        for (JsonElement attribute : attributes.getAsJsonArray()) {
                            ((Clazz) result).getAttributes().add(
                                    gson.fromJson(attribute, Element.class));
                        }
                    }
                    break;
                }
                case LIST: {
                    result = new ClazzList();
                    ((ClazzList) result).setElementsGenericClass(unknownElement.get("elementsGenericClass").getAsString());
                    JsonElement elements = unknownElement.get("elements");
                    if (elements != null) {
                        for (JsonElement element : elements.getAsJsonArray()) {
                            ((ClazzList) result).getElements().add(
                                    gson.fromJson(element, Element.class));
                        }
                    }
                    break;
                }
                default: {
                    result = new ClazzAttribute();
                    JsonElement originalValue = unknownElement.get("originalValue");
                    switch (elementType) {
                        case BOOLEAN: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsBoolean());
                            break;
                        }
                        case STRING: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsString());
                            break;
                        }
                        case BYTE: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsByte());
                            break;
                        }
                        case DOUBLE: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsDouble());
                            break;
                        }
                        case FLOAT: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsFloat());
                            break;
                        }
                        case LONG: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsLong());
                            break;
                        }
                        case INTEGER: {
                            ((ClazzAttribute) result).setOriginalValue(originalValue.getAsInt());
                            break;
                        }
                    }
                }
            }
            /*
            Set the common attributes of an Element
             */
            JsonElement fieldName = unknownElement.get("fieldName");
            if (fieldName != null) {
                result.setFieldName(fieldName.getAsString());
            }
            result.setType(elementType);
            result.setOriginalClass(unknownElement.get("originalClass").getAsString());
            result.setEmpty(unknownElement.get("isEmpty").getAsBoolean());


            return result;
        }
    }


}
