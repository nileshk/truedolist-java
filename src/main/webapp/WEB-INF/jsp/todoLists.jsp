<%@ include file="include/taglib.jsp" %>
<app:top>
  <app:deleteConfirmation code="delete.todolist.confirm"/>
</app:top>

<h2><spring:message code="todolists"/></h2>
<display:table name="list" id="item" class="mars" export="false" sort="list" pagesize="20" requestURI="todoLists.do">
<app:minSecurity level="55">
  <display:column property="position" title="Position" sortable="false" />
</app:minSecurity>
  <display:column property="title" title="Title" sortable="false" href="todoItemList.do" paramId="id" paramProperty="id"/>
<%--<display:column title="Items" sortable="false">${fn:length(item.todoItems)}</display:column>--%>
  <display:column title="Edit" href="todoListEdit.do" paramId="id" paramProperty="id">
		<spring:message code="option.edit"/>
  </display:column>
  <display:column title="Move">
  		<form action="todoListMoveUpOrDown.do" method="POST">
			<input type="hidden" name="id" value="${item.id}"/>
  			<input class='ibutton' type="submit" name="submit.moveUp" value="Up"/>
  			<input class='ibutton' type="submit" name="submit.moveDown" value="Down"/>
  		</form>
  </display:column>  
  <display:column title="Delete">
  		<form action="todoListDelete.do" method="POST" onsubmit="return deleteConfirmation()">
			<input type="hidden" name="id" value="${item.id}"/>
  			<input class='ibutton' type="submit" name="submit.delete" value="Delete"/>
  		</form>
  </display:column>  
</display:table>
<br>
<a href="todoListEdit.do"><spring:message code="todolist.createNew"/></a>