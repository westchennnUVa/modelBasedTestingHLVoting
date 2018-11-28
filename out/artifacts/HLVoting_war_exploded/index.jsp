<%-- 
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : Prediction index-dispatcher JSP 
--%>

<%--Forward to login if no user logged in, or appropriate module base on request--%>

<%
  String username = (String)session.getAttribute("username");
  String sessReq = (String)session.getAttribute("reqType");
  if(username == null || username.equals("")) 
    pageContext.forward("login.jsp");
    
  String dispReq = request.getParameter("reqType");
  if(dispReq != null && dispReq.equals("predSubmit"))
    pageContext.forward("predict.jsp");
  else if (dispReq != null && dispReq.equals("viewSubmit"))
	//pageContext.forward("../HLVoting.View");
	  pageContext.forward("/View");
    //pageContext.forward("../../servlet/jhowdes1.View");
    
  if(sessReq != null && sessReq.equals("viewSubmit")) {
    session.setAttribute("reqType","none");
    //pageContext.forward("../HLVoting.View");
    pageContext.forward("/View");
    //pageContext.forward("../../servlet/jhowdes1.View");
  }  
%>

<html>
<head>
  <title>The Prediction Page</title>
  <link rel='stylesheet' type='text/css' href='../../resources/jhowdes1/style11.css'>
  <script type='text/javascript' src='../../resources/jhowdes1/functions11.js'></script>
</head>

<body>
  <h1>Welcome to the Prediction Page</h1>
  Homework 11 : Submitting all modifications
   
  <br />
  <!--   <form method='post' name='resetform' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Logout'> -->
  <!--  <form method='post' name='resetform' action='http://localhost:8080/experiment/HLVoting.Logout'> -->
  <form method='post' name='resetform' action='http://localhost:8080/Logout'>
  <pre>Logged in as <b><%=username%></b>   <input type='submit' name='logoutsubmit' value='Logout'/></pre>
  </form>
  
  <!--Handle request to view submissions-->
  <hr /><br />
  You may choose to view and vote on previous predictions:
  <br /><br />
  <form method='post' action='index.jsp' name='viewReq'>
  <input type='hidden' name='reqType' value='viewSubmit'/>
  <input type='submit' name='viewSubmit' value='View Predictions'/>
  </form>
  <br /><hr />
  
  <!-- Handle request to make a new prediction -->
  <br />
  You may also submit a new prediction:    
  <br /><br />
  <form method='post' action='index.jsp' name='predReq'>
  <input type='hidden' name='reqType' value='predSubmit'/>
  <input type='submit' name='predSubmit' value='New Prediction'/>
  </form>
  <br /><hr />
  
  <!--button to reset application-->
  <br />
  <!-- <form method='post' name='resetApp' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Reset'> -->
  <!-- <form method='post' name='resetApp' action='http://localhost:8080/experiment/HLVoting.Reset'> -->
  <form method='post' name='resetApp' action='http://localhost:8080/Reset'>
  <pre>Reset Application:  <input type='submit' name='resetSubmit' value='Reset Application'/></pre>
  </form>
  
	<!--Contact information-->
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