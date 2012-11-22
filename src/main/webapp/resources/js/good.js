$(document).ready(function () {
    $.getJSON('good', function (data) {
        window.dataModel = data;
        window.bindingMap = new Object();
        window.modifiedArray = [];
        window.htmlTemplates = new Object();
        window.htmlTemplates.clazz = $('#ClazzTemplate');
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

//Function that mirrors the wrapping mechanism on the server side, especially the
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

function doReverse(id) {

}

//Function used to generate a single input based on the object & its ID
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
    var childsLink = $(result).find('button.childs');
    childsLink[0].onclick = function () {
        getChilds(id);
    };
    return result;
}

function generateClazzListBlock(id, object) {
    var result = window.htmlTemplates.clazz.clone();
    result.toggleClass("template");
    result = result[0];
    var header = $(result).find('h4');
    header.html(object.fieldName);
    var classHint = $(result).find('span');
    classHint.html(object.originalClass);
    var listOfElements = $(result).find('ul')[0];
    for (var elementIndex in object.elements) {
        var li = document.createElement('li');
        listOfElements.appendChild(li);
        li.appendChild(doGood(object.elements[elementIndex], id));
    }
    return result;
}

function getChilds(id) {
    var classBlock = $('#' + id);
    var headerBlock = $(classBlock).find("header");
    var spanBlock = $(headerBlock[0]).find("span");
    var dataString = "parent=" + spanBlock[0].innerHTML;
    $.ajax({
        type:"GET",
        url:"subclasses",
        data:dataString,
        success:function (data) {
            childClasses = arrayToString(data);
            alert(childClasses);
        }
    });
}

function arrayToString(array) {
    var result = "";
    for (i=0; i<array.length; i++) {
        result += array[i] + "\n";
    }
    return result;
}
