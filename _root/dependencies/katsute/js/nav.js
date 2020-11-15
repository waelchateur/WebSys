$(document).ready(function () {
    $('#sidebar-toggler').click(toggleSidebar);
});

function toggleSidebar(e){
    if($('#nav-sidebar').hasClass('show'))
        $('#nav-sidebar').removeClass('show');
    else
        $('#nav-sidebar').addClass('show');
    setCookie("sidebar-show", $('#nav-sidebar').hasClass('show') ? "true" : "false", 0);
}