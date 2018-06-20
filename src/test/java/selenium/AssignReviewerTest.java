package selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignReviewerTest extends TestBase {

    private static final String PAPER_TITLE = "Paper title 1528634398805";

    @Test
    public void asConferenceOrganizerAssignReviewerHappyPath() {
        //given

        //when
        driver.findElement(By.cssSelector("form[action*='login'] button")).click();

        //then
        assertEquals("Login Page", driver.getTitle());

        //when
        driver.findElement(By.id("email")).sendKeys(CONFERENCE_ORGANIZER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(CONFERENCE_ORGANIZER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        //then
        assertEquals("Home page", driver.getTitle());

        //when
        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        //then
        assertEquals("Manage Paper", driver.getTitle());

        //when
        List<WebElement> choosePaperTableHeaders = driver.findElements(By.cssSelector("tr th"));
        List<String> choosePaperHeaders = asList("Title", "Status", "Number of assigned reviewers", "Assign reviewer");

        //then
        assertTrue(choosePaperTableHeaders.stream().map(WebElement::getText).allMatch(choosePaperHeaders::contains));

        //when
        driver.findElement(By.cssSelector("a[href*='" + PAPER_TITLE + "']")).click();

        //then
        assertEquals(PAPER_TITLE, driver.findElement(By.cssSelector("h1")).getText());

        //when
        List<WebElement> assignReviewerTableHeaders = driver.findElements(By.cssSelector("tr th"));
        List<String> assignReviewerHeaders = asList("Name", "LastName", "E-mail", "Number of assigned papers", "Conflict factor", "Assign reviewer");

        //then
        assertTrue(assignReviewerTableHeaders.stream().map(WebElement::getText).allMatch(assignReviewerHeaders::contains));

        //when
        driver.findElement(By.cssSelector("a[href*='" + REVIEWER_EMAIL + "']")).click();

        //then
        assertEquals(PAPER_TITLE + " has been assigned to " + REVIEWER_EMAIL, driver.findElement(By.cssSelector("h1")).getText());

        //when
        driver.findElement(By.cssSelector("button")).click();
        driver.findElement(By.cssSelector("form[action*='home-organizer'] button")).click();
        driver.findElement(By.cssSelector("form[action*='logout'] button")).click();

        driver.findElement(By.cssSelector("form[action*='login'] button")).click();
        driver.findElement(By.id("email")).sendKeys(REVIEWER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(REVIEWER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();

        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        //then
        assertEquals("Manage Paper", driver.getTitle());

        //when
        List<WebElement> papersTitles = driver.findElements(By.xpath("//tbody/tr/td[1]"));

        //then
        assertTrue(papersTitles.stream().map(WebElement::getText).anyMatch(o -> o.equals(PAPER_TITLE)));
    }

    @Test
    public void asConferenceOrganizerAssignReviewerMaxNoOfReviewersAssigned() {
        //when
        driver.findElement(By.cssSelector("form[action*='login'] button")).click();
        driver.findElement(By.id("email")).sendKeys(CONFERENCE_ORGANIZER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(CONFERENCE_ORGANIZER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();
        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        driver.findElement(By.xpath("//tr[td='5']//a")).click();
        driver.findElement(By.cssSelector("a[href*='" + REVIEWER_EMAIL + "']")).click();

        //then
        assertEquals("Maximum number of reviewers is already assigned", driver.findElement(By.cssSelector("h1")).getText());
    }

    @Test
    public void asConferenceOrganizerAssignReviewerToPaperHiIsAlreadyAssignedTo() {
        //when
        driver.findElement(By.cssSelector("form[action*='login'] button")).click();
        driver.findElement(By.id("email")).sendKeys(CONFERENCE_ORGANIZER_EMAIL);
        driver.findElement(By.id("password")).sendKeys(CONFERENCE_ORGANIZER_PASSWORD);
        driver.findElement(By.className("signupbtn")).click();
        driver.findElement(By.cssSelector("form[action*='files'] button")).click();

        driver.findElement(By.cssSelector("a[href*='" + PAPER_TITLE + "']")).click();
        driver.findElement(By.cssSelector("a[href*='" + REVIEWER_EMAIL + "']")).click();

        //then
        assertEquals("This paper is already assigned to this reviewer", driver.findElement(By.cssSelector("h1")).getText());
    }
}
