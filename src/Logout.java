/*
  Jeremiah Howdeshell and Chaoran Lu
  SWE432-001
  HW11 : User Logout Class
*/

// Import Java Libraries
import java.io.*;
import java.util.List;
import java.util.ArrayList;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

public class Logout extends HttpServlet
{
  public void doPost(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException
  {
    HttpSession session = req.getSession(true); //get or create session object
    ServletContext context = session.getServletContext(); //get application/context object
  
    //reset session objects
    session.setAttribute("username","");
    
    //String locJSP = "http://apps-swe432.vse.gmu.edu:8080/swe432/jsp/jhowdes1/index.jsp";
    //String locJSP = "http://localhost:8080/experiment/HLVoting/index.jsp";
    String locJSP = "http://localhost:8080/index.jsp";
    res.sendRedirect(locJSP);
  }
}
