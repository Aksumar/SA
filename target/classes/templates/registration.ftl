<#import "parts/common.ftl" as c>

<#import "parts/login_form.ftl" as login>
<@c.page>
<div class="mb-1">Add new user</div>
${message?ifExists}
<@login.login "/registration" true/>
</@c.page>