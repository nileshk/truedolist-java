<%@ attribute name="property" required="true" %>
<%@ attribute name="code" required="true" %>
<%@ attribute name="size" required="false" type="java.lang.Integer" %>
<%@ attribute name="max" required="false" type="java.lang.Integer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags/springx" prefix="springx" %>

<tr>
	<td><spring:message code="${code}"/></td>
	<td>
		<springx:text property="${property}" size="${size}" max="${max}"/>
	</td>
	<td><springx:error property="${property}"/></td>
</tr>