<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<form name="form" action="invite.do" method="POST">

<table>
	<springx:row property="command.userName" code="createNewUser.userName"/>
	<springx:row property="command.password" code="createNewUser.password"/>
	<springx:row property="command.passwordConfirmation" code="createNewUser.passwordConfirmation"/>
	<springx:row property="command.email" code="createNewUser.email"/>
	<springx:hidden property="command.token"/>
	
	<springx:submitRow/>
</table>

</form>
<springx:error property="command.token"/>
<%-- TODO AJAX validator for userName --%>