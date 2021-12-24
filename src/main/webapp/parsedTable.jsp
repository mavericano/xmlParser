<%--
  Created by IntelliJ IDEA.
  User: haiku
  Date: 22.12.2021
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ct" uri="/WEB-INF/tld/taglib.tld"%>
<html>
<head>
    <title>Table</title>
</head>
<body>
    <c:choose>
        <c:when test="${requestScope.isValid}">
            File was parsed using <c:out value="${requestScope.parserType}"/><br/>
            <ct:devices devices="${requestScope.devices}"/><br/>
        </c:when>
        <c:otherwise>
            Seems like Your file is invalid!
        </c:otherwise>
    </c:choose>
    <form action="controller">
        <input type="hidden" name="command" value="RETURN_TO_MENU"/>
        <input type="submit" value="Back to menu"/>
    </form>
</body>
</html>
