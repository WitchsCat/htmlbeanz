/**
 * Updates search clazz map.
 * @param id
 * @param clazz
 */
function updateSearchClazz(id, clazz) {
    window.search.clazz[id] = {
        fieldName: clazz.fieldName,
        originalClass: clazz.originalClass
    };
}
/**
 * Updates search attribute map.
 * @param id
 * @param attribute
 */
function updateSearchAttribute(id, attribute) {
    window.search.attribute[id] = {
        fieldName: attribute.fieldName,
        originalClass: attribute.originalClass,
        originalValue: attribute.originalValue
    };
}
/**
 * Performes search action in search maps.
 * @param value - text to search for.
 */
function doSearch(value) {
    if (window.search.newSearch == true) {
        saveState();
        window.search.newSearch = false;
    }
    if (value == '') {
        restoreState();
        removeHighlight();
    } else {
        var clazzFoundArray = [];
        var attributeFoundArray = [];
        // creating found clazz array (search algorithm)
        var clazzId = '';
        for (clazzId in window.search.clazz) {
            // simple contains search realization
            clazzFoundArray[clazzId] = 0;
            if (window.search.clazzFN == true &&
                window.search.clazz[clazzId].fieldName.toLowerCase().indexOf(value.toLowerCase()) != -1) {
                clazzFoundArray[clazzId] += 1;
            }
            if (window.search.clazzOC == true &&
                window.search.clazz[clazzId].originalClass.toLowerCase().indexOf(value.toLowerCase()) != -1) {
                clazzFoundArray[clazzId] += 2;
            }
        }
        // creating found attribute array
        var attributeId = '';
        for (attributeId in window.search.attribute) {
            // simple contains search realization
            attributeFoundArray[attributeId] = 0;
            if (window.search.attributeFN == true &&
                window.search.attribute[attributeId].fieldName.toLowerCase().indexOf(value.toLowerCase()) != -1) {
                attributeFoundArray[attributeId] += 1;
            }
            if (window.search.attributeOC == true &&
                window.search.attribute[attributeId].originalClass.toLowerCase().indexOf(value.toLowerCase()) != -1) {
                attributeFoundArray[attributeId] += 2;
            }
            if (window.search.attributeOV == true &&
                (window.search.attribute[attributeId].originalValue+'').toLowerCase().indexOf(value.toLowerCase()) != -1) {
                attributeFoundArray[attributeId] += 4;
            }
        }
        // collapsing all blocks before showing found elements
        collapseAll();
        // expanding found clazz blocks and highlighting found elements
        for (clazzId in clazzFoundArray) {
            switch(clazzFoundArray[clazzId]) {
                case 1:
                    highlightFoundTextInClazzBlockFieldNameById(clazzId, value);
                    expandClazzBlockById(clazzId);
                    break
                case 2:
                    highlightFoundTextInClazzBlockOriginalClassById(clazzId, value);
                    expandClazzBlockById(clazzId);
                    break
                case 3:
                    highlightFoundTextInClazzBlockFieldNameById(clazzId, value);
                    highlightFoundTextInClazzBlockOriginalClassById(clazzId, value);
                    expandClazzBlockById(clazzId);
                    break
                default:
                    break
            }
        }
        // expanding found attribute blocks and highlighting found elements
        for (attributeId in attributeFoundArray) {
            switch(attributeFoundArray[attributeId]) {
                case 1:
                    highlightFoundTextInAttributeBlockFieldNameById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 2:
                    highlightFoundTextInAttributeBlockOriginalClassById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 3:
                    highlightFoundTextInAttributeBlockFieldNameById(attributeId, value);
                    highlightFoundTextInAttributeBlockOriginalClassById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 4:
                    highlightFoundTextInAttributeBlockOriginalValueById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 5:
                    highlightFoundTextInAttributeBlockFieldNameById(attributeId, value);
                    highlightFoundTextInAttributeBlockOriginalValueById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 6:
                    highlightFoundTextInAttributeBlockOriginalClassById(attributeId, value);
                    highlightFoundTextInAttributeBlockOriginalValueById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                case 7:
                    highlightFoundTextInAttributeBlockFieldNameById(attributeId, value);
                    highlightFoundTextInAttributeBlockOriginalClassById(attributeId, value);
                    highlightFoundTextInAttributeBlockOriginalValueById(attributeId, value);
                    expandClazzBlockById(attributeId);
                    break
                default:
                    break
            }
        }
    }
}
/**
 * Collapses all blocks (sections) in DOM except TOP block.
 */
function collapseAll() {
    $('section').addClass('collapsed');
    $('#TOP').removeClass('collapsed');
}
/**
 * Expands specific block and all it's parent blocks by ID.
 * @param id
 */
function expandClazzBlockById(id) {
    $('#'+id).removeClass('collapsed');
    var parentId = id;
    var indexOfParent = parentId.lastIndexOf('-');
    while (indexOfParent != -1) {
        parentId = parentId.substr(0,indexOfParent);
        indexOfParent = parentId.lastIndexOf('-');
        $('#'+parentId).removeClass('collapsed');
    }
}

function highlightFoundTextInClazzBlockFieldNameById(id, text) {
    highlightFoundTextInElement($('#'+id).find('h4').first(), text);
}

function highlightFoundTextInClazzBlockOriginalClassById(id, text) {
    highlightFoundTextInElement($('#'+id).find('span').first(), text);
}

function highlightFoundTextInAttributeBlockFieldNameById(id, text) {
    highlightFoundTextInElement($('#'+id).find('label').first(), text);
}

function highlightFoundTextInAttributeBlockOriginalClassById(id, text) {
    highlightFoundTextInElement($('#'+id).find('span').first(), text);
}

function highlightFoundTextInAttributeBlockOriginalValueById(id, text) {
    highlightFoundTextInElement($('#'+id).find('input').first(), text);
}
/**
 * Highlights text in element.
 * @param element
 * @param text
 */
function highlightFoundTextInElement(element, text) {
    var textToHighlight = element.html();
    var highlightedText = removeHighlight(textToHighlight);
    highlightedText = addHighlight(highlightedText, text);
    element.html(highlightedText);
}
/**
 * Adds highlight tag to specific text of the original text.
 * @param originalText
 * @param textToHighlight
 * @return String - highlighted text.
 */
function addHighlight(originalText, textToHighlight) {
    return originalText.replace(textToHighlight, '<b class="highlight">'+textToHighlight+'</b>');
}
/**
 * Removes highlight tags of the text.
 * @param text
 * @return String - clean text.
 */
function removeHighlight(text) {
    if (text != null) {
        return text.replace('<b class="highlight">', '').replace('</b>', '')
    }
}
/**
 * Saves current collapse state of blocks.
 */
function saveState() {
    for (id in bindingMap) {
        var element = $('#'+id);
        if (element.hasClass('collapsed')) {
            window.search.savedState[id] = 1;
        } else {
            window.search.savedState[id] = 0;
        }
    }
}
/**
 * Restores collapse state of blocks.
 */
function restoreState() {
    for (id in window.search.savedState) {
        var element = $('#'+id);
        if (window.search.savedState[id] == 1) {
            element.addClass('collapsed');
        } else {
            element.removeClass('collapsed');
        }
        // remove highlight
        var h4 = element.find('h4').first();
        var span = element.find('span').first();
        h4.html(removeHighlight(h4.html()));
        span.html(removeHighlight(span.html()));
    }
}
/**
 * Sets newSearch to true if search input field is empty.
 * @param value
 */
function resetNewSearch(value) {
    if (value == '') {
        window.search.newSearch = true;
    }
}