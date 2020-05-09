<div id="updateForm" >
    <script template="row" type="text/x-jquery-tmpl">
        <option value="{{= $data}}">{{= $data}}</option>
    </script>

    Set column:
    <div id="set">
        <select name="setColumn" class="setColumn" >
        </select>
        = <input type="text" name="setValue" id="setValue"/><br><br>
    </div>

    Where column:
    <div id="where">
        <select name="whereColumn" class="whereColumn" >
        </select>
        = <input type="text" name="whereValue" id="whereValue" /><br><br>
    </div>

    <input type="submit" value="update" id="update"/><br>
</div>