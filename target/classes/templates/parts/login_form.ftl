<#macro login path isRegisterForm>

<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User name : </label>
        <div class="col-sm-6">
            <input type="text" name="username" placeholder="Username"/>
        </div>
    </div>

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User password : </label>
        <div class="col-sm-6">
            <input type="text" name="password" placeholder="Password"/>
        </div>
    </div>
<#if !isRegisterForm>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User email : </label>
        <div class="col-sm-6">
            <input type="text" name="email" placeholder="userMail@mail.com"/>
        </div>
    </div>
</#if>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button type="submit" class="btn btn-primary"><#if isRegisterForm>Create<#else> Sign In</#if></button>
    <div class="mr-8">
     <#if !isRegisterForm><a href="/registration">Add new user</a> </#if>
    </div>

</form>
</#macro>

<#macro logout>
    <div>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Sign out</button>
        </form>
    </div>
</#macro>