$(document).ready(function () {
    $.getJSON('good', function (data) {
        window.dataModel = data;
        window.bindingMap = new Object();
        window.modifiedArray = [];
        window.htmlTemplates = new Object();
        window.htmlTemplates.clazz = $('#ClazzTemplate');
        window.htmlTemplates.clazzList = $('#ClazzListTemplate');
        window.htmlTemplates.clazzAttribute = $('#ClazzAttributeTemplate');

        document.getElementById('graphroot').appendChild(doGood(data));

    });
});

function updateBindingMap(id, object) {
    window.bindingMap[id] = object;
}

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

    if (object.isEmpty == true) {
        $(result).find('.null').removeClass('invisible');
        $(result).find('.initialize').removeClass('invisible');
        $(result).find('.add-elements').addClass('invisible');
        $(result).find('.elements-impl').addClass('invisible');
    }

    var addListElementButton = $(result).find('.add');
    addListElementButton[0].onclick = function () {
        addElementToList(id);
    }

    // button to show all available elements to add
    var addElementsButton = $(result).find('.add-elements');
    console.log($(addElementsButton));
    addElementsButton[0].onclick = function () {
        loadChildrenClasses(id, object.elementsGenericClass);
    }
    // button to initialize list
    var initializeListButton = $(result).find('.initialize');
    initializeListButton[0].onclick = function () {
        initializeList(id);
    }
    // buttons to confirm/reject initialization act
    var confirmInitializeButton = $(result).find('.confirm-initialize');
    confirmInitializeButton[0].onclick = function () {
        confirmInitializeList(id)
    }
    var rejectInitializeButton = $(result).find('.reject-initialize');
    rejectInitializeButton[0].onclick = function () {
        rejectInitializeList(id);
    }

    // recursively load all children elements
    var listOfElements = $(result).find('ul')[0];
    for (var elementIndex in object.elements) {
        var li = document.createElement('li');
        listOfElements.appendChild(li);
        li.appendChild(doGood(object.elements[elementIndex], id));
    }
    return result;
}

/**
 * Function to check parent class for available children in JVM.
 * GET - SubClassesServlet
 *
 * @param id
 * @param parentClass
 */
function loadChildrenClasses(id, parentClass) {
    var listElementClasses = [];
    var dataString = 'parent=' + parentClass;
    $.ajax({
        type:"GET",
        url:"subclasses",
        data:dataString,
        success:function (data) {
            if (data != '') {
                var element = $('#' + id);
                var selectElementsMenu = $(element).find('select.elements-impl').first();
                for (var childClass in data) {
                    var selectElementsMenuItem = document.createElement('option');
                    $(selectElementsMenu).append(selectElementsMenuItem);
                    selectElementsMenuItem.innerHTML = data[childClass];
                }
                $(element).find('.add-elements').first().addClass('invisible');
                $(element).find('.add').first().removeClass('invisible');
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
    // TODO server call can be implemented in future (or just static list of List impls)
    var implementation = "java.util.ArrayList";

    var listBlock = $('#' + id);
    var selectElementsMenu = $(listBlock).find('select.list-impl').first();
    $(selectElementsMenu).removeClass("invisible");
    var selectElementsMenuItem = document.createElement('option');
    $(selectElementsMenu).append(selectElementsMenuItem);
    selectElementsMenuItem.innerHTML = implementation;

    $(listBlock).find('.confirm-initialize').first().removeClass('invisible');
    $(listBlock).find('.reject-initialize').first().removeClass('invisible');

}

/**
 * Function to send initialize list request to server.
 * Update client and server.
 * PUT - DoGoodServlet
 *
 * @param id
 */
function confirmInitializeList(id) {
    // TODO index should be get from select list
    var selectedItem = 0;
    var listBlock = $('#' + id);
    var selectElementMenuOptions = $(listBlock).find('select.list-impl').first().find('option');

    var implementation = selectElementMenuOptions[selectedItem].value;

    // update client model
    var object = bindingMap[id];
    object.isEmpty = false;
    object.originalClass = implementation;
    // TODO update UI originalClass

    $(listBlock).find('.null').addClass('invisible');
    $(listBlock).find('.initialize').addClass('invisible');
    $(listBlock).find('.list-impl').addClass('invisible');
    $(listBlock).find('.confirm-initialize').addClass('invisible');
    $(listBlock).find('.reject-initialize').addClass('invisible');

    $(listBlock).find('.add-elements').removeClass('invisible');
    $(listBlock).find('.elements-impl').removeClass('invisible');
    $(listBlock).find('.add').removeClass('invisible');

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
 * Function to undo list initialization.
 * @param id
 */
function rejectInitializeList(id) {
    var listBlock = $('#' + id);
    $(listBlock).find('.list-impl').addClass('invisible');
    $(listBlock).find('.list-impl')[0] = null;
    $(listBlock).find('.confirm-initialize').addClass('invisible');
    $(listBlock).find('.reject-initialize').addClass('invisible');
}

/**
 * Function to put new element into the model.
 * @param id
 */
function addElementToList(id) {
    var targetList = window.bindingMap[id];
    var listDiv = $('#'+id);
    var ul = listDiv.find('ul');
    $.ajax({
        type:'GET',
        url:'describe',
        data:'class='+targetList.elementsGenericClass,
        success:function(data){
            ul.append(doGood(data,id));
        }

    })
}