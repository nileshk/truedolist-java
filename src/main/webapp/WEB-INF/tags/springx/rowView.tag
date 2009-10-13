<%@ attribute name="property" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/springx" prefix="springx" %>

<tr>
	<td><spring:message code="${code}"/></td>
	<td>
		<spring:bind path="${property}">
			${status.value}
		</spring:bind>
	</td>
</tr>