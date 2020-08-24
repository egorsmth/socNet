<#import "profile_nav.ftl" as pnav>
<html>
<body>
  <@pnav.pnav logoutUrl homeUrl user.name/>
  <h1>user Info</h1>
    <p>${user.name}</p>
    <#list friends as friend>
      <p>
       <b>${friend.getRelationStatusString()}</b>
       <#if friend.getRelationStatus() == "REQUEST_RECEIVED">
            <a href="acceptRequest?userId=${friend.user.id}">Accept</a>
       </#if>
       <a href="userInfo?userId=${friend.user.id}">
            <b>${friend.user.id}</b>
            ${friend.user.name}
       </a>
      </p>
    </#list>
</body>
</html>
