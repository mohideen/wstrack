
<%@ page import="edu.umd.lib.wstrack.server.History" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'history.label', default: 'History')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-history" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-history" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="computerName" title="${message(code: 'history.computerName.label', default: 'Computer Name')}" params="${filterParams}"/>
					
						<g:sortableColumn property="status" title="${message(code: 'history.status.label', default: 'Status')}" params="${filterParams}"/>
					
						<g:sortableColumn property="os" title="${message(code: 'history.os.label', default: 'Os')}" params="${filterParams}"/>
					
						<g:sortableColumn property="userHash" title="${message(code: 'history.userHash.label', default: 'User Hash')}" params="${filterParams}"/>
					
						<g:sortableColumn property="guestFlag" title="${message(code: 'history.guestFlag.label', default: 'Guest Flag')}" params="${filterParams}"/>
					
						<g:sortableColumn property="timestamp" title="${message(code: 'history.timestamp.label', default: 'Timestamp')}" params="${filterParams}"/>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${historyInstanceList}" status="i" var="historyInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${historyInstance.id}">${fieldValue(bean: historyInstance, field: "computerName")}</g:link></td>
					
						<td>${fieldValue(bean: historyInstance, field: "status")}</td>
					
						<td>${fieldValue(bean: historyInstance, field: "os")}</td>
					
						<td>${fieldValue(bean: historyInstance, field: "userHash")}</td>
					
						<td><g:formatBoolean boolean="${historyInstance.guestFlag}" /></td>
					
						<td><g:formatDate date="${historyInstance.timestamp}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
                <filterpane:filterButton text="Filter" />
				<g:paginate total="${historyInstanceTotal}" params="${filterParams}" />
                <span>Entries: ${historyInstanceTotal}</span>
                <export:formats formats="['csv', 'excel', 'xml']" params="${exportParams }"/>
			</div>
		</div>
        <filterpane:filterPane domain="edu.umd.lib.wstrack.server.History" />
	</body>
</html>
