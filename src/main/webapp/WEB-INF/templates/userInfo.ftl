<#import "profile_nav.ftl" as pnav>
<html>
<body>
  <@pnav.pnav "/logout" "/home"/>
  <h1>user Info</h1>
    <p>${name}</p>
</body>
</html>
