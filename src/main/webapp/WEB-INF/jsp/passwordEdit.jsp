<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<h2>Change Password</h2>
<form name="form" action="passwordEdit.do" method="POST">

<table>
	<springx:row property="command.currentPassword" code="profileEdit.currentPassword"/>
	<springx:row property="command.newPassword" code="profileEdit.newPassword"/>
	<springx:row property="command.newPasswordConfirmation" code="profileEdit.newPasswordConfirmation"/>
	<springx:submitRow/>
</table>
</form>
