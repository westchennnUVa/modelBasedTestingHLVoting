<%-- 
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : New prediction JSP 
--%>

<%--initialize session variable to hold username--%>
<%
  String username = (String)session.getAttribute("username"); 
  if(username == null || username.equals("")) 
    pageContext.forward("login.jsp");  
%>

<html>
<head>
  <title>The Prediction Page</title>
  <link rel='stylesheet' type='text/css' href='../../resources/jhowdes1/style11.css'>
  <script type='text/javascript' src='../../resources/jhowdes1/functions11.js'></script>
</head>

<body onload='predFocus()'>
  <h1>Welcome to the Prediction Page</h1>
   
  <br />
  <!--  <form method='post' name='resetform' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Logout'> -->
  <!-- <form method='post' name='resetform' action='http://localhost:8080/experiment/HLVoting.Logout'> -->
  <form method='post' name='resetform' action='http://localhost:8080/Logout'>
  <pre>Logged in as <b><%=username%></b>   <input type='submit' name='resetsubmit' value='Logout'/></pre>
  </form>
  <hr /><br />
  
  <!--Instructions for the user-->
  Please make a prediction and provide support or arguments,
  or you may view and vote on previous predictions.
	
  <!--Form for user prediction input-->
  <br /><br />
  <!-- <form method='post' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Predict' name='predForm' onSubmit='return predValidate()'> -->
  <!--  <form method='post' action='http://localhost:8080/experiment/HLVoting.Predict' name='predForm' onSubmit='return predValidate()'> -->
  <form method='post' action='http://localhost:8080/Predict' name='predForm' onSubmit='return predValidate()'>
  <table>
  <tr><td>Enter a brief prediction: 
  </td></tr>
  <tr><td><input type='text' class='predwidth' id='prediction' name='prediction' value=''>
  </td></tr>
  <!--field for arguments has scrollbar hidden unless input overflows the default area -->
  <!--Scrollbar appears and input field enlarges when user input overlows default area -->
  <tr><td>After entering your prediction, support it with evidence or arguments:
  </td></tr>
  <tr><td><textarea name='arguments' id='arguments' class='predwidth' rows=5></textarea>
  </td></tr>
  </table>
  <br />
			
  <!--Submit button for prediction-->
  <input type='submit' name='predAdd' value='Submit'/>
	
  <!--Reset button for both fields-->
  <input type='reset' value='Reset'/>
  </form>
  <hr />

  <!--Form to jump to viewing predictions-->
  If you do not want to make a prediction and wish to view previous submissions:
  <br /><br />
  <form method='post' action='index.jsp' name='viewReq'>
  <input type='hidden' name='reqType' value='viewSubmit'/>
  <input type='submit' name='viewSubmit' value='View Predictions'/>
  </form>
  <hr />
			
  <!--Contact information-->
  <br />
  <!--  <a href='http://apps-swe432.vse.gmu.edu:8080/swe432/jsp/jhowdes1/index.jsp'> -->
  <!--  <a href='http://localhost:8080/experiment/HLVoting/index.jsp'>  -->
  <a href='http://localhost:8080/index.jsp'>
  Return to the home page.</a>
  <br />
  <br />
  Webpage created by Jeremiah Howdeshell and Chaoran Lu for HW11
  <br />
  <br />
  For questions or comments, please contact us by emailing <a href='mailto:jhowdes1@gmu.edu' target='_blank'>jhowdes1@gmu.edu.</a>
	<!--Display date last modified-->
  <br />
  <br />
  This document was last modified on:
  <script>document.write(document.lastModified);</script>
</body>
</html>