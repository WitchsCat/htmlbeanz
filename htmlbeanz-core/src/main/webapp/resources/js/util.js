/**
 * Parses ID and returns parent ID or null.
 * @param id
 * @return {*}
 */
function getParentIdOrNull(id) {
    if (id.lastIndexOf('-') == -1) {
        return null;
    } else {
        return id.substr(0, id.lastIndexOf('-'));
    }
}

function getIndexInList(id) {
    return getFieldName(id).substr(getFieldName(id).lastIndexOf('element_')+1);
}

function getFieldName(id) {
    return id.substr(id.lastIndexOf('-')+1);
}