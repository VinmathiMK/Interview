package VinGroup.Mavenroject;

import com.example.config.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.time.Duration;

public class BookAppointmentSteps {
	
	WebDriver driver;
    ConfigReader config = new ConfigReader();

    @Given("the user is on the CURA Healthcare login page")
    public void user_is_on_login_page() {
    	
    	driver = new FirefoxDriver();
        String url = config.getProperty("url").trim();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @When("the user logs in with valid credentials")
    public void user_logs_in_with_valid_credentials() {
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        driver.findElement(By.id("btn-make-appointment")).click();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("btn-login")).click();
    }
    
    @And("the user books an appointment with the following details:")
    public void user_books_an_appointment_with_details(io.cucumber.datatable.DataTable dataTable) {
        var data = dataTable.asMaps(String.class, String.class).get(0);

        WebElement appointmentHeader = driver.findElement(By.xpath("//h2[contains(text(),'Make Appointment')]"));
        Assert.assertTrue("Login failed! Please ensure the username and password are valid.", appointmentHeader.isDisplayed());

        driver.findElement(By.id("combo_facility")).sendKeys(data.get("Facility"));
        driver.findElement(By.id("radio_program_medicaid")).click();
        driver.findElement(By.id("txt_visit_date")).sendKeys(data.get("Visit Date"));
        driver.findElement(By.id("txt_comment")).sendKeys(data.get("Comment"));
        driver.findElement(By.id("btn-book-appointment")).click();
    }

    @Then("the appointment should be confirmed")
    public void appointment_should_be_confirmed() {
        WebElement confirmationHeader = driver.findElement(By.xpath("//h2[contains(text(),'Appointment Confirmation')]"));
        Assert.assertTrue("Appointment confirmation failed!", confirmationHeader.isDisplayed());
    }
    
    @And("the submitted data should be verified")
    public void submitted_data_should_be_verified() {
        Assert.assertEquals("Hongkong CURA Healthcare Center", driver.findElement(By.id("facility")).getText());
        Assert.assertEquals("No", driver.findElement(By.id("hospital_readmission")).getText());
        Assert.assertEquals("Medicaid", driver.findElement(By.id("program")).getText());
        Assert.assertEquals("today", driver.findElement(By.id("comment")).getText());
    }

    @And("the user logs out")
    public void user_logs_out() {
        driver.findElement(By.id("menu-toggle")).click();
        driver.findElement(By.linkText("Logout")).click();
        driver.quit();
    }


}
