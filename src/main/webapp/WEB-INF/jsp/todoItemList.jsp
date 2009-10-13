<%@ include file="include/taglib.jsp" %>
<app:top>
  <app:deleteConfirmation code="delete.todoitem.confirm"/>
</app:top>

<h2>${todoList.title}</h2>
<%--
<table>
<c:forEach items="${list}" var="item">
	<tr>
	<td>${item.title}</td>
	<td>( <a href="todoItemMove.do?id=${item.id}"><spring:message code="option.move"/></a></td>
	<td>
	<form action="todoItemMoveUpOrDown.do" method="POST">
		<input type="hidden" name="id" value="${item.id}"/>
		<input type="hidden" name="returnId" value="${todoList.id}"/>
 		<input class='ibutton' type="submit" name="submit.moveUp" value="Up"/>
 		<input class='ibutton' type="submit" name="submit.moveDown" value="Down"/>
 	</form>
 	</td>
 	<td><a href="todoItemEdit.do?id=${item.id}"><spring:message code="option.edit"/></a></td>
 	<td>
  		<form action="todoItemDelete.do" method="POST" onsubmit="return deleteConfirmation()">
			<input type="hidden" name="id" value="${item.id}"/>
			<input type="hidden" name="returnId" value="${id}"/>
  			<input class='ibutton' type="submit" name="submit.delete" value="Delete"/> )
  		</form>
	</td> 	
	</tr>
</c:forEach>
</table>
--%>

<display:table name="list" id="item" class="mars" export="false" sort="list" pagesize="20" requestURI="todoItemList.do">
<app:minSecurity level="55">
  <display:column property="position" title="Position" sortable="false" />
</app:minSecurity>
  <display:column property="title" title="Title" sortable="false" />
  <display:column title="Move" href="todoItemMove.do" paramId="id" paramProperty="id">
		<spring:message code="option.move"/>
  </display:column>
  <display:column title="Move">
  		<form action="todoItemMoveUpOrDown.do" method="POST">
			<input type="hidden" name="id" value="${item.id}"/>
			<input type="hidden" name="returnId" value="${todoList.id}"/>
  			<input class='ibutton' type="submit" name="submit.moveUp" value="Up"/>
  			<input class='ibutton' type="submit" name="submit.moveDown" value="Down"/>
  		</form>
  </display:column>
  <display:column title="Edit" href="todoItemEdit.do" paramId="id" paramProperty="id">
		<spring:message code="option.edit"/>
  </display:column>
  <display:column title="Delete">
  		<form action="todoItemDelete.do" method="POST" onsubmit="return deleteConfirmation()">
			<input type="hidden" name="id" value="${item.id}"/>
			<input type="hidden" name="returnId" value="${id}"/>
  			<input class='ibutton' type="submit" name="submit.delete" value="Delete"/>
  		</form>
  </display:column>  
</display:table>
<c:if test="${not empty deleteFailed}">
<spring:message code="delete.failed"></spring:message><br>
</c:if>
<%--
<br>
<a href="todoItemEdit.do?parentId=${id}"><spring:message code="todoitem.createNew"/></a>
--%>
<form name="form" action="todoItemEdit.do" method="POST">
<table>
	<springx:row property="command.title" code="todoitem.new" size="80"/>
	<springx:id />	
	<springx:hidden property="command.todoList.id"/>
	<springx:submitRow/>
</table>

<app:focus form="form"/>