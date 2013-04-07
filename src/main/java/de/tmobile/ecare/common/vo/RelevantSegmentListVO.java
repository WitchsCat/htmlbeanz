package de.tmobile.ecare.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class RelevantSegmentListVO.
 */
public class RelevantSegmentListVO implements Serializable {

	/** The relevant segment list. */
	List<RelevantSegmentVO> relevantSegmentList=new ArrayList<RelevantSegmentVO>();
	
	/** The Constant DELIVERY_CONTEXT. */
	public static final String DELIVERY_CONTEXT = "delivery terms";
	
	/** The Constant STOCK_CONTEXT. */
	public static final String STOCK_CONTEXT = "stock";
	
	/**
	 * Gets the relevant segment list.
	 *
	 * @return the relevant segment list
	 */
	public List<RelevantSegmentVO> getRelevantSegmentList() {
		return relevantSegmentList;
	}

	/**
	 * Sets the relevant segment list.
	 *
	 * @param relevantSegmentList the new relevant segment list
	 */
	public void setRelevantSegmentList(List<RelevantSegmentVO> relevantSegmentList) {
		this.relevantSegmentList = relevantSegmentList;
	}
	
	/**
	 * Gets the delivery segment.
	 *
	 * @return the delivery segment
	 */
	public RelevantSegmentVO getDeliverySegment(){
		for (RelevantSegmentVO relevantSegmentVO : relevantSegmentList) {
			if(DELIVERY_CONTEXT.equals(relevantSegmentVO.getContext())){
				return  relevantSegmentVO;
			}
		}
		return null;
	}
	
	
	/**
	 * Gets the offer segment.
	 *
	 * @return the offer segment
	 */
	public RelevantSegmentVO getOfferSegment(){
		for (RelevantSegmentVO relevantSegmentVO : relevantSegmentList) {
			if(STOCK_CONTEXT.equals(relevantSegmentVO.getContext())){
				return  relevantSegmentVO;
			}
		}
		return null;
	}
	
	/**
	 * Adds the to list.
	 *
	 * @param segmentVO the segment vo
	 */
	public void addToList(RelevantSegmentVO segmentVO){
		relevantSegmentList.add(segmentVO);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
	    if (o == null || !(o instanceof RelevantSegmentListVO)) {
	        return false;
	    }
	    if (o == this) {
	        return true;
	    }
	    RelevantSegmentListVO other = (RelevantSegmentListVO) o;
	    if (relevantSegmentList != null && relevantSegmentList.equals(other.relevantSegmentList)) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
	    return relevantSegmentList == null ? super.hashCode() : relevantSegmentList.hashCode();
	}
	
}
