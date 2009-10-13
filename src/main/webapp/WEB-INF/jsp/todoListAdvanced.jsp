<%@ include file="include/taglib.jsp" %>
<app:top>
  <script>
	function verifyDeleteTodo() {
	    var x=window.confirm("<spring:message code="delete.todo.confirm"/>")
		if (x) {
			return true;
		} else {
			return false;
		}
	}
  </script>  
</app:top>

Activities:<br/>
<display:table name="list" id="item" class="mars" export="false" sort="list" pagesize="20" requestURI="todoList.do">
  <display:column title="Edit" href="todoEdit.do" paramId="id" paramProperty="id">
		<spring:message code="option.edit"/>
  </display:column>
  <display:column title="Delete">
  		<form action="todoDelete.do" method="POST" onsubmit="return verifyDeleteTodo()">
			<input type="hidden" name="id" value="${item.id}"/>
  			<input class='ibutton' type="submit" name="submit.delete" value="Delete"/>
  		</form>
  </display:column>  
  <display:column property="description" title="Description" sortable="true" />
  <display:column property="level" title="Level" sortable="true" />  
</display:table>
<br>
<table>
	<tr>
	<c:forEach items="${todoListMap}" var="todoList">
		<td>
			<table border="1">
			<tr>
				<th>${todoList.key}</th>
			</tr>
			<c:forEach items="${todoList.value}" var="todoItem">
				<tr>
					<td>${todoItem.description}</td>
				</tr>
			</c:forEach>
			</table>
		</td>
	</c:forEach>
	</tr>
</table>
<a href="todoEdit.do"><spring:message code="todo.createNew"/></a>