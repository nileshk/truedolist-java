<%@ include file="include/top.jsp" %>

<h2><spring:message code="todolist.createNew"/></h2>
<form name="form" action="todoListEdit.do" method="POST">
<table>
	<springx:row property="command.title" code="todolist.title" size="80"/>
	<springx:id />
	<springx:submitRow/>
</table>
<app:focus/>