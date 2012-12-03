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
 * Marks visually the changes element & updates the changed, created, and deleted lists
 * @param id the element id that has to be marked as changed
 */
function markAsChanged(id) {
    var p = $('#' + id);
    var input = $('#' + id + ' :input');
    if (window.bindingMap[id].originalValue != input.val()) {
        if (window.modifiedArray.indexOf(id) == -1) {
            $(p).addClass("modified");
            $(p).removeClass("saved");
            window.modifiedArray.push(id);
        }
    } else {
        //Think about removing it from the modified array.
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
    var div = $('#' + id);

    window.bindingMap[id].originalValue = div.find(":input").val();
    var dataString = id + "=" + JSON.stringify(window.bindingMap[id]);
    $.ajax({
        type:"POST",
        url:"good",
        data:dataString,
        success:function (jqXHR) {
            window.modifiedArray.splice(window.modifiedArray.indexOf(id), 1);
            div.removeClass("modified");
            div.addClass("saved");
        }
    });
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
        markAsChanged(id)
    };
    var saveLink = $(result).find('a.save');
    saveLink[0].onclick = function () {
        submitGood(id);
    };
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
        $(result).find('.add-element').first().addClass('invisible');
    } else {
        $(result).find('.initialize').first().addClass('invisible');
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
                        $(uiElement).toggleClass('collapsed');
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
    listSection.find('.initialize').first().addClass('invisible');
    listSection.find('.add-element').first().removeClass('invisible');
    listSection.removeClass('collapsed');

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

            var elementId = 'element_' + targetList.elements.length;
            data.id = listId + '-' + elementId;
            targetList.elements.push(data);
            updateBindingMap(data.id, data);
            ul.appendChild(createListElement(data, elementId));
            submitGood(data.id);
        }
    })
}