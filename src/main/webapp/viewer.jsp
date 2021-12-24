<%--
  Created by IntelliJ IDEA.
  User: ivan_gavrilovich
  Date: 15.12.21
  Time: 15:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="ct" uri="/WEB-INF/tld/taglib.tld"%>
<html>
<head>
    <title>Menu</title>
</head>
<body>
    <a>This is viewer.jsp</a>
    <br/>
    <form action = "controller" method = "post" enctype = "multipart/form-data">
        <input type="hidden" name="command" value="VIEW_TABLE"/>
        Select parser:
        <select name="parserType" size="1">
            <option value="SAX">SAX</option>
            <option value="StAX">StAX</option>
            <option value="DOM">DOM</option>
        </select>
        <br/>
        Select file:
        <br/>
        <input type="file" name="file" required />
        <br/>
        <input type="submit" value="Submit" />
    </form>
    <br/>
</body>
</html>