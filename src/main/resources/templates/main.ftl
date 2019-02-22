<#import "parts/common.ftl" as c>

<@c.page>

<div>
    <form method="post" enctype="multipart/form-data">
        <div>Загрузите таблицу для анализа :</div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-primary ml-2">Добавить</button>
    </form>


    <p>
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Additional settings
        </button>
    </p>
    <div class="collapse" id="collapseExample">
        <div class="card card-body">
            блаблабла
        </div>
    </div>

</div>
</@c.page>