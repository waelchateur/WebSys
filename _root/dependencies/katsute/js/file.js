$(document).ready(function () {
    // navigation
    $("#toggle-view").click(toggle);
    // files
    $('.file-link').click(function() {
        return false;
    }).dblclick(function(e) {
        window.location = 
            window.location.pathname +
            (window.location.pathname.endsWith('/') 
                ? '' 
                : '/' )
            + $(e.target).attr("href");
        return false;
    });

    $(".file-link").contextmenu(showMenu);
    $("#files").contextmenu(showMenu);

    $("#files").scroll(hideMenu);
    $(".file-link").click(hideMenu);
    $(document).click(hideMenu);
    $(document).contextmenu(hideMenu);
    $(window).blur(hideMenu);
    $(window).resize(hideMenu);

    $(".context-item").click(context);
});

var selected;

const menu   = $("#context-menu");

// toggle menu
function showMenu(e){
    const y      = e.pageY;
    const x      = e.pageX;
    const width  = menu.width();
    const height = menu.height();

    const top    = (y + height) > window.innerHeight ? y - height : y;
    const left   = (x + width)  > window.innerWidth  ? x - width : x;

    selected     = $(e.target).attr("file-target");
    const type   = $(e.target).attr("file-type");

    menu.find(".dropdown-item").each(function(_, btn){
        var requires = $(btn).attr("file-requires");
        if(
            (requires != null && type != requires) || 
            (requires == "parent" && e.target != $("#files"))
        )
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

function hideMenu(e){
    menu.removeClass("d-block");
}

// handle menu click
function context(e){
    const action = $(e.target).attr("action");
    const params = "width=100,height=200,left=" + e.pageX + ",top=" + e.pageY;
    const popuplink = "/popup?target=" + encodeURIComponent(selected) + "&action=" + action;

    switch(action){
        case "properties":
            open(popuplink, selected, params)
    }
}

// toggle file grid/list view
function toggle(e){
    setCookie("file-grid", getCookie("file-grid") == "true" ? "false" : "true", 0);
    location.reload();
}