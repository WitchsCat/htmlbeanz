package de.tmobile.ecare.common.vo;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.math.NumberUtils;


/**
 * This Object serves for matching CCOS catalogue offers (without customer context) to a customer.
 *
 * See: getOffersBulk, isBusinessProcessEligible for details
 * 
 * @author meistere
 * @version 1.1 
 *
 */

public class CCOSSelectionContext implements Serializable {
    
    /** The Constant PAYLOAD_DELIMITER. */
    public static final char PAYLOAD_DELIMITER = ':';
    
    /** The Constant SESSIONKEY_CCOS_SELECTIONCONTEXT_OFFERFOLDER. */
    public static final String SESSIONKEY_CCOS_SELECTIONCONTEXT_OFFERFOLDER = "ccos.selectioncontext.offerfolder";

    /** The Constant SESSIONKEY_CCOS_SELECTIONCONTEXT_LEADSOURCEID. */
    public static final String SESSIONKEY_CCOS_SELECTIONCONTEXT_LEADSOURCEID = "ccos.selectioncontext.leadsourceid";

    /** The Constant SESSIONKEY_CCOS_SELECTIONCONTEXT_LEADSOURCE. */
    public static final String SESSIONKEY_CCOS_SELECTIONCONTEXT_LEADSOURCE = "ccos.selectioncontext.leadsource";

    /** The Constant SESSIONKEY_CCOS_SELECTIONCONTEXT_ISOFFERFOLDEROVERWRITE. */
    public static final String SESSIONKEY_CCOS_SELECTIONCONTEXT_ISOFFERFOLDEROVERWRITE = "ccos.selectioncontext.isofferfolderoverwrite";

    /** The Constant SESSIONKEY_CCOS_SELECTIONCONTEXT_CAMPAIGNCODE. */
    public static final String SESSIONKEY_CCOS_SELECTIONCONTEXT_CAMPAIGNCODE = "ccos.selectioncontext.campaigncode";

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** The selection context id. */
    private Long selectionContextId;
    
//    Do we really need a back-reference to offers ?
//    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "selectionContextList")
//    private List<CCOSOfferDefVO> offers;
    
    /** The campaign code. */
private String campaignCode;
    
    /** The campaign alert. */
    private String campaignAlert;

    /** The campaign execution type. */
    private Integer campaignExecutionType;
    
    /** The lead source. */
    private String leadSource;
    
    /** The lead source id. */
    private String leadSourceId;
    
    /** The offer folder. */
    private String offerFolder;
    
    /** The use offer folder overwrite. */
    private Boolean useOfferFolderOverwrite;
    
    /** The offer instance number. */
    private String offerInstanceNumber;
       
    /** The additional information. */
    private String additionalInformation;
    
    // transient attributes
    
    /** The new binding start. */
    private Calendar newBindingStart;
    
    /** The ccos process. */
    private String ccosProcess;
   
    /** The relevant segment list vo. */
    private RelevantSegmentListVO relevantSegmentListVO;

    /**
     * Gets the campaign code.
     *
     * @return the campaignCode
     */
    public String getCampaignCode() {
        return campaignCode;
    }
    
