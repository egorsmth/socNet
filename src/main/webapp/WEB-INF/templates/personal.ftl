<#import "profile_nav.ftl" as pnav>
<html>
<body>
  <@pnav.pnav logoutUrl homeUrl user.name/>
  <h1>user Info</h1>
    <p>${user.name}</p>
    <#list friends as friend>
      <p>
       <a href="userInfo?userId=${friend.id}"> <b>${friend.id}</b> ${friend.name} </a>
      </p>
    </#list>
</body>
</html>
