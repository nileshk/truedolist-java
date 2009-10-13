<c:if test="${not empty sessionUserId && empty param.popup && empty popup}">
	<div id="menu">
<%-- 		<a href="<c:url value="/"/>">Home</a>--%>
		<a href="todoLists.do">Todo lists</a>
		| <a href="profile.do">Profile</a>
		| <a href="client/">Full Interface</a>
		| <a href="logout.do">Logout</a>

		<app:minSecurity level="55">
			| <a href="todoAdvanced.do">Todo</a>
			| <a href="todoMulti.do">Todo Multiple</a>
		</app:minSecurity>
		<app:minSecurity level="75">
			| <a href="users.do">User List</a>
		</app:minSecurity>
		<app:minSecurity level="75">
			| <a href="userList.do">User Admin</a>
		</app:minSecurity>
	</div>
</c:if>