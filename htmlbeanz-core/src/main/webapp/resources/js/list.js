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
 * Function initializes the list with specific implementation.
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

    submitGood(id);
}
/**
 * Function describes class element and adds it into the list.
 * @param listId
 * @param className
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
            if (targetList.elements == null) {
                targetList.elements = [];
            }
            data.fieldName = 'element_' + targetList.elements.length;
            data.isEmpty = false;
            targetList.elements.push(data);
            var listElement = createListElement(data, listId);
            ul.appendChild(listElement);
            listSection.removeClass('collapsed');
            markAsChanged(listId + '-' + data.fieldName);
        }
    })
}