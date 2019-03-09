<#import "parts/common.ftl" as c>

<@c.page>

<div>
    <form method="post" enctype="multipart/form-data">
        <div>
            <h2>Загрузите таблицу для анализа :</h2></div>
        <div class="form-group">
            <div class="custom-file">
                <input type="file" name="file" id="customFile" accept=".xlsx">
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

                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <label class="input-group-text">Выберете методы анализа</label>
                    </div>
                    <div>
                        <ul class="list-group">
                            <li class="list-group-item">
                                <div class="form-check ml-3">
                                    <input class="form-check-input" type="checkbox" name="all"
                                           id="all">
                                    <label class="form-check-label" for="all">
                                        Подробный отчет по каждому ответу
                                    </label>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="form-check ml-3">
                                    <input class="form-check-input" type="checkbox" name="minMax" id="minMax">
                                    <label class="form-check-label" for="minMax">
                                        Максимальные\минимальные ответы
                                    </label>
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="form-check ml-3">
                                    <input class="form-check-input" type="checkbox" name="intervals" id="intervals">
                                    <label class="form-check-label" for="intervals">
                                        Разбиение выборки ответов по Стеджерсу
                                    </label>
                                </div>
                            </li>

                            <li class="list-group-item">
                                <div class="form-check ml-3">
                                    <input class="form-check-input" type="checkbox" name="compare" id="compare">
                                    <label class="form-check-label" for="intervals">
                                        Сравнение полученных ответов на вопросы
                                    </label>

                                    <div>
                                        <ul class="list-group list-group-horizontal mt-3">
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" id="basic-addon1">Вопрос №1</span>
                                                </div>
                                                <input type="text" name="1question" class="form-control" placeholder="3" >
                                            </div>
                                            <div class="input-group mb-3">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text" id="basic-addon1">Вопрос №1</span>
                                                </div>
                                                <input type="text" name="2question" class="form-control" placeholder="15">
                                            </div>

                                        </ul>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>


                <div>
                    <label>Выберите вопросы для сравнения</label>

                </div>


                <input type="hidden" name="_csrf" value="${_csrf.token}"/>

                <button type="submit" class="btn btn-primary ml-2">Добавить</button>
            </div></form>


    <div class="form-group">
        <label for="exampleFormControlTextarea1">Large textarea</label>
        <textarea class="form-control rounded-0" id="exampleFormControlTextarea1" value = "blahblah" rows="10"></textarea>
    </div>
</div>

</@c.page>