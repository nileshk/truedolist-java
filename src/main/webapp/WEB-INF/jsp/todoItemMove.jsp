<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<form name="form" action="todoItemEdit.do" method="POST">
<br>
Moving todo: ${command.title}<br>
<br>
<table>
	<springx:rowSelection property="command.todoList.id" code="todolist" list="${todoLists}"/>
	<springx:id />	
	<springx:submitRow/>
</table>
<app:focus/>