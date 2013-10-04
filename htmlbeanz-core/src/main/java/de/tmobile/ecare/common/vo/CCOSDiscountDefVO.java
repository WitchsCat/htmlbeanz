package de.tmobile.ecare.common.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * 
 * Base class for discounts. Used for customer discounts, too.
 * 
 * @version 0.0 07.12.2007
 * @author Viktor Hassenrik, mindstep GmbH, hassenrik@mindstep.de
 *
 */
@MappedSuperclass
public class CCOSDiscountDefVO implements Serializable{

    /** The rule name. */
    protected String ruleName;
    
    /** The rule value. */
    protected String ruleValue;
    
    /** The gross discount. */
    protected Float grossDiscount;
    
    /** The net discount. */
    protected Float netDiscount;
    
    /** The unit. */
    protected String unit;
    
    /** The used amount. */
    protected Float usedAmount;
    

	/**
	 * Instantiates a new cCOS discount def vo.
	 */
	public CCOSDiscountDefVO() {
        super();
    }

    /**
     * hibernate.property column="RULE_NAME"
     * @return the ruleName
     */
    @Column(name = "RULE_NAME")
    public String getRuleName() {
        return ruleName;
    }

    /**
     * Sets the rule name.
     *
     * @param ruleName the ruleName to set
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * hibernate.property column="RULE_VALUE"
     * @return the ruleValue
     */
    @Column(name = "RULE_VALUE")
    public String getRuleValue() {
        return ruleValue;
    }

    /**
     * Sets the rule value.
     *
     * @param ruleValue the ruleValue to set
     */
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    /**
     * hibernate.property column="GROSS_DISCOUNT"
     * @return the grossDiscount
     */
    @Column(name="GROSS_DISCOUNT")
    public Float getGrossDiscount() {
        return grossDiscount;
    }

    /**
     * Sets the gross discount.
     *
     * @param grossDiscount the grossDiscount to set
     */
    public void setGrossDiscount(Float grossDiscount) {
        this.grossDiscount = grossDiscount;
    }

    /**
     * hibernate.property column="NET_DISCOUNT"
     * @return the netDiscount
     */
    @Column(name="NET_DISCOUNT")
    public Float getNetDiscount() {
        return netDiscount;
    }

    /**
     * Sets the net discount.
     *
     * @param netDiscount the netDiscount to set
     */
    public void setNetDiscount(Float netDiscount) {
        this.netDiscount = netDiscount;
    }

    /**
     * hibernate.property column="UNIT"
     * @return the unit
     */
    @Column(name="UNIT")
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit.
     *
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Gets the used amount.
     *
     * @return the used amount
     */
    @Transient    
	public Float getUsedAmount() {
		return usedAmount;
	}

	/**
	 * Sets the used amount.
	 *
	 * @param usedAmount the new used amount
	 */
	public void setUsedAmount(Float usedAmount) {
		this.usedAmount = usedAmount;
	}
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if (!(other instanceof CCOSDiscountDefVO)) {
            return false;
        }

        CCOSDiscountDefVO castOther = (CCOSDiscountDefVO)other;
        return new EqualsBuilder().append(this.ruleName, castOther.ruleName)
                .append(this.ruleValue, castOther.ruleValue).isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.ruleName).append(this.ruleValue).toHashCode();
    }
    
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "** " + this.getClass().getName() + ": " +
        "ruleName: " + getRuleName() + 
        ", ruleValue: " + getRuleValue();

        
    }

}
