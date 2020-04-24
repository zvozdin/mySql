$(window).load(function(){
    $.get( "help/content", function(elements) {
        $("#loading").hide(300, function(){
            $('#help row-template').tmpl(elements).appendTo('#help .container');
        });
    });
});