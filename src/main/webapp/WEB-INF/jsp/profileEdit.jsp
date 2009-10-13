<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<h2>Edit Profile</h2>

<form name="form" action="updateProfile.do" method="POST">

<table>
	<springx:row property="command.email" code="profileEdit.email"/>
	<springx:submitRow/>
</table>
</form>
