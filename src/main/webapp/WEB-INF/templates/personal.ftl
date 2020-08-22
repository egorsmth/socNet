<#import "profile_nav.ftl" as pnav>
<html>
<body>
  <@pnav.pnav logoutUrl homeUrl user.name/>
  <h1>user Info</h1>
    <p>${user.name}</p>
</body>
</html>
