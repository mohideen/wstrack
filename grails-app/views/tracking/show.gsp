
<%@ page import="umd.edu.lib.wstrack.Tracking" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tracking.label', default: 'Tracking')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-tracking" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-tracking" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list tracking">
			
				<g:if test="${trackingInstance?.ip}">
				<li class="fieldcontain">
					<span id="ip-label" class="property-label"><g:message code="tracking.ip.label" default="Ip" /></span>
					
						<span class="property-value" aria-labelledby="ip-label"><g:fieldValue bean="${trackingInstance}" field="ip"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trackingInstance?.guestFlag}">
				<li class="fieldcontain">
					<span id="guestFlag-label" class="property-label"><g:message code="tracking.guestFlag.label" default="Guest Flag" /></span>
					
						<span class="property-value" aria-labelledby="guestFlag-label"><g:formatBoolean boolean="${trackingInstance?.guestFlag}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${trackingInstance?.hostName}">
				<li class="fieldcontain">
					<span id="hostName-label" class="property-label"><g:message code="tracking.hostName.label" default="Host Name" /></span>
					
						<span class="property-value" aria-labelledby="hostName-label"><g:fieldValue bean="${trackingInstance}" field="hostName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trackingInstance?.os}">
				<li class="fieldcontain">
					<span id="os-label" class="property-label"><g:message code="tracking.os.label" default="Os" /></span>
					
						<span class="property-value" aria-labelledby="os-label"><g:fieldValue bean="${trackingInstance}" field="os"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trackingInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="tracking.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${trackingInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${trackingInstance?.userHash}">
				<li class="fieldcontain">
					<span id="userHash-label" class="property-label"><g:message code="tracking.userHash.label" default="User Hash" /></span>
					
						<span class="property-value" aria-labelledby="userHash-label"><g:fieldValue bean="${trackingInstance}" field="userHash"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${trackingInstance?.id}" />
					<g:link class="edit" action="edit" id="${trackingInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
