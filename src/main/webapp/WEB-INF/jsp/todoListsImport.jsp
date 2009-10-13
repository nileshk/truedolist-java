<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<h2>Import Todo Lists from XML</h2>
<form name="form" action="todoListsImportFromXml.do" method="POST">

<spring:message code="todoLists.importFromXml.instructions"/>
<br>
<spring:bind path="command.string">
	<textarea name="${status.expression}" rows="40" cols="80">${status.value}</textarea>
</spring:bind>
<br>
<input type="submit" name="submit.submit" value="Submit"/>

</form>
