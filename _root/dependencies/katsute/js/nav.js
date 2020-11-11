$(document).ready(function () {
    $('#sidebar-toggler').on('click', function () {
        if($('#nav-sidebar').hasClass('active'))
            $('#nav-sidebar').removeClass('active');
        else
            $('#nav-sidebar').addClass('active');
    });
});