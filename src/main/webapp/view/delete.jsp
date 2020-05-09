<div id="deleteForm" >
    <script template="row" type="text/x-jquery-tmpl">
        <option value="{{= $data}}">{{= $data}}</option>
    </script>

    Delete record where column:<br>
    <select name="deleteColumn" class="deleteColumn" >
    </select>
    = <input type="text" name="deleteValue" id="deleteValue"/><br><br>

    <input type="submit" value="delete" id="delete"/><br>
</div>