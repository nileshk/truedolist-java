<%@ include file="include/top.jsp" %>
<c:if test="${userData != null}">
	Database initialized.  Admin login is:<br>
	Login name: <c:out value="${userData.userLogin}"/><br>
	Password: admin<br>
	Security Level: ${userData.securityLevel}<br>
	E-mail: ${userData.email}<br>
</c:if>
<c:if test="${userData == null}">
	Database has already been initialized, no initialization occurred.
</c:if>
  </body>
</html>
