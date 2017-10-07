<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<body>
  <a href="/">Home</a>
  <c:choose>
    <c:when test="${users == null && color.isEmpty()}">
      <h1>No Users yet</h1>
    </c:when>
    <c:when test="${users == null}">
      <h1>No users like ${color}</h1>
    </c:when>
    <c:when test="${color.length() > 0}">
      <h1>Users with favorite color ${color}</h1>
    </c:when>
    <c:otherwise>
      <h1>Showing all Users</h1>
    </c:otherwise>
  </c:choose>
  <c:if test="${users != null}">
    <c:forEach items="${users}" var="user">
    <div>
      <p>User: ${user.getName()}</p>
      <p>ID: ${user.getID()}</p>
      <p>Favorite Color: 
          ${user.getFavColor().isEmpty() ? "None" : user.getFavColor()}
      </p>
      <a href="user?name=${user.getName()}">User page</a>
    </div>
    <hr>
    </c:forEach>
  </c:if>
</body>
</html>
