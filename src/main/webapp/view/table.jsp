<div id="table">
    <table border="1" class="container">
        <script template="row" type="text/x-jquery-tmpl">
            <tr>
                {{each $data}}
                    <td>
                        {{= this}}
                    </td>
                {{/each}}
            </tr>
        </script>
    </table>
</div>