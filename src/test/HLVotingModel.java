package test;

import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HLVotingModel implements FsmModel {

//    private enum GradeBoxState {Empty, NotEmpty}
    private WebDriver driver;
    private String url = "http://localhost:8080";

    private enum State {NotLogin, Login, PredictionPageWithOnlyOwnPrediction, PredictionPageWithOnlyOtherPrediction,
        PredictionPageWithOwnAndOtherPrediction, PredictionPageWithoutPrediction, NewPredictionPage, InvisibleState};

    private State state = State.NotLogin;

    private Set<Integer> registerIds = new HashSet<>();

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

    public void validLogin(){
        driver.findElement(By.name("userLogin")).sendKeys("clu3");
        driver.findElement(By.name("userPass")).sendKeys("123456");
        driver.findElement(By.name("loginButton")).click();
    }

    public void invalidLogin(){
        driver.findElement(By.name("userLogin")).sendKeys("invalid");
        driver.findElement(By.name("userPass")).sendKeys("123456");
        driver.findElement(By.name("loginButton")).click();
    }


    @Action
    public void login_with_valid_account(){
        setUpWebDriver();
        if(state == State.NotLogin){
            validLogin();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            assertEquals("validLoginUnsuccessfully", true, driver.getPageSource().contains("Logged in as "));
            state = State.Login;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void login_with_invalid_account(){
        setUpWebDriver();
        if(state == State.NotLogin){
            invalidLogin();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            assertEquals("invalidLoginUnsuccessfully", true, driver.getPageSource().contains("Username does not exist."));
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void register(){
        setUpWebDriver();
        if(state == State.NotLogin){
            Random random = new Random();
            int id = random.nextInt(1000);
            while(!registerIds.add(id)){
                id = random.nextInt(1000);
            }
            driver.findElement(By.name("userName")).sendKeys("test" + id);
            driver.findElement(By.name("userEmail")).sendKeys("test" + id+ "gmail.com");
            driver.findElement(By.name("firstPass")).sendKeys("123");
            driver.findElement(By.name("secPass")).sendKeys("123");
            driver.findElement(By.name("newUserButton")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            assertEquals("registerUnsuccessfully", true, driver.getPageSource().contains("Logged in as "));
            state = State.Login;
        }else {
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void viewPredictionsAfterLogin(){
        setUpWebDriver();
        validLogin();
        if(state == State.Login){
            driver.findElement(By.name("viewSubmit")).click();
            boolean viewPredictionNoPredictions =  driver.getPageSource().contains("No predictions have been made at this time.");
            boolean viewPredictionWithPredictions = driver.getPageSource().contains("Predictions ordered by the most agreed with: ");
            boolean viewPrediction = viewPredictionNoPredictions || viewPredictionWithPredictions;
            assertEquals("viewPredictionUnsuccessfully", viewPrediction, true);
            boolean delete = driver.getPageSource().contains("Your arguments");
            boolean vote = driver.getPageSource().contains("Their arguments:");
            if(delete && vote)
                state = State.PredictionPageWithOwnAndOtherPrediction;
            else if(delete)
                state = State.PredictionPageWithOnlyOwnPrediction;
            else if(vote)
                state = State.PredictionPageWithOnlyOtherPrediction;
            else
                state = State.PredictionPageWithoutPrediction;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void turnToNewPredictionsPage(){
        setUpWebDriver();
        validLogin();
        if(state == State.Login){
            driver.findElement(By.name("predSubmit")).click();
            boolean newPredictionPage = driver.getPageSource().contains("If you do not want to make a prediction and wish to view previous submissions:");
            assertEquals("viewPredictionUnsuccessfully", true, newPredictionPage);
            state = State.NewPredictionPage;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void makeNewPrediction(){
        setUpWebDriver();
        validLogin();
        driver.findElement(By.name("predSubmit")).click();
        if(state == State.NewPredictionPage){
            Random random = new Random();
            int randomIndex = random.nextInt(10);
            driver.findElement(By.name("prediction")).sendKeys("myOwnPrediction" + randomIndex);
            driver.findElement(By.name("arguments")).sendKeys("myOwnArguments" + randomIndex);
            driver.findElement(By.name("predAdd")).click();
            boolean viewPredictionNoPredictions =  driver.getPageSource().contains("No predictions have been made at this time.");
            boolean viewPredictionWithPredictions = driver.getPageSource().contains("Predictions ordered by the most agreed with:");
            boolean viewPrediction = viewPredictionNoPredictions || viewPredictionWithPredictions;
            assertEquals("viewPredictionUnsuccessfully", true, viewPrediction);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            boolean vote = driver.getPageSource().contains("Their arguments:");
            if(vote)
                state = State.PredictionPageWithOwnAndOtherPrediction;
            else
                state = State.PredictionPageWithOnlyOwnPrediction;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void viewPredictionsOnNewPredictionPage(){
        setUpWebDriver();
        validLogin();
        driver.findElement(By.name("predSubmit")).click();
        if(state == State.NewPredictionPage){
            driver.findElement(By.name("viewSubmit")).click();
            boolean viewPredictionNoPredictions =  driver.getPageSource().contains("No predictions have been made at this time.");
            boolean viewPredictionWithPredictions = driver.getPageSource().contains("Predictions ordered by the most agreed with:");
            boolean viewPrediction = viewPredictionNoPredictions || viewPredictionWithPredictions;
            assertEquals("viewPredictionUnsuccessfully", true, viewPrediction);
            boolean delete = driver.getPageSource().contains("Your arguments");
            boolean vote = driver.getPageSource().contains("Their arguments:");
            if(delete && vote)
                state = State.PredictionPageWithOwnAndOtherPrediction;
            else if(delete)
                state = State.PredictionPageWithOnlyOwnPrediction;
            else if(vote)
                state = State.PredictionPageWithOnlyOtherPrediction;
            else
                state = State.PredictionPageWithoutPrediction;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void deletePrediction(){
        setUpWebDriver();
        validLogin();
        driver.findElement(By.name("viewSubmit")).click();
        if(state == State.PredictionPageWithOnlyOwnPrediction){
            driver.findElement(By.xpath("(//input[@value='Delete Prediction'])[1]")).click();
            boolean delete = driver.getPageSource().contains("Your arguments");
            if(!delete){
                state = State.PredictionPageWithoutPrediction;
            }
        }else if(state == State.PredictionPageWithOwnAndOtherPrediction){
//            WebElement element = driver.findElement(By.xpath("(//input[@action='http://localhost:8080/Delete'])[1]"));
//            element.submit();
            driver.findElement(By.xpath("(//input[@value='Delete Prediction'])[1]")).click();
//            boolean delete = doesWebElementExist(driver, By.name("deletePred"));
            boolean delete = driver.getPageSource().contains("Your arguments");
            if(!delete){
                state = State.PredictionPageWithOnlyOtherPrediction;
            }
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void logOut(){
        setUpWebDriver();
        validLogin();
        if(state == State.Login || state == State.NewPredictionPage){
            driver.findElement(By.name("logoutsubmit")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            assertEquals("logOutUnsuccessfully", true, driver.getPageSource().contains("Homework 11 : Submitting all modifications"));
            state = State.NotLogin;
        }else{
            state = State.InvisibleState;
        }
        teardown();
    }

    @Action
    public void resetApplication(){
        setUpWebDriver();
        validLogin();
        if(state == State.NotLogin || state == State.InvisibleState || state == State.NewPredictionPage){
            state = State.InvisibleState;
        }else{
            driver.findElement(By.name("resetSubmit")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.print(e.toString());
            }
            assertEquals("reSetUnsuccessfully", true, driver.getPageSource().contains("Register New User:"));
            state = State.NotLogin;
        }
        teardown();
    }
}
