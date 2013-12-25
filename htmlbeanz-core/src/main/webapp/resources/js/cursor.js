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