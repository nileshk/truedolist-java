<%@ attribute name="property" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/springx" prefix="springx" %>

<spring:bind path="${property}">
	<input type="hidden" 
		name="${status.expression}" 
		value="${status.value}"/>
</spring:bind>
