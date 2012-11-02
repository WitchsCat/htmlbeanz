/**
 * Created with IntelliJ IDEA.
 * User: amatveev
 * Date: 12.10.12
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
$(document).ready(function () {
    $.getJSON('good', function (data) {
        document.getElementById('graphroot').appendChild(doGood(data));
    });
});

function submitGood(id) {
    var p = $('#'+id);
    var input = $('#'+id+' > input');

    var dataString = id + '=' + input.val();
    $.ajax({
        type:"POST",
        url:"good",
        data:dataString,
        success:function () {
            p.style = 'saved';
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

    console.log(object);
    var htmlResult;
    if (object.type == 'COMPLEX') {
        htmlResult = document.createElement('div');
        htmlResult.id = id;
        var header = document.createElement('span');
        header.innerHTML = object.fieldName + '/' + object.originalClass;
        var submitButton = document.createElement('button');
        submitButton.innerHTML = "SAVE";
        submitButton.onclick = function(){
            submitGood(id);
        };
        var ul = document.createElement('ul');
        ul.setAttribute('style', 'border-left: 2px groove Red');
        htmlResult.appendChild(header);
        htmlResult.appendChild(submitButton);
        htmlResult.appendChild(ul);
        for (attributeIndex in object.attributes) {
            var li = document.createElement('li');
            li.appendChild(doGood(object.attributes[attributeIndex], id));
            ul.appendChild(li);
        }
    } else if (object.type == 'LIST') {
        htmlResult = document.createElement('ul');
        htmlResult.id = id;
        for (attributeIndex in object.elements) {
            var attributeItem = document.createElement('li');
            attributeItem.appendChild(doGood(object.elements[attributeIndex], id));
            htmlResult.appendChild(attributeItem);
        }
    } else {
        htmlResult = document.createElement('p');
        htmlResult.id = id;
        var span = document.createElement('span');
        htmlResult.appendChild(span);
        span.innerHTML = object.fieldName;
        var input = document.createElement('input');
        htmlResult.appendChild(input);
        input.value = object.originalValue;
        var button = document.createElement('button');
        htmlResult.appendChild(button);
        button.onclick = function(){
            submitGood(id);
        }
        button.innerHTML = 'SAVE Field';
    }

    return htmlResult;
}

function doReverse(id){

}

