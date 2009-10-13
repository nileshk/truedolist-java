<%@ include file="include/taglib.jsp" %>
<app:top>
</app:top>
<h2>${userData.userLogin}'s profile</h2>

<a href="passwordEdit.do">Change password</a><br>
<%--
<br>
<a href="profileEdit.do">Edit profile</a>--%>

<h2>Export</h2>
<a href="todoListsAllXml.do">Export All Todo Lists to XML</a>

<h2>Import</h2>
<a href="todoListsImportFromXml.do">Import Todo Lists from XML</a>