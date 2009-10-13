<%@ attribute name="code" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:choose>
	<c:when test="${code==null}">
		<c:set var="code" value="delete.default"/>
	</c:when>
	<c:otherwise>
		<c:set var="code" value="${code}"/>
	</c:otherwise>
</c:choose>
<script type="text/javascript" language="JavaScript">
  <!--
	function deleteConfirmation() {
	    var x=window.confirm("<spring:message code="${code}"/>")
		if (x) {
			return true;
		} else {
			return false;
		}
	}  // -->
</script>
