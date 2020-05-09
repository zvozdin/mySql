function init(ctx) {

    var fromPage = null;

    var showFromPage = function() {
        window.location.hash = fromPage;
        fromPage = null;
    };

    var isConnected = function(url, onConnected) {
        $.get(ctx + "/connected", function(userName) {
            if (userName == "") {
                fromPage = url
                window.location.hash = '/connect';
            } else {
                if (!!onConnected) {
                    onConnected(userName);
                }
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

    var initActions = function() {
        isConnected('actions/', function(userName) {
            show('#actions');
            $.get(ctx + '/actions/' + userName + '/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#actions script[template="row"]').tmpl(elements).appendTo('#actions .container');
                });
            });
        });
    };

    var initCreateDatabase = function() {
        isConnected('newDatabase', function() {
            $('#createDatabaseName').val("");
            $('#reportCreateDatabase').hide();
            $('#reportCreateDatabase').html("");
            $('#loading').hide(300, function(){
                $('#createDatabaseForm').show();
            });
        });
    };

    function Action(action, data) {
        this.action=action;
        this.data=data;
    }

    var initDatabasesForDrop = function() {
        isConnected('dropDatabase', function() {
            show('#tablesForAction');
            $.get(ctx + '/dropDatabase/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('droppingTheDatabase', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initDropDatabase = function(databaseName) {
        $.ajax({
            url: ctx + '/dropDatabase/' + databaseName,
            type: 'DELETE',
            success: function(message) {
                $('#report').html(message);
                $('#loading').hide();
                $('#report').show();
            }
        });
    };

    var initTablesForDrop = function() {
        isConnected('dropTable', function() {
            show('#tablesForAction');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('droppingTheTable', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initDropTable = function(tableName) {
        $.ajax({
            url: ctx + '/dropTable/' + tableName,
            type: 'DELETE',
            success: function(message) {
                $('#report').html(message);
                $('#loading').hide();
                $('#report').show();
            }
        });
    };

    var initTablesForClear = function() {
        isConnected('clear', function() {
            show('#tablesForAction');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('clearingTheTable', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initClearTable = function(tableName) {
        $.ajax({
            url: ctx + '/clear/' + tableName,
            type: 'DELETE',
            success: function(message) {
                $('#report').html(message);
                $('#loading').hide();
                $('#report').show();
            }
        });
    };

    var initTablesForInsert = function() {
        isConnected('insert', function() {
            show('#tablesForAction');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('insertingTheTable', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initInsertTable = function(tableName) {
        $('#namesRow').children().remove();
        $('#inputRow').children().remove();
        $('#insertForm').append('<input type="hidden" name="tableName" value="' + tableName + '"/>');
        $.get(ctx + '/insert/' + tableName + '/content', function( elements ) {
            $('#loading').hide(300, function(){
                $('#insertForm script[template="row"]').tmpl(elements).appendTo('#namesRow');
                $('#insertForm script[template="row-input"]').tmpl(elements).appendTo('#inputRow');
                $('#insertForm').show();
            });
        });
    };

    var initTablesForUpdate = function() {
        isConnected('update', function() {
            show('#tablesForAction');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('updatingTheTable', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initUpdateTable = function(tableName) {
        $('#setColumn').children().remove();
        $('#whereColumn').children().remove();
        $('#setValue').val("");
        $('#whereValue').val("");
        $('#updateForm').append('<input type="hidden" name="tableName" value="' + tableName + '"/>');
        $.get(ctx + '/update/' + tableName + '/content', function( elements ) {
            $('#loading').hide(300, function(){
                $('#updateForm script[template="row"]').tmpl(elements).appendTo('#setColumn');
                $('#updateForm script[template="row"]').tmpl(elements).appendTo('#whereColumn');
                $('#updateForm').show();
            });
        });
    };

    var initTablesForDelete = function() {
        isConnected('delete', function() {
            show('#tablesForAction');
            $.get(ctx + '/tables/content', function( elements ) {
                $('#loading').hide(300, function(){
                    $('#tablesForAction script[template="row"]')
                        .tmpl(new Action('deletingTheTable', elements))
                        .appendTo('#tablesForAction .container');
                });
            });
        });
    };

    var initDeleteTable = function(tableName) {
        $('#deleteColumn').children().remove();
        $('#deleteValue').val("");
        $('#deleteForm').append('<input type="hidden" name="tableName" value="' + tableName + '"/>');
        $.get(ctx + '/delete/' + tableName + '/content', function( elements ) {
            $('#loading').hide(300, function(){
                $('#deleteForm script[template="row"]').tmpl(elements).appendTo('#deleteColumn');
                $('#deleteForm').show();
            });
        });
    };

    var initCreateTableSetName = function() {
        isConnected('newTable', function() {
           $('#loading').hide();
           $('#createTableName').val("");
           $('#columnsCount').val("");
           $('#createTableSetNameForm').show();
        });
    };

    var hideAllScreens = function() {
        $('#actions').hide();
        $('#connecting-form').hide();
        $('#createDatabaseForm').hide();
        $('#createTableSetColumnsForm').hide();
        $('#createTableSetNameForm').hide();
        $('#deleteForm').hide();
        $('#help').hide();
        $('#insertForm').hide();
        $('#menu').hide();
        $('#report').hide();
        $('#table').hide();
        $('#tables').hide();
        $('#tablesForAction').hide();
        $('#updateForm').hide();
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
        } else if (page == 'connect') {
            initConnect();
        } else if (page == 'actions') {
            initActions();
        } else if (page == 'newDatabase') {
            initCreateDatabase();
        } else if (page == 'dropDatabase') {
            initDatabasesForDrop();
        } else if (page == 'droppingTheDatabase') {
            initDropDatabase(data[1]);
        } else if (page == 'dropTable') {
            initTablesForDrop();
        } else if (page == 'droppingTheTable') {
            initDropTable(data[1]);
        } else if (page == 'clear') {
            initTablesForClear();
        } else if (page == 'clearingTheTable') {
            initClearTable(data[1]);
        } else if (page == 'newTable') {
            initCreateTableSetName();
        } else if (page == 'insert') {
            initTablesForInsert();
        } else if (page == 'insertingTheTable') {
            initInsertTable(data[1]);
        } else if (page == 'update') {
            initTablesForUpdate();
        } else if (page == 'updatingTheTable') {
            initUpdateTable(data[1]);
        } else if (page == 'delete') {
            initTablesForDelete();
        } else if (page == 'deletingTheTable') {
            initDeleteTable(data[1]);
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

    load();

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
                    $('#report').html(message);
                    $('#report').show();
                }
            }
        });
    });

    $('#createDatabase').click(function() {
        $.ajax({
            url: ctx + '/newDatabase/' + $('#createDatabaseName').val(),
            type: 'PUT',
            success: function(message) {
                $('#createDatabaseForm').hide();
                $('#report').html(message);
                $('#report').show();
            }
        });
    });

    $('#setColumns').click(function() {
        var tableName = $('#createTableName').val();
        var columnsCount = $('#columnsCount').val();

        $('#createTableSetColumnsForm .container').empty();

        for(var j = 1; j <= columnsCount; j++){
            $('#createTableSetColumnsForm .container').append('Column#' + j + '<br>');
            $('#createTableSetColumnsForm .container')
                .append('<input type="text" name="column' + j +'" id="column' + j +'" /><br>');
        }
        $('#createTableSetColumnsForm').append('<input type="hidden" name="tableName" value="' + tableName + '"/>');

        $('#createTableSetNameForm').hide();
        $('#createTableSetColumnsForm').show();
    });

    $('#createTable').click(function() {
        $('#createTableSetColumnsForm').hide();
        $('#loading').show();
        var divData = $("#createTableSetColumnsForm :input").serializeArray();
        var data = {};
        $(divData).each(function(index, obj){
            data[obj.name] = obj.value;
        });

        $.ajax({
            url: ctx + '/newTable',
            data: divData,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#loading').hide();
                    $('#report').html(message);
                    $('#report').show();
                }
            }
        });
    });

    $('#insert').click(function() {
        $('#insertForm').hide();
        $('#loading').show();
        var divData = $("#insertForm :input").serializeArray();
        var data = {};
        $(divData).each(function(index, obj){
            data[obj.name] = obj.value;
        });

        $.ajax({
            url: ctx + '/insert',
            data: divData,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#loading').hide();
                    $('#report').html(message);
                    $('#report').show();
                }
            }
        });
    });

    $('#update').click(function() {
        $('#updateForm').hide();
        $('#loading').show();
        var divData = $("#updateForm :input").serializeArray();
        var data = {};
        $(divData).each(function(index, obj){
            data[obj.name] = obj.value;
        });

        $.ajax({
            url: ctx + '/update',
            data: divData,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#loading').hide();
                    $('#report').html(message);
                    $('#report').show();
                }
            }
        });
    });

    $('#delete').click(function() {
        $('#deleteForm').hide();
        $('#loading').show();
        var divData = $("#deleteForm :input").serializeArray();
        var data = {};
        $(divData).each(function(index, obj){
            data[obj.name] = obj.value;
        });

        $.ajax({
            url: ctx + '/delete',
            data: divData,
            type: 'POST',
            success: function(message) {
                if (message == "" || message == null) {
                    showFromPage();
                } else {
                    $('#loading').hide();
                    $('#report').html(message);
                    $('#report').show();
                }
            }
        });
    });
}