<%@ include file="include/top.jsp" %>

List of users:<br/>
<c:if test="${failed != null}">
	<br>User is already a friend<br>
</c:if>
<c:if test="${added != null}">
	<br>${added}<br>
</c:if>
<display:table name="userList" id="user" class="mars" sort="list" pagesize="20" requestURI="userList.do">
  <display:column title="Add">
  	<c:if test="${user.userLogin != username}">
		<form action="userAddFriend.do" method="POST">
			<input type="hidden" name="id" value="${user.id}"/>
			<input class="ibutton" type="submit" name="submit.add" value="Add"/>
		</form>
	</c:if>
  </display:column>
  <display:column property="userLogin" title="User" sortable="true" href="profile.do" paramId="id" paramProperty="id"/>
</display:table>
