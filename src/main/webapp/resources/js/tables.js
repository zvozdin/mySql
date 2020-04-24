$(window).load(function(){
    $.get( "tables/content", function( elements ) {
        $("#loading").hide(300, function(){
            $('#tables #temp').tmpl(elements).appendTo('#tables .containers');
        });
    });
});