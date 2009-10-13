<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<form name="form" action="todoItemEdit.do" method="POST">
<br>
<table>
	<springx:row property="command.title" code="todoitem.title" size="60"/>
	<springx:id />	
	<springx:hidden property="command.todoList.id"/>
	<springx:submitRow/>
</table>
<app:focus/>