    /**
     * Sets the campaign code.
     *
     * @param campaignCode the campaignCode to set
     */
    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }
    
    /**
     * Gets the lead source.
     *
     * @return the leadSource
     */
    public String getLeadSource() {
        return leadSource;
    }
    
    /**
     * Sets the lead source.
     *
     * @param leadSource the leadSource to set
     */
    public void setLeadSource(String leadSource) {
        this.leadSource = leadSource;
    }
    
    /**
     * Gets the lead source id.
     *
     * @return the leadSourceId
     */
    public String getLeadSourceId() {
        return leadSourceId;
    }
    
    /**
     * Sets the lead source id.
     *
     * @param leadSourceId the leadSourceId to set
     */
    public void setLeadSourceId(String leadSourceId) {
        this.leadSourceId = leadSourceId;
    }
    
    /**
     * Gets the offer folder.
     *
     * @return the offerFolder
     */
    public String getOfferFolder() {
        return offerFolder;
    }
    
    /**
     * Sets the offer folder.
     *
     * @param offerFolder the offerFolder to set
     */
    public void setOfferFolder(String offerFolder) {
        this.offerFolder = offerFolder;
    }
    
    /**
     * Gets the offer instance number.
     *
     * @return the offerInstanceNumber
     */
    public String getOfferInstanceNumber() {
        return offerInstanceNumber;
    }
    
    /**
     * Sets the offer instance number.
     *
     * @param offerInstanceNumber the offerInstanceNumber to set
     */
    public void setOfferInstanceNumber(String offerInstanceNumber) {
        this.offerInstanceNumber = offerInstanceNumber;
    }
    
    /**
     * Gets the use offer folder overwrite.
     *
     * @return the useOfferFolderOverwrite
     */
    public Boolean getUseOfferFolderOverwrite() {
        return useOfferFolderOverwrite;
    }
    
    /**
     * Sets the use offer folder overwrite.
     *
     * @param useOfferFolderOverwrite the useOfferFolderOverwrite to set
     */
    public void setUseOfferFolderOverwrite(Boolean useOfferFolderOverwrite) {
        this.useOfferFolderOverwrite = useOfferFolderOverwrite;
    }
    
    /**
     * Gets the additional information.
     *
     * @return the additionalInformation
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }
    
    /**
     * Sets the additional information.
     *
     * @param additionalInformation the additionalInformation to set
     */
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
               
    /**
     * Gets the new binding start.
     *
     * @return the new binding start
     */
    public Calendar getNewBindingStart() {
        return newBindingStart;
    }
    
    /**
     * Sets the new binding start.
     *
     * @param newBindingStart the new new binding start
     */
    public void setNewBindingStart(Calendar newBindingStart) {
        this.newBindingStart = newBindingStart;
    }
    
    /**
     * Gets the selection context id.
     *
     * @return the selectionContextId
     */
    public Long getSelectionContextId() {
        return selectionContextId;
    }
    
    /**
     * Sets the selection context id.
     *
     * @param selectionContextId the selectionContextId to set
     */
    public void setSelectionContextId(Long selectionContextId) {
        this.selectionContextId = selectionContextId;
    }
   
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
 
        result = PRIME * result + ((campaignCode == null) ? 0 : campaignCode.hashCode());
        result = PRIME * result + ((leadSource == null) ? 0 : leadSource.hashCode());
        
        result = PRIME * result + ((offerFolder == null) ? 0 : offerFolder.hashCode());
        result = PRIME * result + ((offerInstanceNumber == null) ? 0 : offerInstanceNumber.hashCode());
        result = PRIME * result + ((useOfferFolderOverwrite == null) ? 0 : useOfferFolderOverwrite.hashCode());
        return result;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CCOSSelectionContext other = (CCOSSelectionContext)obj;
        if (campaignCode == null) {
            if (other.campaignCode != null) {
                return false;
            }
        } else if (!campaignCode.equals(other.campaignCode)) {
            return false;
        }
        if (leadSource == null) {
            if (other.leadSource != null) {
                return false;
            }
        } else if (!leadSource.equals(other.leadSource)) {
            return false;
        }
        
        if (offerFolder == null) {
            if (other.offerFolder != null) {
                return false;
            }
        } else if (!offerFolder.equals(other.offerFolder)) {
            return false;
        }
        if (offerInstanceNumber == null) {
            if (other.offerInstanceNumber != null) {
                return false;
            }
        } else if (!offerInstanceNumber.equals(other.offerInstanceNumber)) {
            return false;
        }
        if (useOfferFolderOverwrite == null) {
            if (other.useOfferFolderOverwrite != null) {
                return false;
            }
        } else if (!useOfferFolderOverwrite.equals(other.useOfferFolderOverwrite)) {
            return false;
        }
        return true;
    }
  
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String deliveryCustomerType = "";
        String deliverySegementClass = "";
        String deliverySegmentString = "";

        String offerCustomerType = "";
        String offerSegementClass = "";
        String offerSegmentString = "";

        if (getRelevantSegmentListVO() != null) {
            if (getRelevantSegmentListVO().getDeliverySegment() != null) {
                if (getRelevantSegmentListVO().getDeliverySegment().getCustomerType() != null) {
                    deliveryCustomerType = "" + getRelevantSegmentListVO().getDeliverySegment().getCustomerType();
                }

                if (getRelevantSegmentListVO().getDeliverySegment().getSegmentClass() != null) {
                    deliverySegementClass = "" + getRelevantSegmentListVO().getDeliverySegment().getSegmentClass();
                }

                if (getRelevantSegmentListVO().getDeliverySegment().getSegmentString() != null) {
                    deliverySegmentString = getRelevantSegmentListVO().getDeliverySegment().getSegmentString();
                }
            }

            if (getRelevantSegmentListVO().getOfferSegment() != null) {

                if (getRelevantSegmentListVO().getOfferSegment().getCustomerType() != null) {
                    offerCustomerType = "" + getRelevantSegmentListVO().getOfferSegment().getCustomerType();
                }

                if (getRelevantSegmentListVO().getOfferSegment().getSegmentClass() != null) {
                    offerSegementClass = "" + getRelevantSegmentListVO().getOfferSegment().getSegmentClass();
                }

                if (getRelevantSegmentListVO().getOfferSegment().getSegmentString() != null) {
                    offerSegmentString = getRelevantSegmentListVO().getOfferSegment().getSegmentString();
                }
            }
        }
    
        return "** " + this.getClass().getName() + ": " +
        " selectionContextId: " + getSelectionContextId() +
        ",\n CAMPAIGN_CODE: " + getCampaignCode() +
        ", LEAD_SOURCE: " + getLeadSource() +
        ", LEAD_SOURCE_ID: " + getLeadSourceId() +
        ", OFFER_FOLDER: " + getOfferFolder() +
        ", IS_OFFER_FOLDER_OVERWRITE: " + getUseOfferFolderOverwrite() +
        ", OFFER_INSTANCE_NR: " + getOfferInstanceNumber() +
        ", Delivery CUSOTMER_TYPE: " +  deliveryCustomerType +
        ", Delivery SEGMENT_CLASS: " + deliverySegementClass +
        ", Delivery SEGMENT_STRING: " + deliverySegmentString +
        ", Offer CUSOTMER_TYPE: " + offerCustomerType +
        ", Offer SEGMENT_CLASS: " + offerSegementClass +
        ", Offer SEGMENT_STRING: " + offerSegmentString;
    }
    
    /** The Constant CRYPTO_PARAMETERS_NUMBER1. */
    private final static int CRYPTO_PARAMETERS_NUMBER1=5;
    
    /** The Constant CRYPTO_PARAMETERS_NUMBER2. */
    private final static int CRYPTO_PARAMETERS_NUMBER2=3;
    
    /**
     * To payload string1.
     *
     * @return the string
     */
    public String toPayloadString1() {
        String[] elements = new String[CRYPTO_PARAMETERS_NUMBER1];

        elements[0] = toPayloadStr(getCampaignCode());
        elements[1] = toPayloadStr(getLeadSource());
        elements[2] = toPayloadStr(getLeadSourceId());
        elements[3] = toPayloadStr(getOfferFolder());
        elements[4] = toPayloadStr(getUseOfferFolderOverwrite());

        //        elements[5] = toPayloadStr(getSelectionContextId());
        //        elements[6] = toPayloadStr(getOfferInstanceNumber());

       
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i < elements.length - 1) {
                sb.append(PAYLOAD_DELIMITER);
            }
        }

        return sb.toString();
    }
    
    /**
     * To payload string2.
     *
     * @return the string
     */
    public String toPayloadString2() {
        String[] elements = new String[CRYPTO_PARAMETERS_NUMBER2];

        if (getRelevantSegmentListVO() != null) {
            final RelevantSegmentVO deliverySegment = getRelevantSegmentListVO().getDeliverySegment();
            if (deliverySegment != null) {
                elements[0] = toPayloadStr(deliverySegment.getCustomerType());
                elements[1] = toPayloadStr(deliverySegment.getSegmentClass());
                elements[2] = toPayloadStr(deliverySegment.getSegmentString());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i]);
            if (i < elements.length - 1) {
                sb.append(PAYLOAD_DELIMITER);
            }
        }

        return sb.toString();
    }
    
    /**
     * To payload str.
     *
     * @param o the o
     * @return the string
     */
    private static String toPayloadStr(Object o) {
        return o == null ? "" : o.toString();
    }
    
    /**
     * From payload string.
     *
     * @param str the str
     * @return the long
     */
    private static Long fromPayloadString(String str) {
        if ("".equals(str)) {
            return NumberUtils.toLong(null);
        }
        return NumberUtils.toLong(str);
    }

    /**
     * Creates the from payload strings.
     *
     * @param payload1 the payload1
     * @param payload2 the payload2
     * @return the cCOS selection context
     */
    public static CCOSSelectionContext createFromPayloadStrings(String payload1, String payload2) {
        CCOSSelectionContext ret = new CCOSSelectionContext();
        String[] elements1 = payload1.split("" + PAYLOAD_DELIMITER, CRYPTO_PARAMETERS_NUMBER1);
        String[] elements2 = payload2.split("" + PAYLOAD_DELIMITER, CRYPTO_PARAMETERS_NUMBER2);

        ret.setCampaignCode(elements1[0]);
        ret.setLeadSource(elements1[1]);
        ret.setLeadSourceId(elements1[2]);
        ret.setOfferFolder(elements1[3]);
        try {
            ret.setUseOfferFolderOverwrite(Boolean.parseBoolean(elements1[4]));
        } catch (Exception e) {
        }
        
        
        RelevantSegmentListVO list= new RelevantSegmentListVO();
        ret.setRelevantSegmentListVO(list);
        
        try{
            Long customerType = fromPayloadString(elements2[0]);
            Long segmentClass = fromPayloadString(elements2[1]);
            String segmentString = elements2[2];

            /**
             * if there were no number-format-exception still, we can create a RelevantSegmentVO.
             */
            
            RelevantSegmentVO rsvo=new RelevantSegmentVO();
            rsvo.setContext(RelevantSegmentListVO.DELIVERY_CONTEXT);
            list.addToList(rsvo);
            
            rsvo.setCustomerType(customerType);
            rsvo.setSegmentClass(segmentClass);
            rsvo.setSegmentString(segmentString);
            
        }catch (Exception e) {
            //number format exception is possible
        }
        
//        try {
//            ret.setSelectionContextId(Long.parseLong(elements[?]));
//        } catch (Exception e) {
//        }
//        ret.setOfferInstanceNumber(elements[6]);

        return ret;
    }
    
    
	/**
	 * Gets the relevant segment list vo.
	 *
	 * @return the relevant segment list vo
	 */
	public RelevantSegmentListVO getRelevantSegmentListVO() {
        return relevantSegmentListVO;
    }

    /**
     * Sets the relevant segment list vo.
     *
     * @param relevantSegmentListVO the new relevant segment list vo
     */
    public void setRelevantSegmentListVO(RelevantSegmentListVO relevantSegmentListVO) {
        this.relevantSegmentListVO = relevantSegmentListVO;
    }

    /**
     * Gets the campaign alert.
     *
     * @return the campaign alert
     */
    public String getCampaignAlert() {
        return campaignAlert;
    }

    /**
     * Sets the campaign alert.
     *
     * @param campaignAlert the new campaign alert
     */
    public void setCampaignAlert(String campaignAlert) {
        this.campaignAlert = campaignAlert;
    }

    /**
     * Gets the campaign execution type.
     *
     * @return the campaign execution type
     */
    public Integer getCampaignExecutionType() {
        return campaignExecutionType;
    }

    /**
     * Sets the campaign execution type.
     *
     * @param campaignExecutionType the new campaign execution type
     */
    public void setCampaignExecutionType(Integer campaignExecutionType) {
        this.campaignExecutionType = campaignExecutionType;
    }
    
    /**
     * Gets the ccos process.
     *
     * @return the ccos process
     */
    public String getCcosProcess() {
        return ccosProcess;
    }
    
    /**
     * Sets the ccos process.
     *
     * @param ccosProcess the new ccos process
     */
    public void setCcosProcess(String ccosProcess) {
        this.ccosProcess = ccosProcess;
    }
}


