<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="name" required="true"
	description="Name of the command object" %>

<spring:hasBindErrors name="${name}">
	<fmt:message key="errors.header"/>
	<spring:bind path="${name}">
		<c:forEach var="error" items="${status.errorMessages}">
			<fmt:message key="errors.prefix"/>${error}<fmt:message key="errors.suffix"/>
		</c:forEach>
	</spring:bind>
	<fmt:message key="errors.footer"/>
</spring:hasBindErrors>