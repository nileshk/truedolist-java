<%@ include file="include/top.jsp" %>

List of users:<br/>

<form name="form" action="releaseUserSelection.do" method="POST">
<input type="hidden" name="id" value="${param.id}"/>

<display:table name="userList" id="user" class="mars" sort="list" pagesize="20" requestURI="userListSelection.do">
  <display:column title="x">
  	<input type="checkbox" name="checks[${user_rowNum}]" value="${user.id}"/>
  </display:column>
  <display:column property="userLogin" title="Login" sortable="true" />
  <display:column property="securityLevel" title="Security Level" sortable="true" />
</display:table>

<input type="submit" name="submit.submit" value="Save"/>
</form>
