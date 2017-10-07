<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<body>
  <c:if test="${error != null}">
    <h2>${error}</h2>
  </c:if>
  <c:if test="${msg != null}">
    <h2>${msg}</h2>
  </c:if>

  <a href="about.html">about.html</a> | 
  <a href="users">Show all users</a> |

  <c:choose>
    <c:when test="${user != null}">
      <a href="user?name=${user.getName()}">${user.getName()}</a> | 
      <a href="logout">logout</a> <br>
    </c:when>
    <c:otherwise>
      <form action="login" method="post">
        <fieldset>
          Username:<br>
          <input type="text" name="name" placeholder="username"> <br>
          Password:<br>
          <input type="password" name="password" placeholder="password"> <br>
          <input type="submit" value="Login">
        </fieldset>
      </form> <br>

      <form action="add_user" method="post">
        Create user:<br> 
        <fieldset>
          Name:<br>
          <input type="text" name="name" placeholder="username"> <br>
          Password:<br>
          <input type="password" name="password" placeholder="password"> <br>
          <input type="submit" value="Submit">
        </fieldset>
      </form>
    </c:otherwise>
  </c:choose>

  <hr>

  <form action="users">
    Get users by favorite color:<br> 
    <fieldset>
      Color:<br>
      <input type="text" name="color" placeholder="blue"> 
      <br>
      <input type="submit" value="Search">
    </fieldset>
  </form> <br>
  

</html>
</body>
