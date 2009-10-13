<%@ include file="include/top.jsp" %>
<h1>Error has occured:</h1><br>
${errorMessage}
<c:if test="${not empty exception}">
	<c:if test="${not empty exception.code}">
		<spring:message code='${exception.code}'/>
	</c:if>
	<c:if test="${empty exception.code && not empty exception.defaultMessage}">
		${exception.defaultMessage}
	</c:if>
</c:if>