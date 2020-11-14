$(document).ready(function () {
    $('.file-link').click(function() {
        return false;
    }).dblclick(function() {
        window.location = this.href;
        return false;
    });

    $(".file-link").contextmenu(show);
    $("#files").contextmenu(show);

    $("#files").scroll(hide);
    $(document).click(hide);
    $(document).contextmenu(hide);
    $(window).blur(hide);
    $(window).resize(hide)
});

var selected;

const menu   = $("#context-menu");

function show(e){
    e.stopPropagation();
    
    var y      = e.pageY;
    var x      = e.pageX;
    var width  = menu.width();
    var height = menu.height();

    var top    = (y + height) > window.innerHeight ? y - height : y;
    var left   = (x + width)  > window.innerWidth  ? x - width : x;

    selected   = $(e.target).attr("file-target");
    var dir    = $(e.target).attr("file-dir");

    console.log(e.target);

    $("#context-menu").find(".dropdown-item").each(function(_, btn){
        var file_dir = $(btn).attr("file-dir");
        if(file_dir != null && file_dir != dir)
            $(btn).addClass("disabled");
        else
            $(btn).removeClass("disabled");
    });

    menu.css({
        top: top,
        left: left
    }).addClass("d-block");
    return false;
}

function hide(e){
    $("#context-menu").removeClass("d-block");
}