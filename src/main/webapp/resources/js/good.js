/* Cursor coordinates */
var x, y;

window.onload = init;
function init() {
    document.onmousemove = getCursorXY;
}

function getCursorXY(e) {
    x = (window.Event) ? e.pageX : event.clientX + (document.documentElement.scrollLeft ? document.documentElement.scrollLeft : document.body.scrollLeft);
    y = (window.Event) ? e.pageY : event.clientY + (document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop);
}

$(document).ready(function () {
    $.getJSON('good', function (data) {
        window.dataModel = data;
        window.bindingMap = new Object();
        window.modifiedArray = [];
        window.createdArray = [];
        window.deletedArray = [];
        window.htmlTemplates = new Object();
        window.htmlTemplates.clazz = $('#ClazzTemplate');
        window.htmlTemplates.clazzList = $('#ClazzListTemplate');
        window.htmlTemplates.clazzAttribute = $('#ClazzAttributeTemplate');

        document.getElementById('graphroot').appendChild(doGood(data));

    });
});
/**
 * Method that put a newly created element to the binding map.
 * @param id to assign to the object.
 * @param object Object to put into the map.
 */
function updateBindingMap(id, object) {
    window.bindingMap[id] = object;
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

    var p = $('#' + id);
    if (window.modifiedArray.indexOf(id) == -1) {
        $(p).addClass("modified");
        $(p).removeClass("saved");
        window.modifiedArray.push(id);
    }
}
/**
 * Removes changed marking from the element and all of its children
 * @param id the element id that has to be unmarked as changed
 */
function unMarkAsChanged(id) {
    var p = $('#' + id);
    if (window.modifiedArray.indexOf(id) != -1) {
        $(p).removeClass("modified");
        window.modifiedArray.splice(window.modifiedArray.indexOf(id), 1);
    }
}

/**
 * Function submits all changes to the server as a list of HTTP parameters.
 * POST - DoGoodServlet
 */
function submitModified() {
    var data = '';
    for (var index in window.modifiedArray) {
        if (index != 0) {
            data += '&';
        }
        var id = window.modifiedArray[index];
        data += (id + '=' + $('#' + id + ' :input').val());
    }
    if (data != '') {
        $.ajax({
            type:"POST",
            url:"good",
            data:data,
            success:function (jqXHR) {
                for (var index in window.modifiedArray) {
                    $('#' + window.modifiedArray[index]).removeClass("modified");
                    $('#' + window.modifiedArray[index]).addClass("saved");
                }
                window.modifiedArray = [];
            }
        });
    } else {
        window.alert('no changes to submit!');
    }

}

/**
 * Function submits the changed element to server.
 * POST - DoGoodServlet
 *
 * @param id
 */
function submitGood(id) {

    var objectToSubmit = window.bindingMap[id];
    syncInputsToObjects(objectToSubmit, id);

    var dataString = id + "=" + JSON.stringify(objectToSubmit);
    $.ajax({
        type:"POST",
        url:"good",
        data:dataString,
        success:function (jqXHR) {
            window.modifiedArray.splice(window.modifiedArray.indexOf(id), 1);
            var div = $('#' + id);
            div.removeClass("modified");
            div.addClass("saved");
        }
    });
}

function syncInputsToObjects(object, id) {
    switch (object.type) {
        case 'LIST':
            for (var elementIndex in object.elements) {
                syncInputsToObjects(object.elements[elementIndex],
                    id + '-' + object.elements[elementIndex].fieldName);
            }
            break;
        case 'COMPLEX':
            for (var attributeIndex in object.attributes) {
                syncInputsToObjects(object.attributes[attributeIndex],
                    id + '-' + object.attributes[attributeIndex].fieldName);
            }
            break;
        default :
            var input = $('#' + id + ' input');
            object.isEmpty = false;
            object.originalValue = input.val();
            break;
    }

}

/**
 * Function that mirrors the wrapping mechanism on the server side.
 * @param object
 * @param parentId
 * @return {*}
 */
function doGood(object, parentId) {
    if (parentId == null) {
        parentId = "TOP";
    }
    var id;
    if (object.fieldName != null) {
        id = parentId + '-' + object.fieldName;
    } else {
        id = parentId;
        object.fieldName = parentId;
    }
    updateBindingMap(id, object);

    var htmlResult;

    if (object.type == 'COMPLEX') {
        htmlResult = generateClazzBlock(id, object)
    } else if (object.type == 'LIST') {
        htmlResult = generateClazzListBlock(id, object);
    } else {
        htmlResult = generateClazzAttributeBlock(id, object);
    }
    htmlResult.id = id;

    return htmlResult;
}


/**
 * Function used to generate a single input based on the object & its ID.
 * @param id
 * @param object
 * @return {*}
 */
