<%@ include file="include/top.jsp" %>

List of users:<br/>
<display:table name="userList" id="user" class="mars" sort="list" pagesize="20" requestURI="userList.do">
  <display:column title="Delete">
	<form action="userDelete.do" method="POST">
		<input type="hidden" name="id" value="${user.id}"/>
		<input type="submit" name="submit.delete" value="Delete"/>
	</form>
  </display:column>
  <display:column property="userLogin" title="Login" sortable="true" />
  <display:column property="email" title="Email" sortable="true" />
  <display:column property="userPassword" title="Password" sortable="true" />
  <display:column property="securityLevel" title="Security Level" sortable="true" />
</display:table>

<p>Security Levels: Guest = 5, Basic = 10, User = 50, Beta Tester = 55, Admin = 75, SuperUser = 100</p>

<form action="<c:url value="userList.do"/>" method="post">
	<spring:bind path="command.id">
		<input type="hidden" 
			name="${status.expression}" 
			value="${status.value}"/>
	</spring:bind>
	<table border="0" cellspacing="0" width="100%">
		<tr><td>Login:</td>
			<td>
				<spring:bind path="command.userLogin">
					<input type="text" 
						name="${status.expression}" 
						value="${status.value}"
						size="120"/>
				</spring:bind>
			</td>
			<td><springx:error property="command.userLogin"/></td>
		</tr>
		<tr><td>Password:</td>
		<td>
			<spring:bind path="command.userPassword">
				<input type="text" 
						name="${status.expression}" 
						value="${status.value}"
						size="120"/>
			</spring:bind>
		</td>
		<tr><td>Email:</td>
		<td>
			<spring:bind path="command.email">
				<input type="text" 
						name="${status.expression}" 
						value="${status.value}"
						size="120"/>
			</spring:bind>
		</td>
		<td><springx:error property="command.userPassword"/></td>
		</tr>
		<tr><td>Security Level:</td>
		<td>
			<spring:bind path="command.securityLevel">
				<input type="text" 
						name="${status.expression}" 
						value="${status.value}"
						size="120"/>
			</spring:bind>
		</td>
		<td><springx:error property="command.securityLevel"/></td>
		</tr>

	<tr><td/><td>
		<input type="submit" name="submit.save" value="Save"/>
	</td></tr>
	</table>
</form>

<b>Invite creation</b>
<form action="<c:url value="createInvite.do"/>" method="post">
	E-mail: <input type="text" name="email" size="100" /><br />
	<input type="submit" name="submit.createInvite" value="Create New Invite"/>
</form>