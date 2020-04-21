$(window).load(function(){
    $.get( "help/content", function(elements) {
        $("#loading").hide(300, function(){
            var container = $("#commands");
            for (var index in elements) {
                var element = elements[index];
                container.append('<a href="' + element.command +'">' + element.command + '</a><br>' +
                        element.description + '<br>');
            }
        });
    });
});