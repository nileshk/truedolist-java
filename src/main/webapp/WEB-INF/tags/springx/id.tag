<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="command.id">
	<input type="hidden" 
		name="${status.expression}" 
		value="${status.value}"/>
</spring:bind>
