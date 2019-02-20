<#import "parts/common.ftl" as c>

<#import "parts/login_form.ftl" as l>
<@c.page>
<div>
    <@l.logout/>
    <span><a href="/users">User list</a></span>
</div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="textInput" placeholder="Введите текст">
        <input type="text" name="tagInput" placeholder="Введите тэг">
        <input type="file" name="file">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Добавить</button>
    </form>


    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter?ifExists}">
        <button type="submit">Найти</button>
    </form>


    <div>Список сообщений</div>

    <#list messagesToShow as message>
        <div>
            <b>${message.id}</b>
            <span> ${message.text}</span>
            <i>${message.tag}</i>
            <strong>${message.authorName}</strong>

            <div>
                <#if message.filename??>
                   <b>message.filename</b>
                </#if>
            </div>

        </div>
    </#list >






</@c.page>