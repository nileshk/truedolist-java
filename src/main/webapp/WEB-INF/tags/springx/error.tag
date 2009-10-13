<%@ attribute name="property" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="${property}">
    <c:if test="${not empty status.errorMessage}">
        ${status.errorMessage}
    </c:if>
</spring:bind>
