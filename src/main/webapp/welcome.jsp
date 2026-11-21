<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String memberName = (String) session.getAttribute("memberName");

    if (memberName == null) {
        response.sendRedirect("login.html");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>

    <h2>Welcome, ${sessionScope.memberName}!</h2>
    <p>Your Member ID: ${sessionScope.memberId}</p>

    <a href="books.html">Books</a>
    <br><br>
    <a href="booklist">View Live Database Book List</a>
    <br><br>
    <a href="logout">Logout</a>

</body>
</html>