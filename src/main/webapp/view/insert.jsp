<div id="insertForm" >
    <script template="row" type="text/x-jquery-tmpl">
        <td>{{= $data}}</td>
    </script>
    <script template="row-input" type="text/x-jquery-tmpl">
        <td><input type="text" name="{{= $data}}" id="{{= $data}}" /></td>
    </script>
    <table border="1" class="container" >
        <tr id="namesRow"></tr>
        <tr id="inputRow"></tr>
    </table>
    <br>
    <input type="submit" value="insert" id="insert"/><br>
</div>