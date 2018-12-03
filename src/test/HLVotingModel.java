package test;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HLVotingModel implements FsmModel {

//    private enum GradeBoxState {Empty, NotEmpty}
    private WebDriver driver;
    private String url = "http://localhost:8080";

    private enum State {NotLogin, Login};

    private State state = State.NotLogin;

    public Object getState() {return state;}

    public void reset(boolean b) {
        state = State.NotLogin;
    }

    public void setUpWebDriver(){
        System.setProperty("webdriver.chrome.driver", "/Users/WestChen/IdeaProjects/HLVoting/src/tools/chromedriver");    // specify path the the driver
        driver = new ChromeDriver();    // create an instance of the web browser and open it
        driver.get(url);
    }

    public void teardown()
    {
        driver.quit();                  // close the browser
    }

    @Action
    public void login(){
        setUpWebDriver();
        assertEquals(driver.getTitle(), "The Prediction Page");
        if(state == State.NotLogin){
            state = State.Login;
        }
        teardown();
    }
}
