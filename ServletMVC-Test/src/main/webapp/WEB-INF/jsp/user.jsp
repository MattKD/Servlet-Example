<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<body>
  <a href="/">Home</a> <br>
  <h1>${user.getName()}'s page</h1>
  <p>User: ${user.getName()}</p>
  <p>ID: ${user.getID()}</p>
  <p>Favorite Color:
     ${user.getFavColor().isEmpty() ? "None" : user.getFavColor()}
  </p>
  <c:if test="${home_page == true}">
    <form action="set_color" method="post">
      <input type="hidden" name="id" value="${user.getID()}"></input>
      <input type="text" name="color" placeholder="blue"></input>
      <input type="submit" value="Change Color"></input>
    </form>
    <br>
    <form action="delete_user" method="post">
      <input type="hidden" name="id" value="${user.getID()}"></input>
      <input type="submit" value="Delete Account"></input>
    </form>
  </c:if>

</body>
</html>
