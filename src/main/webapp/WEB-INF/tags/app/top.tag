<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib tagdir="/WEB-INF/tags/springx" prefix="springx" %>
<%@ taglib tagdir="/WEB-INF/tags/app" prefix="app" %>
<%@ attribute name="showErrors" required="false" type="java.lang.Boolean" %>
<html>
	<head>
		<title><spring:message code="application.name"/></title>
		<link rel="stylesheet" type="text/css" href="css/screen.css">
		<meta name = "viewport" content = "width = device-width"/>
		<jsp:doBody/>
	</head>
	
<body>

<%@ include file="/WEB-INF/jsp/include/menu.jsp" %>
<c:if test="${showErrors == null || showErrors == true }">
<springx:errors name="command"/>
</c:if>

<c:if test="${empty dateTimePattern}">
	<c:set var="dateTimePattern" value="yyyy-MM-dd HH:mm"/>
</c:if>
<c:if test="${empty datePattern}">
	<c:set var="datePattern" value="yyyy-MM-dd"/>
</c:if>
<c:if test="${empty timePattern}">
	<c:set var="timePattern" value="HH:mm"/>
</c:if>
