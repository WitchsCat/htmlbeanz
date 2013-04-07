package de.tmobile.ecare.common.vo;

import java.io.Serializable;

import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Object for the ONE! Segment Data.
 *
 * @author besel
 */
public class RelevantSegmentVO implements Serializable {

    /** The customer type. */
    @Transient
    private Long customerType;

    /** The segment class. */
    @Transient
    private Long segmentClass;

    /** The segment string. */
    @Transient
    private String segmentString;

    /** The context. */
    private String context;

    /**
     * Gets the context.
     *
     * @return the context
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the context.
     *
     * @param context the new context
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Gets the customer type.
     *
     * @return the customer type
     */
    public Long getCustomerType() {
        return customerType;
    }

    /**
     * Sets the customer type.
     *
     * @param customerType the new customer type
     */
    public void setCustomerType(Long customerType) {
        this.customerType = customerType;
    }

    /**
     * Gets the segment class.
     *
     * @return the segment class
     */
    public Long getSegmentClass() {
        return segmentClass;
    }

    /**
     * Sets the segment class.
     *
     * @param segementClass the new segment class
     */
    public void setSegmentClass(Long segementClass) {
        this.segmentClass = segementClass;
    }

    /**
     * Gets the segment string.
     *
     * @return the segment string
     */
    public String getSegmentString() {
        return segmentString;
    }

    /**
     * Sets the segment string.
     *
     * @param segementString the new segment string
     */
    public void setSegmentString(String segementString) {
        this.segmentString = segementString;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof RelevantSegmentVO)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        RelevantSegmentVO other = (RelevantSegmentVO)o;
        return new EqualsBuilder().append(customerType, other.customerType).append(segmentClass, other.segmentClass)
                .append(segmentString, other.segmentString).append(context, other.context).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(customerType).append(segmentClass).append(segmentString)
                .append(context).toHashCode();
    }
}
