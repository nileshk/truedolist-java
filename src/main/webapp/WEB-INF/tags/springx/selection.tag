<%@ attribute name="property" required="true" %>
<%@ attribute name="list" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<spring:bind path="${property}">
	<select name="${status.expression}">
		<c:forEach var="item" items="${list}">
			<option <c:if test="${item.id == status.value}">selected</c:if> value="${item.id}">
				${item.title}
			</option>
		</c:forEach>
	</select>
</spring:bind>
