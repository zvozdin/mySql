<div id="updateForm" >
    <script template="row" type="text/x-jquery-tmpl">
        <option value="{{= $data}}">{{= $data}}</option>
    </script>

    Set column:<br>
    <select name="setColumn" id="setColumn" >
    </select>
    = <input type="text" name="setValue" id="setValue"/><br><br>

    Where column:<br>
    <select name="whereColumn" id="whereColumn" >
    </select>
    = <input type="text" name="whereValue" id="whereValue" /><br><br>

    <input type="submit" value="update" id="update"/><br>
</div>