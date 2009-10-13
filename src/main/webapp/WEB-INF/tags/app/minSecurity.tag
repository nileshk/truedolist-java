<%@ attribute name="level" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${currentUserSecurityLevel != null}">
	<c:if test="${currentUserSecurityLevel >= level}">
		<jsp:doBody/>
	</c:if>
</c:if>