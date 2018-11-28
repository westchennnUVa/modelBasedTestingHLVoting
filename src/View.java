/*
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : View Predictions Servlet
*/

// Import Java Libraries
import java.io.*;
import java.util.*;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

// Import XML Libraries
import org.w3c.dom.*;
import javax.xml.parsers.*;

public class View extends HttpServlet
{  
  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    doPost(req,res);
  }
  
	public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    HttpSession session = req.getSession(true); //get or create session object
    ServletContext context = session.getServletContext(); //get context object
    res.setContentType("text/html"); //set response object type
    PrintWriter out = res.getWriter(); //initialize PW object to provide html to browser

    //get username from session, otherwise retrieve from request and set in session
    String username = (String) session.getAttribute("username");
		
		//print initial html statements
    out.println("<html>");
    out.println("<head>");
    out.println("<title>The Prediction Page</title>");
    out.println("<link rel='stylesheet' type='text/css' href='../../resources/jhowdes1/style11.css'>");
    out.println("<script type='text/javascript' src='../../resources/jhowdes1/functions11.js'></script>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>Welcome to the Prediction Page</h1>");

    List<PredObj> predArr = new ArrayList<PredObj>();
   
    //initialize variables for PredObj creation
    String predUser = "";
    String predStr = "";
    String argsStr = "";
    int agreeCount = 0,unsureCount = 0,disagreeCount = 0;
    int count = 0;
    int[] votes = new int[3];
    
    //create DOM and read predictions from XML file into PredObj List
    try{
      DocumentBuilderFactory docFactory =  DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
      //String xml = "/data/apps-swe432/swe432/WEB-INF/data/jhowdes1_preds.xml";
      //String xml = "/Applications/apache-tomcat-7.0.54/webapps/experiment/WEB-INF/data/HLVoting/jhowdes1_preds.xml";
      String xml = "/Users/WestChen/IdeaProjects/HLVoting/src/independent/jhowdes1_preds.xml";
      Document doc = docBuilder.parse(xml);
      Element  element = doc.getDocumentElement(); 
      NodeList predictionNodes = element.getChildNodes();        
      for (int i=0; i < predictionNodes.getLength(); i++){
        Node inst = predictionNodes.item(i);
        if (isTextNode(inst))
         continue;
        NodeList predItems = inst.getChildNodes(); 
        count = 0;
        for (int j=0; j < predItems.getLength(); j++){
          Node node = predItems.item(j);
          if ( isTextNode(node)) 
          continue;
          switch(count) {
            case 0:
              predUser = node.getFirstChild().getNodeValue();
              break;
            case 1:
              predStr = node.getFirstChild().getNodeValue();
              break;
            case 2:                                     
              argsStr = nl2br(node.getFirstChild().getNodeValue());
              break;
            case 3:
              agreeCount = Integer.parseInt(node.getFirstChild().getNodeValue());
              break;
            case 4:
              unsureCount = Integer.parseInt(node.getFirstChild().getNodeValue());
              break;
            case 5: 
              disagreeCount = Integer.parseInt(node.getFirstChild().getNodeValue());
              break;
          }
          count++;
        }
        votes[0] = agreeCount;
        votes[1] = unsureCount;
        votes[2] = disagreeCount;
        PredObj predo = new PredObj(predUser,predStr,argsStr,votes);
        predArr.add(predo);
        votes = new int[3];
      }
      context.setAttribute("jhowdes1_predArr", predArr);
    }
    catch(Exception e){}
    
    //sort array in order of most positive votes
    Collections.sort(predArr);
    
    //display message for no predictions
    int arrSize = predArr.size() - 1;
    if(arrSize > -1)
      out.println("<p>Predictions ordered by the most agreed with: </p>\n");
    else
      out.println("<p>No predictions have been made at this time.</p>\n");

    //loop to display predictions in order of most agree votes
    for(int i = arrSize; i >= 0; i--) 
    {
      out.println("<table class='outputtable'>");
      //display if own user's prediction
      if(username.equals(predArr.get(i).getUser())) {
        out.print("<tr><td><b>You</b> predicted: ");
        out.print(predArr.get(i).getPred());
        out.print("</td></tr>\n");
        out.print("<tr><td>Your arguments: ");
        out.print(predArr.get(i).getArgs());
        out.println("</td></tr>");
        out.print("<tr><td>Other users were convinced (");
        out.print(predArr.get(i).getAgree());
        out.print("), unsure (");
        out.print(predArr.get(i).getUnsure());
        out.print("), or disagree (");
        out.print(predArr.get(i).getDisagree());
        out.println(")</td></tr>");
        out.println("</table>\n<br />\n");
        
        // provide button that allows user to delete his own assertion
        //out.println("<form method='post' name='deletePred' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Delete'>");
        //out.println("<form method='post' name='deletePred' action='http://localhost:8080/experiment/HLVoting.Delete'>");
        out.println("<form method='post' name='deletePred' action='http://localhost:8080/Delete'>");
        out.println("<input type='hidden' name='indexToDelete' value='" + i + "'>");
        
        //submit button for vote
        out.println("<input type='submit' name='votesubmit' value='Delete Prediction'/>");
        out.println("</form>\n<hr />\n<br />\n");
      }
      else {
        //display prediction of other users
        out.print("<tr><td><b>");
        out.print(predArr.get(i).getUser());
        out.print("</b> predicted: ");
    		out.print(predArr.get(i).getPred());
        out.print("</td></tr>\n");
        out.print("<tr><td>Their arguments: ");
        out.print(predArr.get(i).getArgs());
        out.println("</td></tr>");
        out.println("<tr><td>Other users were ");
        //report previous results of votes in one row table
        out.print("convinced (");
        out.print(predArr.get(i).getAgree());
        out.print("), unsure (");
        out.print(predArr.get(i).getUnsure());
        out.print("), or disagree (");
        out.print(predArr.get(i).getDisagree());
        out.println(")</td></tr>");
        out.println("</table>\n<br />\n");
		
        //display voting options
        //out.println("<form method='post' name='voteform' action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Vote' onSubmit='return voteValidate(this);'>\n");
        //out.println("<form method='post' name='voteform' action='http://localhost:8080/experiment/HLVoting.Vote' onSubmit='return voteValidate(this);'>\n");
        out.println("<form method='post' name='voteform' action='http://localhost:8080/Vote' onSubmit='return voteValidate(this);'>\n");
        out.println("<strong>Are you convinced by this prediction?</strong>\n<br />\n");
        out.println("<table>\n<tr>");
        out.println("<input type='hidden' name='predToVote' value='" + i + "'>");
        out.println("<input type='radio' name='vote' id='Convinced' value='Convinced'/>");
        out.println("<label for='Convinced'> Convinced </label>");
        out.println("<input type='radio' name='vote' id='Unsure' value='Unsure'/>");
        out.println("<label for='Unsure'> Unsure </label>");
        out.println("<input type='radio' name='vote' id='Disagree' value='Disagree'/>");
        out.println("<label for='Disagree'> Disagree </label>");
        out.println("</tr></table>\n");
    
        //submit button for vote
        out.println("<input type='submit' name='votesubmit' value='Vote'/>\n");
        out.println("</form>\n<hr />\n<br />\n");
      }
    }
    
    //button to reset application
    out.print("<br />\n<form method='post' name='resetApp'");
    //out.print("action='http://apps-swe432.vse.gmu.edu:8080/swe432/servlet/jhowdes1.Reset'>");
    //out.print("action='http://localhost:8080/experiment/HLVoting.Reset'>");
    out.print("action='http://localhost:8080/Reset'>");
    out.print("<pre>Reset Application:  <input type='submit' name='resetSubmit' value='Reset Application'/></pre>");
    out.println("\n</form>");
    
    //link back to prediction page
    out.println("<br />");
    //out.print("<a href='http://apps-swe432.vse.gmu.edu:8080/swe432/jsp/jhowdes1/index.jsp'>");
    //out.print("<a href='http://http://localhost:8080/experiment/HLVoting/index.jsp'>");
    out.print("<a href='http://localhost:8080/index.jsp'>");
    out.println("Return to the home page.</a>\n<br /><br />");

    //print contact information
    out.println("Webpage created by Jeremiah Howdeshell and Chaoran Lu for HW11\n<br /><br />");
    out.println("For questions or comments, please contact us by emailing <a href='mailto:jhowdes1@gmu.edu' target='_blank'>jhowdes1@gmu.edu.</a>");

    //print page modified block
    out.print("<br /><br />\nThis document was last modified on: ");
    out.print("<script>document.write(document.lastModified);</script>\n");
    out.print("<br />");
    out.println("</body>");
    out.println("</html>");
    out.close();
  }
	 
  //function to replicate PHP nl2br
  public static String nl2br(String text) {return text.replaceAll("\n","<br />");} 

  //function to test for proper DOM nodes
  public boolean isTextNode(Node n){return n.getNodeName().equals("#text");}
}
