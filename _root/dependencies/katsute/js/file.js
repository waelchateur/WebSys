$(document).ready(function () {
    $('.file-link').click(function() {
        return false;
    }).dblclick(function() {
        window.location = this.href;
        return false;
    });

    $(".file-link").on("contextmenu", function(e){
        // todo: add js for dropdown menu
        return false;
    });

    $(document).click(hide);
    $(window).blur(hide);
});

function hide(e){
    // only if already open
}