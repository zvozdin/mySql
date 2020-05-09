<div id="tablesForAction">
    <dl class="container">
        <script template="row" type="text/x-jquery-tmpl">
            {{each(i) data}}
                <a href="main#/{{= action}}/{{= this}}">{{= this}}</a><br>
            {{/each}}
        </script>
    </dl>
</div>