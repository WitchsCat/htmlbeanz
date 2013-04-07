package de.tmobile.ecare.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import de.tmobile.cprm.ccos.datatypes.cprm.offering.lSalesorg.LSalesOrg;
//import de.tmobile.cprm.ccos.datatypes.cprm.offering.lSalesorgtree.LSalesOrgTree;
//import de.tmobile.cprm.ccos.datatypes.cprm.offering.sSalesorg.SSalesOrg;
//import de.tmobile.cprm.ccos.datatypes.cprm.offering.sSalesstructure.SSalesStructure;


/**
 * VO to keep information about SalesStructure of sales organization.
 *
 * @author buzlukov
 */
public class CCOSSalesStructureVO implements Serializable {

    /** The sales organization. */
    private String salesOrganization;

    /**
     * VO level in the hierarchy, values for now are
     * -1 (group, the parents are its children)
     * 0 (has no children, i.e. is not a parent of anyone)
     * 1, 2, 3 (parents)
     */
    private int level;

    /** The parents. */
    private List<CCOSSalesStructureVO> parents = new ArrayList<CCOSSalesStructureVO>();

    /** The parent groups. */
    private List<CCOSSalesStructureVO> parentGroups = new ArrayList<CCOSSalesStructureVO>();

    /** The children. */
    private List<CCOSSalesStructureVO> children = new ArrayList<CCOSSalesStructureVO>();

    /**
     * Instantiates a new cCOS sales structure vo.
     *
     * @param sSalesStructure the s sales structure
     */
//    public CCOSSalesStructureVO(SSalesStructure sSalesStructure) {
//        if (sSalesStructure == null) {
//            throw new IllegalArgumentException("SSalesStructure must be non-null");
//        }
//        initFromSSalesStructure(sSalesStructure);
//    }

    /**
     * Instantiates a new cCOS sales structure vo.
     *
     * @param salesOrganization the sales organization
     * @param level the level
     */
    private CCOSSalesStructureVO(String salesOrganization, int level) {
        this.salesOrganization = salesOrganization;
        this.level = level;
    }

    /**
     * Gets the level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the parent groups.
     *
     * @return the parent groups
     */
    public List<CCOSSalesStructureVO> getParentGroups() {
        return parentGroups;
    }

    /**
     * Gets the parents.
     *
     * @return the parents
     */
    public List<CCOSSalesStructureVO> getParents() {
        return parents;
    }

    /**
     * Gets the children.
     *
     * @return the children
     */
    public List<CCOSSalesStructureVO> getChildren() {
        return children;
    }

    /**
     * Gets the sales organization.
     *
     * @return the sales organization
     */
    public String getSalesOrganization() {
        return salesOrganization;
    }

    /**
     * Inits the from s sales structure.
     *
     * @param sSalesStructure the s sales structure
     */
//    private void initFromSSalesStructure(SSalesStructure sSalesStructure) {
//        if (sSalesStructure == null) {
//            return;
//        }
//
//        SSalesOrg so = sSalesStructure.getSalesOrganisation();
//        salesOrganization = so.getSalesOrganisation();
//        level = so.getLevel();
//
//        parents.clear();
//        LSalesOrg sParents = sSalesStructure.getParentSalesOrganisations();
//        for (int i = 0; i < sParents.sizeOfArrayelementArray(); i++) {
//            SSalesOrg sParent = sParents.getArrayelementArray(i);
//            parents.add(new CCOSSalesStructureVO(sParent.getSalesOrganisation(), sParent.getLevel()));
//        }
//
//        parentGroups.clear();
//        LSalesOrg sParentGroups = sSalesStructure.getParentSalesOrgGroups();
//        for (int i = 0; i < sParentGroups.sizeOfArrayelementArray(); i++) {
//            SSalesOrg sParentGroup = sParentGroups.getArrayelementArray(i);
//            parentGroups.add(new CCOSSalesStructureVO(sParentGroup.getSalesOrganisation(), sParentGroup.getLevel()));
//        }
//
//        children.clear();
//        LSalesOrgTree sChildren = sSalesStructure.getChildSalesOrganisations();
//        for (int i = 0; i < sChildren.sizeOfArrayelementArray(); i++) {
//            SSalesOrg sChild = sChildren.getArrayelementArray(i).getSalesOrganisation();
//            children.add(new CCOSSalesStructureVO(sChild.getSalesOrganisation(), sChild.getLevel()));
//        }
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CCOSSalesStructureVO: salesOrganisation=").append(salesOrganization).append("\"");
        sb.append("\nParents: ");
        for (CCOSSalesStructureVO parent : parents) {
            sb.append(parent.salesOrganization).append(",");
        }
        sb.append("\nParent Groups: ");
        for (CCOSSalesStructureVO parentGroup : parentGroups) {
            sb.append(parentGroup.salesOrganization).append(",");
        }
        sb.append("\nChildren: ");
        for (CCOSSalesStructureVO child : children) {
            sb.append(child.salesOrganization).append(",");
        }
        return sb.toString();
    }

}
