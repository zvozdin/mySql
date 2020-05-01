function init(ctx) {

    var fromPage = null;

    var showFromPage = function() {
        window.location.hash = fromPage;
        fromPage = null;
    };

    var isConnected = function(url, onConnected) {
        $.get(ctx + "/connected", function(isConnected) {
            if (isConnected) {
                if (!!onConnected) {
                    onConnected();
                }
            } else {
                fromPage = url
                window.location.hash = '/connect';
            }
        });
    };

    var show = function(selector) {
        var component = $(selector)
        component.find(' .container').children().not(':first').remove();
        component.show();
    };

    var initHelp = function() {
        show('#help');
        $.get(ctx + "/help/content", function(elements) {
            $('#loading').hide(300, function(){
                $('#help script[template="row"]').tmpl(elements).appendTo('#help .container');
            });
        });
    };

    var initMenu = function() {
        show('#menu');
        $.get(ctx + '/menu/content', function( elements ) {
            $('#loading').hide(300, function(){
                $('#menu script[template="row"]').tmpl(elements).appendTo('#menu .container');
            });
        });
    };

    var initConnect = function() {
        $('#database').val("");
        $('#user').val("");
        $('#password').val("");
        $('#error').hide();
        $('#error').html("");
        $('#loading').hide(300, function(){
            $('#connecting-form').show();
        });
    };

    var initTables = function() {
        isConnected('tables', function() {
            show('#tables');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tables script[template="row"]').tmpl(elements).appendTo('#tables .container');
                });
            });
        });
    };

    var initTable = function(tableName) {
        isConnected('table/' + tableName, function() {
            show('#table');
            $.get(ctx + '/tables/' + tableName + '/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#table script[template="row"]').tmpl(elements).appendTo('#table .container');
                });
            });
        });
    };

    var hideAllScreens = function() {
        $('#help').hide();
        $('#menu').hide();
        $('#tables').hide();
        $('#table').hide();
        $('#connecting-form').hide();
    };

    var loadPage = function(data) {
        hideAllScreens();
        $('#loading').show();

        var page = data[0];
        if (page == 'help') {
            initHelp();
        } else if (page == 'menu') {
            initMenu();
        } else if (page == 'tables') {
            initTables();
        } else if (page == 'table') {
            initTable(data[1]);
        }else if (page == 'connect') {
            initConnect();
        } else {
            window.location.hash = '/menu';
        }
    };

    var load = function() {
        var hash = window.location.hash.substring(1);
        var parts = hash.split('/');
        if (parts[0] == '') {
            parts.shift();
        }
        loadPage(parts);
    };

    $(window).bind('hashchange', function(event) {
        load();
    });

    $('#connect').click(function() {
        var connection = {};
        connection.database = $('#database').val();
        connection.user = $('#user').val();
        connection.password = $('#password').val();
        $.ajax({
            url: ctx + '/connect',
            data: connection,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#error').html(message);
                    $('#error').show();
                }
            }
        });
    });

    load();
}