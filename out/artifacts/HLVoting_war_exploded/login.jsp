<%-- 
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : Login/new user JSP 
--%>

<html>
<head>
  <title>The Prediction Page</title>
  <link rel='stylesheet' type='text/css' href='../../resources/jhowdes1/style11.css'>
  <script type='text/javascript' src='../../resources/jhowdes1/functions11.js'></script>
</head>

<body onload='loginFocus()'>
  <h1>Welcome to the Prediction Page</h1>
  Homework 11 : Submitting all modifications
  <br />

<%
  String loginFail = (String)session.getAttribute("loginFail");
  if(loginFail!=null && loginFail.equals("pass")) {
%>
  <font color=FF0000><i>Incorrect username/password.</i></font>
<% } else if(loginFail!=null && loginFail.equals("name")){ %>
  <font color=FF0000><i>Username does not exist.</i></font>
<% } %>

  <!--Form for user login-->
  <br />
  <b>Returning Users:</b>
  <br />
  <!--  <form method='post' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.LoginValidate' name='loginForm' onSubmit='return loginValidate()'> --> 
  <!--  <form method='post' action='http://localhost:8080/experiment/HLVoting.LoginValidate' name='loginForm' onSubmit='return loginValidate()'> -->
  <form method='post' action='http://localhost:8080/LoginValidate' name='loginForm' onSubmit='return loginValidate()'>
  <table>
  <tr align='center'><td>
  Username: <input type='text' class='userpass' onfocus='this.select()' id='userLogin' name='userLogin' 
  value=''>
  Password: <input type='password' class='userpass' id='userPass' name='userPass'>
  </td></tr>
  </table>
  <input type='submit' name='loginButton' value='Log In' onclick='buttonPress=this.name'/>
  </form>
  <br /><hr /><br />
  
  <!--Form to create new user-->
  <b>Register New User:</b>
  <br />
  <!--  <form method='post' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.UserWrite' name='newUserForm' onSubmit='return newUserValidate()'> -->
  <!--  <form method='post' action='http://localhost:8080/experiment/HLVoting.UserWrite' name='newUserForm' onSubmit='return newUserValidate()'> -->
  <form method='post' action='http://localhost:8080/UserWrite' name='newUserForm' onSubmit='return newUserValidate()'>
  <table>
  <tr align='justified'><td>
  Username: <input type='text' class='userpass' onfocus='this.select()' id='userName' name='userName' 
  value=''>
  </td><td>
  Contact E-mail: <input type='text' class='userpass' onfocus='this.select()' id='userEmail' name='userEmail' 
  value=''>
  </td></tr>
  <tr align='justified'><td>
  Password: <input type='password' class='userpass' id='firstPass' name='firstPass'>
  </td><td>
  Confirm Password: <input type='password' class='userpass' id='secPass' name='secPass'>
  </td></tr>
  </table>
  <input type='submit' name='newUserButton' value='Register' onclick='buttonPress=this.name'/>
  </form>
  <br />
  <br />
  
  <hr />
	<!--Contact information-->
  <br />Webpage created by Jeremiah Howdeshell and Chaoran Lu for HW11
  <br />
  <br />For questions or comments, please contact us by emailing <a href='mailto:jhowdes1@gmu.edu' target='_blank'>jhowdes1@gmu.edu.</a>
	<!--Display date last modified-->
  <br /><br />
  This document was last modified on:
  <script>document.write(document.lastModified);</script>
</body>
</html>