<#macro pnav logoutUrl homeUrl userName>
  <p>
      <h3>${userName}</h3>
      <a href=${logoutUrl}>logout</a>
      <a href=${homeUrl}>home</a>
  </p>
</#macro>