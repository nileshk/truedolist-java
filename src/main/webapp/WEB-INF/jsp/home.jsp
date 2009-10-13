<%@ include file="include/taglib.jsp" %>
<app:top showErrors="false">
<c:if test="${not empty sessionUserId}">
	<script type="text/javascript" language="JavaScript">
		<!--
		var browser=navigator.userAgent.toLowerCase();
	 	var browser_iphone_or_ipod = ( browser.indexOf('iphone')!=-1 || browser.indexOf('ipod')!=-1 );
	 	if(browser_iphone_or_ipod) {
	 		document.location.href='todoLists.do';
	 	} else {
	 		document.location.href='client/';
	 	}
		// -->
	</script>
</c:if>	
</app:top>
 
<center>
  <c:if test="${empty username}">
  	  <img src="img/truedolist_logo_small.png"/>
  	  <br/>
  	  <br/>
  	  <springx:errors name="command"/>
	  <form action="<c:url value="home.do"/>" method="post">		  
		<table class="inputform" border="0">
		<div id="topleft">
			<tr>
				<td style="text-align: right;">Username:</td>
				<td>
					<spring:bind path="command.username">
						<input type="text" 
							name="<c:out value="${status.expression}"/>" 
							value="<c:out value="${status.value}"/>"/>
					</spring:bind>
				</td>			
				<td><springx:error property="command.username"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">Password:</td>
				<td>
					<spring:bind path="command.password">
						<input type="password" 
							name="<c:out value="${status.expression}"/>" 
							value="<c:out value="${status.value}"/>"/>
					</spring:bind>
				</td>
				<td><springx:error property="command.password"/></td>
			</tr>			
			<tr>
				<td></td>			
				<td>
					<spring:bind path="command.remember"> 
						<input type="hidden" name="_<c:out value="${status.expression}"/>">
						<input type="checkbox" name="<c:out value="${status.expression}"/>" value="true"
						<c:if test="${status.value}">checked</c:if>/>
					</spring:bind>
					Remember me
				</td>				
			</tr>
			<tr>
				<td></td>
				<td>
					<input class="input-button" type="submit" name="submit.login" value="Sign in"/>
				</td>
			</tr>
		</div>
		</table>	
	  </form>
	  <app:focus/>
  </c:if>
  <c:if test="${not empty sessionUserId}">
  	<br><br>
  	Logged in as: ${username}<br><br>
  	<br>
  	It appears you <strong>do not have JavaScript enabled</strong>.  You have two options:
  	<ol style="margin: 20px">
  	<li style="margin: 20px">Enable JavaScript and  
  	<a href="client/">go to the <strong>full interface</strong> for TrueDoList</a></li>
  	<li style="margin: 20px">or <a href="todoLists.do">continue using this <strong>minimal version</strong> of TrueDoList</a>.</li>
  	</ol> 	
  </c:if>
</html>