<#import "profile_nav.ftl" as pnav>
<html>
<body>
  <@pnav.pnav logoutUrl homeUrl user.name/>
  <h1>user Info</h1>
  <p>${other_user.name}</p>
  <a href="${friendRequestUrl}">Send friend request to ${other_user.name}</a>
</body>
</html>
