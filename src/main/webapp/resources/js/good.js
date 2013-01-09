$(document).ready(function () {
    window.dataModel = new Object();
    window.bindingMap = new Object();
    window.parentChildArrayMap = new Object();
    window.modifiedArray = [];
    window.createdArray = [];
    window.deletedArray = [];
    window.htmlTemplates = new Object();
    window.htmlTemplates.clazz = $('#ClazzTemplate');
    window.htmlTemplates.clazzList = $('#ClazzListTemplate');
    window.htmlTemplates.clazzAttribute = $('#ClazzAttributeTemplate');
    window.search = new Object();
    window.search.clazz = new Object();
    window.search.attribute = new Object();
    window.search.savedState = new Object();
    window.search.newSearch = true;
    window.search.clazzFN = true;
    window.search.clazzOC = false;
    window.search.attributeFN = true;
    window.search.attributeOC = false;
    window.search.attributeOV = true;

    $.getJSON('good', function (data) {
        window.dataModel = data;
        document.getElementById('graphroot').appendChild(doGood(data));
    });
});
/**
 * Function submits all changes to the server as a list of HTTP parameters.
 * POST - DoGoodServlet
 */
//function submitModified() {
//    var data = '';
//    for (var index in window.modifiedArray) {
//        if (index != 0) {
//            data += '&';
//        }
//        var id = window.modifiedArray[index];
//        data += (id + '=' + $('#' + id + ' :input').val());
//    }
//    if (data != '') {
//        $.ajax({
//            type:"POST",
//            url:"good",
//            data:data,
//            success:function (jqXHR) {
//                for (var index in window.modifiedArray) {
//                    $('#' + window.modifiedArray[index]).removeClass("modified");
//                    $('#' + window.modifiedArray[index]).addClass("saved");
//                }
//                window.modifiedArray = [];
//            }
//        });
//    } else {
//        window.alert('no changes to submit!');
//    }
//
//}

/**
 * Function submits the changed element to server.
 * POST - DoGoodServlet
 *
 * @param id of the element that has to be submitted to the server as changed.
 */
function submitGood(id) {
    console.log(id);
    var objectToSubmit = window.bindingMap[id];
    console.log(objectToSubmit);
    syncInputsToObjects(objectToSubmit, id);
    console.log(objectToSubmit);
    //TODO change to submitting only modified parts of the object if possible
    var dataString = id + "=" + JSON.stringify(objectToSubmit);
    $.ajax({
        type: "POST",
        url: "good",
        data: dataString,
        success: function () {
            unMarkAsChanged(id);
            unMarkAsSavedRecursively(id);
            markAsSaved(id);
        }
    });
}
/**
 * This function recursively gets the values from the html inputs and sets them to the object model
 * @param object to put the value into
 * @param id of the input to take the value from
 */
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
            if (input.val() == '') {
                object.isEmpty = true;
                delete object.originalValue;
            } else {
                if (object.isEmpty == true) {
                    unMarkAsEmpty(id);
                }
                object.isEmpty = false;
                object.originalValue = input.val();
            }
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
    var renderControls = true;
    if (parentId == null) {
        parentId = "TOP";
        renderControls = false;
    }
    var id;
    if (object.fieldName != null) {
        id = parentId + '-' + object.fieldName;
    } else {
        id = parentId;
        object.fieldName = parentId;
    }
    updateBindingMap(id, object, parentId);

    var htmlResult;

    if (object.type == 'COMPLEX') {
        htmlResult = generateClazzBlock(id, object, renderControls);
        updateSearchClazz(id, object);
    } else if (object.type == 'LIST') {
        htmlResult = generateClazzListBlock(id, object);
    } else {
        htmlResult = generateClazzAttributeBlock(id, object);
        updateSearchAttribute(id, object);
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

    if (object.isEmpty == true) {
        $(result).addClass('empty');
    } else {
        input.value = object.originalValue;
    }

    input.onblur = function () {
        inputChangedValue(id)
    };
    var saveLink = $(result).find('a.save');
    saveLink[0].onclick = function () {
        submitGood(id);
    };

    return result;
}
/**
 * This function renders a block that represents element type Clazz
 * @param id the full element id that this block will be tied to
 * @param object the model object that is represented by this block
 * @param renderControls if true the block will be collapsed and control buttons will be rendered
 * @return {*}
 */
function generateClazzBlock(id, object, renderControls) {
    var result = window.htmlTemplates.clazz.clone();
    result.toggleClass("template");
    result = result[0];
    var header = $(result).find('h4');
    header.html(object.fieldName);
    //for rendering top element should be uncollapsed
    if (!renderControls) {
        $(result).toggleClass('collapsed');
    } else {
        header[0].onclick = function () {
            $(result).toggleClass("collapsed");
        };
    }

    var classSaveButton = $(result).find('.save')[0];
    if (!renderControls) {
        $(classSaveButton).toggleClass('invisible');
    } else {
        classSaveButton.onclick = function () {
            submitGood(id);
        }
    }

    var classDeleteButton = $(result).find('.delete')[0];
    if (!renderControls) {
        $(classDeleteButton).toggleClass('invisible');
    } else {
        //TODO do the clazz deletion for god sakes!
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