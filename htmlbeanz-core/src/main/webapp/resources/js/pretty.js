/**
 * Method that put a newly created element to the binding map.
 * @param id to assign to the object.
 * @param object Object to put into the map.
 */
function updateBindingMap(id, object, parentId) {
    window.bindingMap[id] = object;
    if (window.parentChildArrayMap[parentId] == null) {
        window.parentChildArrayMap[parentId] = [];
    }
    window.parentChildArrayMap[parentId].push(id);
}
/**
 * Called on input value change, triggers update of the quick access maps to the modified elements
 *
 * @param id of the input that has to trigger an update
 */
function inputChangedValue(id) {
    var changedObject = window.bindingMap[id];
    var input = $('#' + id + ' :input');

    if (changedObject.originalValue == input.val()) {
        unMarkAsChanged(id);
    } else if (changedObject.originalValue != input.val()
        && window.modifiedArray.indexOf(id) != -1) {
        //DO NOTHING
    } else {
        if (!isChangedOrInChangedHierarchy(id)) {
            markAsChanged(id);
        }
    }

}
/**
 * This method checks if the element is part of a hierarchy that is already marked as changed
 * @param id
 * @return true if the above statement is true
 */
function isChangedOrInChangedHierarchy(id) {
    var isModified = window.modifiedArray.indexOf(id) != -1;
    if (isModified) {
        return true;
    } else if (id.indexOf('-') != -1) {
        var parentId = id.substring(0, id.lastIndexOf('-'));
        return isChangedOrInChangedHierarchy(parentId);
    } else {
        return false;
    }
}


/**
 * Marks visually the changes element & updates the changed, created, and deleted lists
 * @param id the element id that has to be marked as changed
 */
function markAsChanged(id) {

    if (window.modifiedArray.indexOf(id) == -1) {
        var p = $('#' + id);
        $(p).addClass("modified");
        $(p).removeClass("saved");
        window.modifiedArray.push(id);
    }
}
/**
 * Removes changed marking from the element and all of its children recursively
 * @param id the element id that has to be unmarked as changed
 */
function unMarkAsChanged(id) {

    //Remove 'changed' marking from the object itself
    if (window.modifiedArray.indexOf(id) != -1) {
        var p = $('#' + id);
        $(p).removeClass("modified");
        window.modifiedArray.splice(window.modifiedArray.indexOf(id), 1);
    }
    //Remove 'changed' marking
    if (window.parentChildArrayMap[id] != null) {
        for (var childIdIndex in window.parentChildArrayMap[id]) {
            unMarkAsChanged(window.parentChildArrayMap[id][childIdIndex]);
        }
    }
}
/**
 * Marks element as saved (green).
 * @param id
 */
function markAsSaved(id) {
    var element = $('#' + id);
    element.addClass('saved');
}
/**
 * Unmarks single element as saved.
 * @param id
 */
function unMarkAsSaved(id) {
    var element = $('#' + id);
    element.removeClass('saved');
}
/**
 * Unmarks element and all it's children as saved.
 * @param id
 */
function unMarkAsSavedRecursively(id) {
    if (window.parentChildArrayMap[id] != null) {
        for (var childIdIndex in window.parentChildArrayMap[id]) {
            unMarkAsSaved(id);
            unMarkAsSavedRecursively(window.parentChildArrayMap[id][childIdIndex])
        }
    }
}
/**
 * Marks element as empty.
 * @param id
 */
function markAsEmpty(id) {
    var element = $('#' + id);
    element.addClass('empty');
}
/**
 * Unmarks element as empty.
 * @param id
 */
function unMarkAsEmpty(id) {
    var element = $('#' + id);
    element.removeClass('empty');
}