import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class USStates {
    static WebDriver driver;

    @Test
    public void verifyStates(){
        driver = new ChromeDriver();
        driver.get("https://petdiseasealerts.org/forecast-map#/heartworm-canine/dog/united-states");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));

        String[] statesToValidate = {"maryland", "california", "new-york", "florida"};
        for (int i = 0; i < statesToValidate.length; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[name()='svg']//*[local-name()='g' and @id='" + statesToValidate[i] + "']")));
            WebElement state = driver.findElement(By.xpath("//*[name()='svg']//*[local-name()='g' and @id='" + statesToValidate[i] + "']"));

            switch (statesToValidate[i]) {
                case "maryland":
                    //Click on particular state
                    state.click();
                    //Wait for state to open
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/span[contains(text(),'Maryland')]")));
                    //Assert that is current state opened
                    Assert.assertTrue(driver.findElement(By.xpath("//li/span[contains(text(),'Maryland')]")).isDisplayed());
                    //Go back to whole map
                    driver.findElement(By.xpath("//li/a[contains(text(),'United')]")).click();
                    break;
                    //Start all again with different states
                case "california":
                    state.click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/span[contains(text(),'California')]")));
                    Assert.assertTrue(driver.findElement(By.xpath("//li/span[contains(text(),'California')]")).isDisplayed());
                    driver.findElement(By.xpath("//li/a[contains(text(),'United')]")).click();
                    break;
                case "new-york":
                    state.click();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/span[contains(text(),'New')]")));
                    Assert.assertTrue(driver.findElement(By.xpath("//li/span[contains(text(),'New')]")).isDisplayed());
                    driver.findElement(By.xpath("//li/a[contains(text(),'United')]")).click();
                    break;
                case "florida":
                    //Florida was hardest from me to click on, so I decided to do with offset, above code doesn't work for Florida
                    actions.moveToElement(state).moveByOffset(60,0).click().perform();
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li/span[contains(text(),'Flori')]")));
                    Assert.assertTrue(driver.findElement(By.xpath("//li/span[contains(text(),'Flori')]")).isDisplayed());
                    driver.findElement(By.xpath("//li/a[contains(text(),'United')]")).click();
                    break;
            }
        }
    }


}
