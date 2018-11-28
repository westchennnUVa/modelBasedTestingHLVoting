/*
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : Login Validation Servlet
*/

// Import Java Libraries
import java.io.*;
import java.util.*;
import java.util.Arrays;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;

// XML Imports
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class LoginValidate extends HttpServlet
{  
	public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    HttpSession session = req.getSession(true); //get or create session object
    ServletContext context = session.getServletContext();
    
    //setup redirect variables
    //String locJSP = "http://apps-swe432.vse.gmu.edu:8080/swe432/jsp/jhowdes1/index.jsp";
    //String locJSP = "http://localhost:8080/experiment/HLVoting/index.jsp";
    String locJSP = "http://localhost:8080/index.jsp";
    session.setAttribute("loginFail","");

    //get username from session, otherwise retrieve from request and set in session
    String username = (String)req.getParameter("userLogin");
    String userpass = (String)req.getParameter("userPass");
    
    //build dom to read in usernames and passwords to verify login
    //String xml = "/data/apps-swe432/swe432/WEB-INF/data/jhcl_" + username + ".xml";
    //String xml = "/Applications/apache-tomcat-7.0.54/webapps/experiment/WEB-INF/data/HLVoting/jhcl_" + username + ".xml";
    String xml = "/Users/WestChen/IdeaProjects/HLVoting/src/independent/jhcl_" + username + ".xml";
    String name = null;
    String pass = null;
    String uName = null;
    String uPass = null;
    
    Document dom;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      dom = db.parse(xml);
      Element doc = dom.getDocumentElement();

      name = getValue(name, doc, "name");
      if (name != null)
        if (!name.isEmpty())
          uName = name;
      pass = getValue(pass, doc, "pass");
      if (pass != null)
        if (!pass.isEmpty())
          uPass = pass;
    } catch (ParserConfigurationException pce) {
        System.out.println(pce.getMessage());
    } catch (SAXException se) {
        System.out.println(se.getMessage());
    } catch (IOException ioe) {
        System.err.println(ioe.getMessage());
    }
    
    if(uName != null && uName.equals(username)) {
      if(uPass != null && uPass.equals(userpass)) {
        session.setAttribute("username",username);
        session.setAttribute("loginFail","authenticated");
        res.sendRedirect(locJSP);
      }
      else {
        session.setAttribute("loginFail","pass");
        res.sendRedirect(locJSP);
      }
    } 
    else {
      session.setAttribute("loginFail","name");
      res.sendRedirect(locJSP);
    }
  }
	 
  //function to replicate PHP nl2br
  public static String nl2br(String text) {return text.replaceAll("\n","<br />");} 
  
  //function to get the value of node passed in from XML read
  private String getValue(String def, Element doc, String tag) {
    String value = def;
    NodeList nl;
    nl = doc.getElementsByTagName(tag);
    if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
        value = nl.item(0).getFirstChild().getNodeValue();
    }
    return value;
  }
}