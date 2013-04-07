package de.tmobile.ecare.common.vo;

import java.io.Serializable;


/**
 * The Class CCOSCustomerDiscountDefVO.
 */
public class CCOSCustomerDiscountDefVO extends CCOSDiscountDefVO implements Serializable {

    /** The group. */
    protected String group;

    /** The exclusive type. */
    protected Integer exclusiveType;

    /** The rank. */
    private Integer rank;

    /**
     * Gets the exclusive type.
     *
     * @return the exclusive type
     */
    public Integer getExclusiveType() {
        return exclusiveType;
    }

    /**
     * Sets the exclusive type.
     *
     * @param exclusiveType the new exclusive type
     */
    public void setExclusiveType(Integer exclusiveType) {
        this.exclusiveType = exclusiveType;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param group the new group
     */
    public void setGroup(String group) {
        // XXX: remove this hack when CCOS bug will be fixed.
        // see also TMOab43844
        if (group == null) {
            group = "null";
        }
        this.group = group;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * Sets the rank.
     *
     * @param rank the new rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CCOSCustomerDiscountDefVO [group="
                + group
                + ", exclusiveType="
                + exclusiveType
                + ", rank="
                + rank
                + ", ruleName="
                + ruleName
                + ", ruleValue="
                + ruleValue
                + ", grossDiscount="
                + grossDiscount
                + ", netDiscount="
                + netDiscount
                + ", unit="
                + unit
                + ", usedAmount="
                + usedAmount
                + "]";
    }

}
