<%@ page contentType="text/plain;charset=UTF-8" %>

<%
print request.getRemoteAddr()
print request.getHeader("X-Forwarded-For")
print request.getHeader("Client-IP")
%>