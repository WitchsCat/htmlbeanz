package de.tmobile.ecare.common.vo.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>Title: eCare</p>
 * <p>Description: a VO to mat new S_ValidationReason2 structure
 *  in isBusinessProcessEligible6 CCOS webservice result data</p>
 * <p>Company: T-mobile Deutschland GmbH</p>
 * @author eCare
 * @version 1.0
 * @since 9.1
 */
public class CcosValidationReasonVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String description;

    private String externalDescription;

    private String translatedDescription;

    private String alternativeValue;

    private Integer ranking;

    public String getAlternativeValue() {
        return alternativeValue;
    }

    public void setAlternativeValue(String alternativeValue) {
        this.alternativeValue = alternativeValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternalDescription() {
        return externalDescription;
    }

    public void setExternalDescription(String externalDescription) {
        this.externalDescription = externalDescription;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public String getTranslatedDescription() {
        return translatedDescription;
    }

    public void setTranslatedDescription(String translatedDescription) {
        this.translatedDescription = translatedDescription;
    }

    public String toString() {
        return CcosValidationReasonVO.class
                + "[code: "
                + code
                + ", description: "
                + description
                + ",\n translated:"
                + translatedDescription
                + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(code).append(description).append(externalDescription)
                .append(translatedDescription).append(alternativeValue).append(ranking).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (this == obj) {
            result = true;
        } else if (obj instanceof CcosValidationReasonVO) {
            CcosValidationReasonVO vo = (CcosValidationReasonVO)obj;
            result = new EqualsBuilder().append(code, vo.code).append(description, vo.description)
                    .append(externalDescription, vo.externalDescription)
                    .append(translatedDescription, vo.translatedDescription)
                    .append(alternativeValue, vo.alternativeValue).append(ranking, vo.ranking).isEquals();
        }
        return result;
    }

}
