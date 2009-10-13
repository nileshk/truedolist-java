<%@ attribute name="property" required="true" %>
<%@ attribute name="size" required="false" type="java.lang.Integer" %>
<%@ attribute name="max" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="${property}">
	<input type="text" 
		name="${status.expression}" 
		value="${status.value}"
		<c:if test="${not empty size}">size="${size}"</c:if>
		<c:if test="${not empty max}">size="${max}"</c:if>
		/>
</spring:bind>
