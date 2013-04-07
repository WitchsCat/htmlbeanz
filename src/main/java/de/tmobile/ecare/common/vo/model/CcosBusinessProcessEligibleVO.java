package de.tmobile.ecare.common.vo.model;

import java.io.Serializable;
import java.util.List;

import de.tmobile.ecare.common.vo.CCOSSalesStructureVO;

/**
 * <p>Title: eCare</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: T-mobile Deutschland GmbH</p>
 * @author eCare
 * @version 1.0
 * @since 9.2
 */
public class CcosBusinessProcessEligibleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private CCOSSalesStructureVO salesStructure;

    private List<CcosEligibilityDataVO> eligibilityData;

    public CcosBusinessProcessEligibleVO(CCOSSalesStructureVO salesStructure, List<CcosEligibilityDataVO> eligibilityData) {
        this.salesStructure = salesStructure;
        this.eligibilityData = eligibilityData;
    }

    public List<CcosEligibilityDataVO> getEligibilityData() {
        return eligibilityData;
    }

    public CCOSSalesStructureVO getSalesStructure() {
        return salesStructure;
    }

    @Override
    public String toString() {
        // TODO check debug level?
        StringBuilder str = new StringBuilder();
        if (salesStructure != null) {
            str.append(salesStructure.toString());
        }
        for (CcosEligibilityDataVO data : eligibilityData) {
            str.append(data.toString());
        }
        return str.toString();
    }

}
