<html>
<body>

  <h1>Registration!</h1>
  ${errors!""}
  <form action="${actionUrl}" method="POST">
    <input type="text" name="name" value=${name!""}>
    <input type="text" name="password">
    <input type="submit" value="Sign up">
  </form>

</body>
</html>
