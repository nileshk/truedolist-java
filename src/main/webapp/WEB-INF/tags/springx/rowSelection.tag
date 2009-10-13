<%@ attribute name="property" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="list" required="true" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/springx" prefix="springx" %>

<tr>
	<td><spring:message code="${code}"/></td>
	<td><springx:selection property="${property}" list="${list}"/></td>
	<td><springx:error property="${property}"/></td>
</tr>