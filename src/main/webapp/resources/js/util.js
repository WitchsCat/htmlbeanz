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