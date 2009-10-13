<%@ attribute name="name" required="false" %>
<%@ attribute name="form" required="false" %>
<%@ attribute name="select" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
	<c:when test="${form==null}">
		<c:set var="form" value="0"/>
	</c:when>
	<c:otherwise>
		<c:set var="form" value="'${form}'"/>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${name==null}">
		<c:set var="name" value="0"/>
	</c:when>
	<c:otherwise>
		<c:set var="name" value="'${name}'"/>
	</c:otherwise>
</c:choose>
<script type="text/javascript" language="JavaScript">
  <!--
  var el = document.forms[${form}].elements[${name}];
  if (el.type != "hidden" && !el.disabled) {
     el.focus();
<c:if test="${select != null && select == true}">el.select();</c:if>     
  }
  // -->
</script>
