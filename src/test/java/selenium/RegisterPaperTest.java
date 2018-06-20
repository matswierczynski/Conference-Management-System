package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.*;

public class RegisterPaperTest extends TestBase {

    @Test
    public void asScientistUploadPaperHappyPath() {
        //given&when
        driver.findElement(By.cssSelector("form[action*='login'] button")).click();

        //then
        assertEquals("Login Page", driver.getTitle());

        //when
        driver.findElement(By.id("email")).sendKeys(SCIENTIST_EMAIL);
        driver.findElement(By.id("password")).sendKeys(SCIENTIST_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        //then
        assertEquals("Home page", driver.getTitle());

        //when
        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        //then
        assertEquals("Manage Paper", driver.getTitle());

        //when
        driver.findElement(By.cssSelector("form[action*='upload'] button")).click();

        //then
        assertEquals("Upload Form", driver.getTitle());

        //when
        String paperTitle = "Paper title " + System.currentTimeMillis();
        System.out.println(paperTitle);
        driver.findElement(By.id("title")).sendKeys(paperTitle);
        driver.findElement(By.id("paper")).sendKeys(PATH_TO_CODE + "/Conference-Management-System/src/test/resources/testPaper.pdf");
        driver.findElement(By.id("abstract")).sendKeys(PATH_TO_CODE + "/Conference-Management-System/src/test/resources/testAbstract.pdf");
        driver.findElement(By.cssSelector("input[value='Submit']")).click();

        //then
        assertEquals("Upload Form", driver.getTitle());
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Paper Details");
        assertEquals(driver.findElement(By.xpath("//div[@class='container']/div[2]/div[2]/label")).getText(), paperTitle);
        assertEquals(driver.findElement(By.cssSelector("a[href*=paper]")).getText(), "Download paper");
        assertEquals(driver.findElement(By.cssSelector("a[href*=abstract]")).getText(), "Download abstract");
        assertEquals(driver.findElement(By.xpath("//div[@class='container']/div[5]/div[2]/label")).getText(), "PENDING");
        assertEquals(driver.findElement(By.xpath("//div[@class='container']/div[6]/div[2]/label")).getText(), "0");

        //when
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("form[action*='home-scientist'] button")).click();
        driver.findElement(By.cssSelector("form[action*='logout'] button")).click();

        driver.findElement(By.cssSelector("form[action*='login'] button")).click();
        driver.findElement(By.id("email")).sendKeys(CONFERENCE_ORGANIZER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(CONFERENCE_ORGANIZER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        //then
        assertEquals("Manage Paper", driver.getTitle());

        //when
        List<WebElement> papersTitles = driver.findElements(By.xpath("//tbody/tr/td[1]"));

        //then
        assertTrue(papersTitles.stream().map(WebElement::getText).anyMatch(o -> o.equals(paperTitle)));
    }

    @Test
    public void asScientistUploadPaperFieldsValidation() {
        //given&when
        driver.findElement(By.cssSelector("form[action*='login'] button")).click();

        driver.findElement(By.id("email")).sendKeys(SCIENTIST_EMAIL);
        driver.findElement(By.id("password")).sendKeys(SCIENTIST_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        driver.findElement(By.cssSelector("form[action*='files'] button")).click();
        driver.findElement(By.cssSelector("form[action*='upload'] button")).click();

        //then
        assertEquals("Upload Form", driver.getTitle());

        //when
        driver.findElement(By.cssSelector("input[value='Submit']")).click();

        //then
        assertEquals("Title must have at least 5 characters", driver.findElement(By.xpath("//form[@action='/root/user/upload']/div[2]")).getText());
        assertEquals("File cannot be empty", driver.findElement(By.xpath("//form[@action='/root/user/upload']/div[4]")).getText());
        assertEquals("File cannot be empty", driver.findElement(By.xpath("//form[@action='/root/user/upload']/div[6]")).getText());

        //when
//        driver.findElement(By.id("title")).sendKeys("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin malesuada, nulla eu ullamcorper metus.");
//        driver.findElement(By.cssSelector("input[value='Submit']")).click();

        //then
//        assertEquals(driver.findElement(By.cssSelector("//form[@action='/root/user/upload']/div[2]")).getText(), "Title must have at most 100 characters");

        //when
        String paperTitle = "Paper title " + System.currentTimeMillis();
        driver.findElement(By.id("title")).sendKeys(paperTitle);
        driver.findElement(By.id("paper")).sendKeys(PATH_TO_CODE + "/Conference-Management-System/src/test/resources/testExcelFile.xlsx");
        driver.findElement(By.id("abstract")).sendKeys(PATH_TO_CODE + "/Conference-Management-System/src/test/resources/testExcelFile.xlsx");
        driver.findElement(By.cssSelector("input[value='Submit']")).click();

        //then
        assertEquals("Please upload valid pdf file only", driver.findElement(By.xpath("//form[@action='/root/user/upload']/div[4]")).getText());
        assertEquals("Please upload valid pdf file only", driver.findElement(By.xpath("//form[@action='/root/user/upload']/div[6]")).getText());

        //when
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("form[action*='home-scientist'] button")).click();
        driver.findElement(By.cssSelector("form[action*='logout'] button")).click();

        driver.findElement(By.cssSelector("form[action*='login'] button")).click();
        driver.findElement(By.id("email")).sendKeys(CONFERENCE_ORGANIZER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(CONFERENCE_ORGANIZER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        //then
        assertEquals("Manage Paper", driver.getTitle());

        //when
        List<WebElement> papersTitles = driver.findElements(By.xpath("//tbody/tr/td[1]"));

        //then
        assertFalse(papersTitles.stream().map(WebElement::getText).anyMatch(o -> o.equals(paperTitle)));
    }
}
