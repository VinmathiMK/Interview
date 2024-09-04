package VinGroup.Mavenroject;

import com.example.config.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DownloadsFileStep {
	
	WebDriver driver;
    WebDriverWait wait;
    private List<String> uiData;
    private List<String> downloadedData;
	
	

    @Given("I launch the website")
    public void i_launch_the_website() {
    	
        // Set up Firefox driver using WebDriverManager
    	ConfigReader config = new ConfigReader();
    	driver = new FirefoxDriver();
    	// Launch the website
        String url = config.getProperty("link");
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        
    }
    
    @When("I click on a file format to start download")
    public void i_click_on_a_file_format_to_start_download() throws InterruptedException {
        // Click on CSV format link
        WebElement csvButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CSV")));
        csvButton.click();
        System.out.println("file downloaded");

        // Wait for download to complete (adjust time as needed)
        Thread.sleep(5000);
    
    }
    
    @Then("I verify downloaded content matches values in UI")
    public void i_verify_the_downloaded_content_matches_the_first_3_rows_of_the_table() throws IOException {
    	
    	// Step 1: Get table data from the UI
        WebElement table = driver.findElement(By.id("example"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        uiData = new ArrayList<>();
        // Start from index 1 to skip the header row
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            StringBuilder rowData = new StringBuilder();
            for (WebElement cell : cells) {
                rowData.append(cell.getText()).append(",");
            }
            uiData.add(rowData.toString().replaceAll(",$", "").trim()); // Remove trailing comma and trim
        }

        // Step 2: Read downloaded file content
        File downloadDir = new File(System.getProperty("user.home") + "/Downloads");
        File[] files = downloadDir.listFiles((dir, name) -> name.endsWith("Cloud.csv"));
        if (files == null || files.length == 0) {
            Assert.fail("No downloaded file found");
        }

        File downloadedFile = files[0];
        downloadedData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(downloadedFile))) {
            br.readLine(); // Skip the first line (header)
            String line;
            while ((line = br.readLine()) != null) {
                downloadedData.add(line.replaceAll("\"", "").replaceAll(",$", "").trim()); // Remove trailing comma and quotes, trim
            }
        }

        // Step 3: Compare UI data and downloaded file data row by row
        if (uiData.size() != downloadedData.size()) {
            Assert.fail("Mismatch in the number of visible rows between UI and downloaded data.");
        }

        for (int i = 0; i < uiData.size(); i++) {
            Assert.assertEquals(downloadedData.get(i), uiData.get(i), "Row " + (i + 1) + " does not match!");
        }

        // Print success message if all data matches
        System.out.println("Success: All visible rows match between UI and downloaded data.");

        // Clean up
        driver.quit();
    }
}