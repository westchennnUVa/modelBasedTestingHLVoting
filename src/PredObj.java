// HW11 Servlet Vote Update/Viewer

// Import Java Libraries
import java.io.*;
import java.util.*;
//import java.util.List;
//import java.util.ArrayList;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;

public class PredObj implements Comparable
{
  String username;
  String prediction;
  String arguments;
  int[] votes;
  
  public PredObj(String u, String p, String a, int[] v)
  {
    username = u;
    prediction = p;
    arguments = a;
    votes = v;
  }
  
  public int compareTo(Object obj)
  {
    PredObj temp = (PredObj) obj;
    
    if(this.votes[0] < temp.votes[0])
      return -1;
    else if(this.votes[0] > temp.votes[0])
      return 1;
    else
      return 0;
  }
  
  public String getUser()
  {
    return username;
  }
  
  public String getPred()
  {
    return prediction;
  }
  
  public String getArgs()
  {
    return arguments;
  }
  
  public int getAgree()
  {
    return votes[0];
  }
  
  public int getUnsure()
  {
    return votes[1];
  }
  
  public int getDisagree()
  {
    return votes[2];
  }
  
  public void setVotes(int v)
  {
    votes[v]++;
  }
}