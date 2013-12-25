package org.funcode.htmlbeans.wrappers;

public abstract class Element {
    /**
     * It is really recommended not to rename this field, since it is one core field used during mapping.
     */
    protected ElementType type;

    protected String fieldName;

    protected String originalClass;

    protected boolean isEmpty;

    public Element() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        if (isEmpty != element.isEmpty) return false;
        if (fieldName != null ? !fieldName.equals(element.fieldName) : element.fieldName != null) return false;
        if (originalClass != null ? !originalClass.equals(element.originalClass) : element.originalClass != null)
            return false;
        if (type != element.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (originalClass != null ? originalClass.hashCode() : 0);
        result = 31 * result + (isEmpty ? 1 : 0);
        return result;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getOriginalClass() {
        return originalClass;
    }

    public void setOriginalClass(String originalClass) {
        this.originalClass = originalClass;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
