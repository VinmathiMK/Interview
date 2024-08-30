package InterviewPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Duration;
import java.util.*;


public class DownloadFile {

	public static void main(String[] args) {
        // Set the path to the geckodriver executable
       // System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");

        // Initialize Firefox WebDriver
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Step 1: Launch the website
            driver.get("https://www.lambdatest.com/selenium-playground/table-data-download-demo");

            // Step 2: Click on a file format to start the download
            WebElement csvButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("CSV")));
            csvButton.click();

            // Wait for the download to complete
            Thread.sleep(5000); // Adjust sleep time as needed

            // Step 3: Verify downloaded content vs values in UI
            // Get table data from the UI
            WebElement table = driver.findElement(By.id("example"));
            List<WebElement> rows = table.findElements(By.tagName("tr"));
            
            
            StringBuilder uiData = new StringBuilder();
            // Start from index 1 to skip the header row
            for (int i = 1; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                List<WebElement> cells = row.findElements(By.tagName("td"));
                for (WebElement cell : cells) {
                    uiData.append(cell.getText()).append(",");
                }
                uiData.append("\n");
            }

            // Read downloaded file content
            File downloadDir = new File(System.getProperty("user.home") + "/Downloads");
            File[] files = downloadDir.listFiles((dir, name) -> name.endsWith("Cloud.csv"));
            if (files == null || files.length == 0) {
                System.out.println("No downloaded file found");
                return;
            }

            File downloadedFile = files[0];
            StringBuilder downloadedData = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new FileReader(downloadedFile))) {
                // Skip the first line (header)
                br.readLine();
                String line;
                while ((line = br.readLine()) != null) {
                    downloadedData.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Compare UI data and downloaded file data
            String uiDataString = uiData.toString().trim().replaceAll("\\r\\n|\\r|\\n", "\n");
            String downloadedDataString = downloadedData.toString().trim().replaceAll("\\r\\n|\\r|\\n", "\n");

            if (uiDataString.equals(downloadedDataString)) {
                System.out.println("Data matches successfully!");
            } else {
                System.out.println("Data does not match!");
                // Print differences for debugging
                System.out.println("UI Data:\n" + uiDataString);
                System.out.println("Downloaded Data:\n" + downloadedDataString);
            }
            
            
            
             
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clean up
            driver.quit();
        }
    }
	
	
	
}
