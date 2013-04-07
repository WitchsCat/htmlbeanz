package de.tmobile.ecare.common.vo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.tmobile.ecare.common.vo.CCOSCustomerDiscountDefVO;
import de.tmobile.ecare.common.vo.CCOSSelectionContext;
import de.tmobile.ecare.common.vo.RelevantSegmentListVO;

/**
 * <p>Title: eCare</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: T-mobile Deutschland GmbH</p>
 * @author eCare
 * @version 1.0
 * @since 8.1
 */
public class CcosEligibilityDataVO extends Object implements Serializable {
    private static final long serialVersionUID = 1L;

    private String channelCode;

    private String contractNumber;

    private String voAccount;

    private boolean isEligible;

    /**
      * Cost percentage 
      */
    private Float costPercentage;

    RelevantSegmentListVO relevantSegmentList;
    
    // the pricekey for searching of productprice
    private List<String> targetPriceKeys;

    // TODO: this vatiable ant it's usage should be removed, cause of 
    // new structure validationReasons.
    private String reasonCodeDescription;

    private ArrayList<CcosValidationReasonVO> validationReasons;

    private String ccosProcess;

    // Binding data
    Calendar bindingEndDate;

    // if null, check isEligible. else next possible upgrade date
    Calendar ealiestBookingDate;

    Integer prepaymentMonth;
    
    List<CCOSSelectionContext> selectionContexts;

    List<CCOSCustomerDiscountDefVO> customerDiscounts;

    /**
     * Returns cost percentage
     * @return Cost percentage
     */
    public Float getCostPercentage() {
        return costPercentage;
    }

    /**
     * Sets cost percentage
     * @param costPercentage Cost percentage
     */
    public void setCostPercentage(Float costPercentage) {
        this.costPercentage = costPercentage;
    }

    /**
     * @return Returns the channelCode.
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * @param channelCode The channelCode to set.
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    /**
     * @return Returns the contractNumber.
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * @param contractNumber The contractNumber to set.
     */
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * @return Returns the isEligible.
     */
    public boolean isEligible() {
        return isEligible;
    }

    /**
     * @param isEligible The isEligible to set.
     */
    public void setEligible(boolean isEligible) {
        this.isEligible = isEligible;
    }

    /**
     * @return Returns the ','-separated reason codes.
     */
    public String getReasonCodes() {
        if (null == validationReasons) {
            return null;
        }

        StringBuffer res = new StringBuffer();

        for (CcosValidationReasonVO reason : validationReasons) {
            if (res.length() > 0) {
                res.append(',');
            }
            res.append(reason.getCode());
        }

        return new String(res);
    }

    /**
     * @return Returns the process.
     */
    public String getCcosProcess() {
        return ccosProcess;
    }

    /**
     * @param process The process to set.
     */
    public void setCcosProcess(String process) {
        this.ccosProcess = process;
    }

    /**
     * @return Returns the voAccount.
     */
    public String getVoAccount() {
        return voAccount;
    }

    /**
     * @param voAccount The voAccount to set.
     */
    public void setVoAccount(String voAccount) {
        this.voAccount = voAccount;
    }

    public Calendar getBindingEndDate() {
        return bindingEndDate;
    }

    public void setBindingEndDate(Calendar bindingEndDate) {
        this.bindingEndDate = bindingEndDate;
    }

    public Calendar getEaliestBookingDate() {
        return ealiestBookingDate;
    }

    public void setEaliestBookingDate(Calendar ealiestBookingDate) {
        this.ealiestBookingDate = ealiestBookingDate;
    }
        
    public List<CCOSSelectionContext> getSelectionContexts() {
        return selectionContexts;
    }

    public void setSelectionContexts(List<CCOSSelectionContext> selectionContexts) {
        this.selectionContexts = selectionContexts;
    }

    public List<CCOSCustomerDiscountDefVO> getCustomerDiscounts() {
        return customerDiscounts;
    }

    public void setCustomerDiscounts(List<CCOSCustomerDiscountDefVO> customerDiscounts) {
        this.customerDiscounts = customerDiscounts;
    }

    public String toString() {
        return "** "
                + this.getClass().getName()
                + ": "
                + "\nchannelCode: "
                + getChannelCode()
                + ", isEligible: "
                + isEligible()
                + ", costPercentage: "
                + getCostPercentage()
                + ", contractNumber: "
                + getContractNumber()
                + ", voAccount: "
                + getVoAccount()                
                + ", reasonCodes: "
                + getReasonCodes()
                + ", ccosProcess: "
                + getCcosProcess()
                + ", bindingEndDate: "
                + getSelectionContexts()
                + ", \n customerDiscounts: "
                + getCustomerDiscounts();
    }

    /**
     * @return the validationReasons
     */
    public ArrayList<CcosValidationReasonVO> getValidationReasons() {
        return validationReasons;
    }

    /**
     * @param validationReasons the validationReasons to set
     */
    public void setValidationReasons(ArrayList<CcosValidationReasonVO> validationReasons) {
        this.validationReasons = validationReasons;
    }
    
    /**
     * 
     * Returns <code>true</code> if the eligibility data has a validation reason with the given code. 
     * @param code The reason code.
     * @return <code>true</code> if the eligibility data has a validation reason with the given code.
     */
    public boolean containsReasonCode(String code){
        if (validationReasons != null) {
            for (CcosValidationReasonVO reasonVO : validationReasons) {
                if(code.equals(reasonVO.getCode())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return the reasonCodeDescription
     */
    public String getReasonCodeDescription() {
        return reasonCodeDescription;
    }

    /**
     * @param reasonCodeDescription the reasonCodeDescription to set
     */
    public void setReasonCodeDescription(String reasonCodeDescription) {
        this.reasonCodeDescription = reasonCodeDescription;
    }

    /**
     * @return the prepaimentMonth
     */
    public Integer getPrepaymentMonth() {
        return prepaymentMonth;
    }

    /**
     * @param prepaimentMonth the prepaimentMonth to set
     */
    public void setPrepaymentMonth(Integer prepaymentMonth) {
        this.prepaymentMonth = prepaymentMonth;
    }

    /**
     * @return the relevantSegmentList
     */
    public RelevantSegmentListVO getRelevantSegmentList() {
        return relevantSegmentList;
    }

    /**
     * @param relevantSegmentList the relevantSegmentList to set
     */
    public void setRelevantSegmentList(RelevantSegmentListVO relevantSegmentList) {
        this.relevantSegmentList = relevantSegmentList;
    }

    public List<String> getTargetPriceKeys() {
        return targetPriceKeys;
    }

    public void setTargetPriceKeys(List<String> targetPriceKeys) {
        this.targetPriceKeys = targetPriceKeys;
    }

    public void addToTargetPriceKeys(String priceKey){
        if(this.targetPriceKeys==null){
            this.targetPriceKeys = new ArrayList<String>();
        }
        this.targetPriceKeys.add(priceKey);
    }
}
