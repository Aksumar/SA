<#import "parts/common.ftl" as c>

<#import "parts/login_form.ftl" as l>
<@c.page>
<div>
    <@l.logout/>

</div>
    <form method="post" enctype="multipart/form-data">
        <div>Загрузите таблицу для анализа :</div>
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>



</@c.page>