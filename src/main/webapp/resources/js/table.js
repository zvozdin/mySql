function initTable(ctx) {
    var url = window.location.href;
    var parts = url.split('/');
    var tableName = parts[parts.length - 1];

    $.get(ctx + '/tables/' + tableName + '/content', function( elements ) {
        $('#loading').hide(300, function(){
            $('#table #temp').tmpl(elements).appendTo('#table .container');
        });
    });
}