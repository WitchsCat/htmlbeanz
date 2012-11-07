$(document).ready(function () {
    $.getJSON('good', function (data) {
        window.dataModel = data;
        window.bindingMap = new Object();
        window.modifiedArray = [];
        document.getElementById('graphroot').appendChild(doGood(data));

    });
});

function updateBindingMap(id, object) {
    window.bindingMap[id] = object;
}

function markAsChanged(id) {
    var p = $('#' + id);
    var input = $('#' + id + ' > input');
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
        data += (id + '=' + $('#' + id + ' > input').val());
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
    var p = $('#' + id);
    var input = $('#' + id + ' > input');

    var dataString = id + '=' + input.val();
    $.ajax({
        type:"POST",
        url:"good",
        data:dataString,
        complete:function (jqXHR) {

        }
    });
}

//Function that mirrors the wrapping mechanism on the server side, especially the
function doGood(object, parentId) {
    if (parentId == null) {
        parentId = "TOP";
    }
    var id
    if (object.fieldName != null) {
        id = parentId + '-' + object.fieldName;
    } else {
        id = parentId;
    }
    updateBindingMap(id, object);
    console.log(object);
    var htmlResult;
    if (object.type == 'COMPLEX') {
        htmlResult = document.createElement('div');
        var header = document.createElement('span');
        header.innerHTML = object.fieldName + '/' + object.originalClass;
        var submitButton = document.createElement('button');
        submitButton.innerHTML = "SAVE";
        submitButton.onclick = function () {
            //TODO not implemented for blocks
            submitGood(id);
        };
        var ulAttributes = document.createElement('ul');
        ulAttributes.setAttribute('style', 'border-left: 2px groove Red');
        htmlResult.appendChild(header);
        htmlResult.appendChild(submitButton);
        htmlResult.appendChild(ulAttributes);
        for (attributeIndex in object.attributes) {
            var li = document.createElement('li');
            ulAttributes.appendChild(li);
            li.appendChild(doGood(object.attributes[attributeIndex], id));
        }
    } else if (object.type == 'LIST') {
        htmlResult = document.createElement('p');
        var headerElements = document.createElement('h4');
        headerElements.innerHTML = object.fieldName + '/' + object.originalClass;
        htmlResult.appendChild(headerElements);
        var ulElements = document.createElement('ul');
        htmlResult.appendChild(ulElements);
        for (attributeIndex in object.elements) {
            var attributeItem = document.createElement('li');
            attributeItem.appendChild(doGood(object.elements[attributeIndex], id));
            ulElements.appendChild(attributeItem);
        }
    } else {
        htmlResult = document.createElement('p');
        var span = document.createElement('span');
        htmlResult.appendChild(span);
        span.innerHTML = object.fieldName;
        var input = document.createElement('input');
        htmlResult.appendChild(input);
        input.value = object.originalValue;
        input.onblur = function () {
            markAsChanged(id);
        }
        var button = document.createElement('button');
        htmlResult.appendChild(button);
        button.onclick = function () {
            submitGood(id);
        }
        button.innerHTML = 'SAVE Field';
    }

    htmlResult.id = id;

    return htmlResult;
}

function doReverse(id) {

}

