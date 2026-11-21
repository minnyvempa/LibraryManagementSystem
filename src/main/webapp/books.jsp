<%@ page contentType="text/html; charset=UTF-8" import="java.sql.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    List<Map<String, Object>> books = new ArrayList<>();
    try {
        Class<?> cls = Class.forName("DBConnection");
        Connection con = (Connection) cls.getMethod("getConnection").invoke(null);
        PreparedStatement ps = con.prepareStatement(
                "SELECT id, title, author, category, available FROM books");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Map<String, Object> b = new HashMap<>();
            b.put("id", rs.getInt("id"));
            b.put("title", rs.getString("title"));
            b.put("author", rs.getString("author"));
            b.put("category", rs.getString("category"));
            b.put("available", rs.getInt("available"));
            books.add(b);
        }
        rs.close();
        ps.close();
        con.close();
    } catch (Exception e) {
        out.println("Database Error: " + e);
    }
    request.setAttribute("books", books);
%>
<html>
<head><title>Books</title></head>
<body>
    <h2>Available Books</h2>
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