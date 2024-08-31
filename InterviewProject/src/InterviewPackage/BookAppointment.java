package InterviewPackage;

import org.asynchttpclient.util.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.example.config.ConfigReader;


import com.example.config.ConfigReader;

import java.time.Duration;


public class BookAppointment {
	
	public static void main(String[] args) {
		
		ConfigReader config = new ConfigReader();
        // Retrieve values from configuration file
        String url = config.getProperty("url");
        String username = config.getProperty("username");
        String password = config.getProperty("password");
        System.out.println("URL: " + url);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
		
	
        WebDriver driver = new FirefoxDriver();

        // Navigate to the website
        driver.get(url);
        driver.getTitle();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        
        // Click on "Make Appointment" button to go to the login page
        WebElement makeAppointment = driver.findElement(By.id("btn-make-appointment"));
        makeAppointment.click();
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        WebElement submit = driver.findElement(By.id("btn-login"));
        submit.click();
        
        // Assert if login is successful by checking URL or element on the landing page
        WebElement appointmentHeader = driver.findElement(By.xpath("//h2[contains(text(),'Make Appointment')]"));
        Assert.assertTrue(appointmentHeader.isDisplayed(), "Login failed! Please ensure the username and password are valid.");
        
        
        // Select facility
        WebElement facilityDropdown = driver.findElement(By.id("combo_facility"));
        facilityDropdown.sendKeys("Hongkong CURA Healthcare Center");
        
        // Select healthcare program (Medicaid)
        WebElement medicaidRadio = driver.findElement(By.id("radio_program_medicaid"));
        medicaidRadio.click();

        // Set the visit date
        WebElement visitDateField = driver.findElement(By.id("txt_visit_date"));
        visitDateField.sendKeys("31/08/2024"); 

        // Enter a comment
        WebElement commentField = driver.findElement(By.id("txt_comment"));
        commentField.sendKeys("today");

        // Click on the "Book Appointment" button
        WebElement bookAppointmentButton = driver.findElement(By.id("btn-book-appointment"));
        bookAppointmentButton.click();
        
        // Verify appointment confirmation
        WebElement confirmationHeader = driver.findElement(By.xpath("//h2[contains(text(),'Appointment Confirmation')]"));
        
        
         // Open the menu
        WebElement menuButton = driver.findElement(By.id("menu-toggle"));
        menuButton.click();
        
        // Click on "History" from the menu
        WebElement historyLink = driver.findElement(By.linkText("History"));
        historyLink.click();
        
        //Verify the submitted data
        WebElement data = driver.findElement(By.id("facility"));
        String actualElementText = data.getText();
        String expectedElementText = "Hongkong CURA Healthcare Center";
        Assert.assertEquals(actualElementText, expectedElementText,"Expected and Actual are same");
        System.out.println("facility data");
        
        WebElement data1 = driver.findElement(By.id("hospital_readmission"));
        String actualElementText1 = data1.getText();
        String expectedElementText1 = "No";
        Assert.assertEquals(actualElementText, expectedElementText,"Expected and Actual are same");
        System.out.println("hospital_readmission data");
        
        WebElement data2 = driver.findElement(By.id("program"));
        String actualElementText2 = data2.getText();
        String expectedElementText2 = "Medicaid";
        Assert.assertEquals(actualElementText, expectedElementText,"Expected and Actual are same");
        System.out.println("program data");
        
       
        WebElement data3 = driver.findElement(By.id("comment"));
        String actualElementText3 = data3.getText();
        String expectedElementText3 = "today";
        Assert.assertEquals(actualElementText, expectedElementText,"Expected and Actual are same");
        System.out.println("Submitted datas are verified");
        
        
        //logout the website
        driver.findElement(By.id("menu-toggle")).click();
        WebElement logoutLink = driver.findElement(By.linkText("Logout"));
        logoutLink.click();
        System.out.println("Logout");


        driver.quit();
    }
}

