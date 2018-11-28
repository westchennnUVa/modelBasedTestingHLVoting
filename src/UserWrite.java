/*
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : New User XML Writing Class
*/

// Import Java Libraries
import java.io.*;
import java.util.*;
import java.util.Arrays;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

// Import XML Libraries
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

public class UserWrite extends HttpServlet
{
  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    HttpSession session = req.getSession(true); //get or create session object
    
    //get form values from request
    String username = (String)req.getParameter("userName");
    String userpass = (String)req.getParameter("firstPass");
    
    //Authenticate
    //String locJSP = "http://apps-swe432.vse.gmu.edu:8080/swe432/jsp/jhowdes1/index.jsp";
    //String locJSP = "http://localhost:8080/experiment/HLVoting/index.jsp";
    String locJSP = "http://localhost:8080/index.jsp";
    
    //save new user in an XML file
    try{
      saveUser(username,userpass);
      //authenticate user
      session.setAttribute("username",username);
      session.setAttribute("loginFail","authenticated");
    }catch(Exception e){ System.out.println(e); } 
    
    res.sendRedirect(locJSP);
  }
  
  public void saveUser(String name,String password) throws Exception
  {
    //String filePath = "/data/apps-swe432/swe432/WEB-INF/data/jhcl_" +
	//String filePath = "/Applications/apache-tomcat-7.0.54/webapps/experiment/WEB-INF/data/HLVoting/jhcl_" +
	String filePath = "/Users/WestChen/IdeaProjects/HLVoting/src/independent/jhcl_" +
                        name + ".xml";
    
    File file = new File(filePath);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
    bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    bw.newLine();
    bw.write("<users>");
    bw.newLine();
    bw.write("\t<name>" + name + "</name>");
    bw.newLine();
    bw.write("\t<pass>" + password + "</pass>");
    bw.newLine();
    bw.write("</users>");
    bw.flush();
    bw.close();
  }
}
