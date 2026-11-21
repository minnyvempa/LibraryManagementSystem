<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Book List (Spring)</title></head>
<body>
    <h2>Available Books (Servlet + Spring Service + DAO)</h2>
    <table border="1" cellpadding="8">
        <tr>
            <th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>Copies</th>
        </tr>
        <c:forEach var="b" items="${books}">
            <tr>
                <td>${b.id}</td>
                <td>${b.title}</td>
                <td>${b.author}</td>
                <td>${b.category}</td>
                <td>
                    <c:choose>
                        <c:when test="${b.available > 0}">${b.available}</c:when>
                        <c:otherwise>Out of stock</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br><a href="welcome.jsp">Back</a>
</body>
</html>