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
        <p>
            <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample"
                    aria-expanded="false" aria-controls="collapseExample">
                Additional settings
            </button>
        </p>
        <div class="collapse" id="collapseExample">
            <div class="card card-body">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text">Тема опроса</label>
                    </div>
                    <input type="text" name="header" class="form-control"
                           placeholder="Получение финансирования из средств Фонда содействия инновациям">
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text">Кого вы анкетируете?</label>
                    </div>
                    <select class="custom-select" name="responderType">
                        <option value="организаций" selected>Органиазции</option>
                        <option value="компаний">Компании</option>
                        <option value="респондентов">Респондентов</option>
                        <option value="населения">Население</option>
                    </select>
                </div>
            </div>


        </div>
        <button type="submit" class="btn btn-primary ml-2">Добавить</button>
    </form>
</div>
</@c.page>