function generateClazzAttributeBlock(id, object) {
    var result = window.htmlTemplates.clazzAttribute.clone();
    result.toggleClass("template");
    result = result[0];
    var label = $(result).find("label");
    label.html(object.fieldName);
    var hint = $(result).find('.field-wrap > span');
    hint.html(object.originalClass);
    var input = $(result).find(":input")[0];
    input.value = object.originalValue;
    input.onblur = function () {
        inputChangedValue(id)
    };
    var saveLink = $(result).find('a.save');
    saveLink[0].onclick = function () {
        submitGood(id);
    };
    if (object.isEmpty == true) {
        $(result).addClass('empty');
    }
    return result;
}

function generateClazzBlock(id, object) {
    var result = window.htmlTemplates.clazz.clone();
    result.toggleClass("template");
    result = result[0];
    var header = $(result).find('h4');
    header.html(object.fieldName);


    header[0].onclick = function () {
        $(result).toggleClass("collapsed");
    };

    var classSaveButton = $(result).find('.save')[0];
    classSaveButton.onclick = function () {
        submitGood(id);
    }

    var classHint = $(result).find('span');
    classHint.html(object.originalClass);
    var listOfAttributes = $(result).find('ul')[0];
    for (var attributeIndex in object.attributes) {
        var li = document.createElement('li');
        listOfAttributes.appendChild(li);
        li.appendChild(doGood(object.attributes[attributeIndex], id));
    }

    return result;
}

function generateClazzListBlock(id, object) {
    var result = window.htmlTemplates.clazzList.clone();
    result.toggleClass("template");
    result = result[0];
    var header = $(result).find('h4');
    header.html(object.fieldName);
    var classHint = $(result).find('.original-class');
    classHint.html(object.originalClass);

    header[0].onclick = function () {
        $(result).toggleClass("collapsed");
    };

    if (object.isEmpty == true) {
        $(result).addClass("empty");
    }

    // button to show all available elements to add
    var addElementsButton = $(result).find('.add-element');
    addElementsButton[0].onclick = function () {
        loadChildrenClasses(id, object.elementsGenericClass, result);
    }

    // button to initialize list
    var initializeListButton = $(result).find('.initialize');
    initializeListButton[0].onclick = function () {
        initializeList(id);
    }

    // recursively load all children elements
    var listOfElements = $(result).find('ul')[0];
    for (var elementIndex in object.elements) {
        var li = createListElement(object.elements[elementIndex], id)
        listOfElements.appendChild(li);
    }
    return result;
}

/**
 * Creates a li element wrapper
 * @param object to create wrapper based on
 * @param id of the element
 * @return {Element}
 */
function createListElement(object, id) {
    var li = document.createElement('li');
    li.appendChild(doGood(object, id));
    return li;
}

/**
 * Function to check parent class for available children in JVM.
 * GET - SubClassesServlet
 *
 * @param id
 * @param parentClass
 */
function loadChildrenClasses(id, parentClass, uiElement) {
    var listElementClasses = [];
    var dataString = 'parent=' + parentClass;
    $.ajax({
        type:"GET",
        url:"subclasses",
        data:dataString,
        success:function (data) {
            if (data != '') {
                var listSection = $('#' + id);
                var popupDiv = listSection.find('.popup').first();
                popupDiv.empty();
                for (var index in data) {
                    var childClass = document.createElement('span');
                    var childClassName = data[index];
                    childClass.onclick = function () {
                        addElementToList(id, childClassName);
                        popupDiv.hide();
                        // TODO childClassName passing to function is always the last one (javascript language specifics)
                    }
                    $(childClass).addClass('row');
                    popupDiv.append(childClass);
                    childClass.innerHTML = childClassName;
                }
                popupDiv.css('top', y);
                popupDiv.css('left', x);
                popupDiv.show();
            } else {
                alert('no elements found.. fixmeplease!');
            }
        }
    });
}

/**
 * Function to perform initialize list act.
 * @param id
 */
function initializeList(id) {
    var implementation = "java.util.ArrayList";

    var listSection = $('#' + id);
    listSection.find('.original-class').first().html(implementation);
    listSection.removeClass('empty');

    // update client model
    var object = bindingMap[id];
    object.isEmpty = false;
    object.originalClass = implementation;

    put(id);
}

/**
 * Function to send initialize list request to server.
 * Update client and server.
 * PUT - DoGoodServlet
 *
 * @param id
 */
function put(id) {
    var object = bindingMap[id];
    // update server model
    var dataString = id + '=' + JSON.stringify(object);
    $.ajax({
        type:"PUT",
        url:"good",
        data:dataString,
        success:function (data) {

        }
    });
}

/**
 * Function to put new element into the model.
 * @param id
 */
function addElementToList(listId, className) {
    var targetList = window.bindingMap[listId];
    var listSection = $('#' + listId);
    var ul = listSection.find('ul')[0];
    $.ajax({
        type:'GET',
        url:'describe',
        data:'class=' + className,
        success:function (data) {
            data.fieldName = 'element_' + targetList.elements.length
            targetList.elements.push(data);
            var listElement = createListElement(data, listId);
            ul.appendChild(listElement);
            listSection.removeClass('collapsed');
            markAsChanged(listId + '-' + data.fieldName);
        }
    })
}