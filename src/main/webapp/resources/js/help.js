$(window).load(function(){
    $.get( "help/content", function(elements) {
        $("#loading").hide(300, function(){
            $('#help script[template="row"]').tmpl(elements).appendTo('#help .container');
        });
    });
});