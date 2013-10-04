package org.funcode.htmlbeans.wrappers;

public class ClazzAttribute extends Element {

    private Object originalValue;

    public ClazzAttribute() {
        super();
    }

    public Object getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(Object originalValue) {
        this.originalValue = originalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;

        ClazzAttribute that = (ClazzAttribute) o;

        return !(originalValue != null ? !originalValue.equals(that.originalValue) : that.originalValue != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (originalValue != null ? originalValue.hashCode() : 0);
        return result;
    }

}
