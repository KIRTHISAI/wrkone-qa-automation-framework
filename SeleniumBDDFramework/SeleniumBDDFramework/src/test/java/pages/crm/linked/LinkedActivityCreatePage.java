package pages.crm.linked;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import model.LinkedCRMActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;


import pages.crm.general.GeneralActivityCreatePage;

public class LinkedActivityCreatePage extends GeneralActivityCreatePage {

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

	public LinkedActivityCreatePage(WebDriver driver) {

	    super(driver);

	    if (driver == null) {
	        throw new IllegalArgumentException(
	                "WebDriver cannot be null");
	    }

	    // IMPORTANT:
	    // Make sure the inherited driver field contains
	    // the actual WebDriver instance.
	    this.driver = driver;

	    System.out.println("==========================================");
	    System.out.println("LinkedActivityCreatePage initialized successfully.");
	    System.out.println("Driver initialized = " + (this.driver != null));
	    System.out.println("Wait initialized = " + (this.wait != null));
	    System.out.println("==========================================");
	}

    // =========================================================
    // LINKED ACTIVITY RADIO BUTTON
    // =========================================================

    private final By linkedActivity =
            By.id("activity-create-page-category-radio-lead");

    private final By leadNameButton =
            By.id("activity-create-page-lead-name-button");

    // =========================================================
    // LEAD MODAL
    // =========================================================

    private final By leadModal =
            By.id("lead-modal");

    // =========================================================
    // LEAD LIST
    // =========================================================

    private final By leadList =
            By.id("lead-list");

    // =========================================================
    // LINK TO STAGE
    // =========================================================
    //
    // Screenshot confirms:
    //
    // <div id="activity-stage-link-container">
    //     <select>...</select>
    // </div>
    //
    // =========================================================

    private final By linkToStage =
            By.cssSelector(
                    "#activity-stage-link-container select"
            );

    private final By linkedPurpose =
            By.xpath("//*[normalize-space()='Purpose']/following::select[1]");

    // =========================================================
    // CREATE LINKED ACTIVITY
    // =========================================================

    public void createLinkedActivity(LinkedCRMActivity activity) {

        if (activity == null) {
            throw new IllegalArgumentException(
                    "LinkedCRMActivity data cannot be null");
        }

        System.out.println();
        System.out.println("==========================================");
        System.out.println("CREATING LINKED ACTIVITY FROM EXCEL");
        System.out.println("==========================================");

        System.out.println("Lead Name       = " + activity.getLeadName());
        System.out.println("Activity Type   = " + activity.getActivityType());
        System.out.println("Purpose         = " + activity.getPurpose());
        System.out.println("Description     = " + activity.getDescription());
        System.out.println("Link To Stage   = " + activity.getLinkToStage());
        System.out.println("Date            = " + activity.getDate());
        System.out.println("Start Time      = " + activity.getStartTime());
        System.out.println("End Time        = " + activity.getEndTime());
        System.out.println("Assignment Type = " + activity.getAssignmentType());

        selectLinkedActivity();
        System.out.println("Linked Activity radio selected.");
        selectLead(activity.getLeadName());
        System.out.println("Lead selected from Excel.");
        selectLinkToStage(activity.getLinkToStage());
        System.out.println("Link To Stage selected from Excel.");
        selectActivityType(activity.getActivityType());
        System.out.println("Activity Type reused from General Activity flow.");
        selectLinkedPurpose(activity.getPurpose());
        enterDescription(activity.getDescription());
        enterDate(activity.getDate());
        selectStartTime(activity.getStartTime());
        selectEndTime(activity.getEndTime());
        selectAssignmentType(activity.getAssignmentType());
        handleAssignmentDetails(
                activity.getAssignmentType(),
                activity.getUser(),
                activity.getReason());
        System.out.println("Shared assignment fields completed.");
        clickCreateActivity();

        System.out.println("==========================================");
        System.out.println("LINKED ACTIVITY CREATION COMPLETED");
        System.out.println("==========================================");
        }

        private void selectLinkedPurpose(String purpose) {

                String value = required(purpose, "Purpose");

                WebElement purposeSelect = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(linkedPurpose));

                scrollIntoView(purposeSelect);
                new Select(purposeSelect).selectByVisibleText(value);
        }

        // =========================================================
    // SELECT LINKED ACTIVITY
    // =========================================================

        protected void selectLinkedActivity() {

        System.out.println();
        System.out.println(
                "Selecting Linked Activity...");

        WebElement radio =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                linkedActivity
                        )
                );

        scrollIntoView1(radio);

        if (!radio.isSelected()) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            radio
                    );
        }

        wait.until(driver -> {

            try {

                WebElement current =
                        driver.findElement(linkedActivity);

                return current.isSelected();

            } catch (Exception e) {

                return false;
            }
        });

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                leadNameButton));

        System.out.println(
                "Linked Activity selected successfully.");
    }

    // =========================================================
    // SELECT LEAD
    // =========================================================
 // =========================================================
 // SELECT LEAD - ROBUST VERSION
 // =========================================================

 public void selectLead(String leadName) {

 String requiredLead = required(leadName, "Lead Name");

     System.out.println();
     System.out.println("==========================================");
     System.out.println("SELECTING LEAD");
     System.out.println("Lead Name = " + requiredLead);
     System.out.println("==========================================");

     // ---------------------------------------------------------
     // 1. Click Lead Name field/button
     // ---------------------------------------------------------

     By leadButton = leadNameButton;

     try {

         WebElement button = wait.until(
                 ExpectedConditions.elementToBeClickable(leadButton)
         );

         scrollIntoView1(button);

         try {
             button.click();
         } catch (Exception e) {
             jsClick(button);
         }

     } catch (Exception e) {

         // Fallback: find element using text around Lead section
         By fallbackLeadButton = By.xpath(
                 "//*[contains(normalize-space(.), 'Lead')]"
                         + "/following::*[self::button or @role='button'][1]"
         );

         WebElement button = wait.until(
                 ExpectedConditions.elementToBeClickable(fallbackLeadButton)
         );

         scrollIntoView1(button);

         try {
             button.click();
         } catch (Exception ex) {
             jsClick(button);
         }
     }

     System.out.println("Lead Name button clicked.");

        String normalizedLead = normalizeText(requiredLead);

     // ---------------------------------------------------------
     // 2. Wait for Lead selection modal
     // ---------------------------------------------------------

     wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath(
                     "//*[contains(normalize-space(.), 'Select Lead')]"
             )
     ));

     System.out.println("Lead selection modal opened.");

     // ---------------------------------------------------------
     // 3. Wait for lead list to become visible
     // ---------------------------------------------------------

         wait.until(driver -> {
                 try {
                         if (!driver.findElements(leadList).isEmpty()
                                         && driver.findElement(leadList).isDisplayed()) {
                                 return true;
                         }

                         return driver.findElements(By.xpath(
                                         "//*[@role='option' or self::li or self::button]"))
                                         .stream()
                                         .anyMatch(WebElement::isDisplayed);
                 } catch (StaleElementReferenceException e) {
                         return false;
                 }
         });

     System.out.println("Lead list displayed.");

     // ---------------------------------------------------------
     // 4. Normalize required lead name
     // ---------------------------------------------------------

     // ---------------------------------------------------------
     // 5. FIRST ATTEMPT
     // Find exact visible text anywhere in the modal
     // ---------------------------------------------------------

     List<WebElement> candidates = new ArrayList<>();

     try {

         String xpath =
                 "//*[contains(translate(normalize-space(.),"
                         + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                         + " 'abcdefghijklmnopqrstuvwxyz'),"
                         + " '" + escapeXPath(normalizedLead) + "')]";

         candidates = driver.findElements(By.xpath(xpath));

     } catch (Exception e) {

         System.out.println(
                 "Primary lead search failed: " + e.getMessage()
         );
     }

     // ---------------------------------------------------------
     // 6. Find the best matching candidate
     // ---------------------------------------------------------

     WebElement leadElement = null;

     for (WebElement element : candidates) {

         try {

             if (!element.isDisplayed()) {
                 continue;
             }

             String text = normalizeText(element.getText());

             if (text.contains(normalizedLead)) {

                 leadElement = element;

                 System.out.println(
                         "Lead candidate found = " + element.getText()
                 );

                 break;
             }

         } catch (StaleElementReferenceException e) {
             // DOM refreshed - ignore and continue
         }
     }

     // ---------------------------------------------------------
     // 7. SECOND ATTEMPT
     // Search common clickable elements
     // ---------------------------------------------------------

     if (leadElement == null) {

         System.out.println(
                 "Exact lead element not found. Trying clickable elements..."
         );

         List<By> possibleLocators = Arrays.asList(

                 By.xpath(
                         "//button[contains(translate(normalize-space(.),"
                                 + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                 + " 'abcdefghijklmnopqrstuvwxyz'),"
                                 + " '" + escapeXPath(normalizedLead) + "')]"
                 ),

                 By.xpath(
                         "//*[@role='button'][contains(translate(normalize-space(.),"
                                 + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                 + " 'abcdefghijklmnopqrstuvwxyz'),"
                                 + " '" + escapeXPath(normalizedLead) + "')]"
                 ),

                 By.xpath(
                         "//li[contains(translate(normalize-space(.),"
                                 + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                 + " 'abcdefghijklmnopqrstuvwxyz'),"
                                 + " '" + escapeXPath(normalizedLead) + "')]"
                 ),

                 By.xpath(
                         "//div[contains(translate(normalize-space(.),"
                                 + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                 + " 'abcdefghijklmnopqrstuvwxyz'),"
                                 + " '" + escapeXPath(normalizedLead) + "')]"
                 )
         );

         for (By locator : possibleLocators) {

             try {

                 List<WebElement> elements =
                         driver.findElements(locator);

                 for (WebElement element : elements) {

                     if (element.isDisplayed()) {

                         String text =
                                 normalizeText(element.getText());

                         if (text.contains(normalizedLead)) {

                             leadElement = element;

                             System.out.println(
                                     "Lead found using fallback locator = "
                                             + element.getText()
                             );

                             break;
                         }
                     }
                 }

                 if (leadElement != null) {
                     break;
                 }

             } catch (Exception e) {
                 // Try next locator
             }
         }
     }

     // ---------------------------------------------------------
     // 8. THIRD ATTEMPT
     // Search modal text and click parent container
     // ---------------------------------------------------------

     if (leadElement == null) {

         System.out.println(
                 "Trying parent-container lead selection..."
         );

         By parentLocator = By.xpath(
                 "//*[contains(translate(normalize-space(.),"
                         + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                         + " 'abcdefghijklmnopqrstuvwxyz'),"
                         + " '" + escapeXPath(normalizedLead) + "')]/ancestor::*"
                         + "[self::button or @role='button' or self::li][1]"
         );

         try {

             List<WebElement> elements =
                     driver.findElements(parentLocator);

             for (WebElement element : elements) {

                 if (element.isDisplayed()) {

                     String text =
                             normalizeText(element.getText());

                     if (text.contains(normalizedLead)) {

                         leadElement = element;

                         System.out.println(
                                 "Lead parent container found = "
                                         + element.getText()
                         );

                         break;
                     }
                 }
             }

         } catch (Exception e) {

             System.out.println(
                     "Parent-container search failed: "
                             + e.getMessage()
             );
         }
     }

     // ---------------------------------------------------------
     // 9. If still not found, print modal DOM information
     // ---------------------------------------------------------

     if (leadElement == null) {

         System.out.println();
         System.out.println("==========================================");
         System.out.println("LEAD NOT FOUND");
         System.out.println("Required Lead = " + requiredLead);
         System.out.println("==========================================");

         printVisibleLeadModalText11();

         throw new TimeoutException(
                 "Lead '" + requiredLead
                         + "' was displayed/searchable but could not be selected."
         );
     }

     // ---------------------------------------------------------
     // 10. Scroll lead into view
     // ---------------------------------------------------------

     scrollIntoView1(leadElement);

     // ---------------------------------------------------------
     // 11. Wait until clickable
     // ---------------------------------------------------------

     try {

         wait.until(ExpectedConditions.elementToBeClickable(
                 leadElement
         ));

     } catch (Exception e) {

         System.out.println(
                 "Normal clickable wait failed. Using JavaScript click."
         );
     }

     // ---------------------------------------------------------
     // 12. Click lead
     // ---------------------------------------------------------

     try {

         leadElement.click();

     } catch (Exception e) {

         System.out.println(
                 "Normal click failed. Using JavaScript click."
         );

         jsClick(leadElement);
     }

     System.out.println("Lead clicked successfully.");

     // ---------------------------------------------------------
     // 13. Wait for modal to close
     // ---------------------------------------------------------

     wait.until(driver -> {

         try {

             List<WebElement> modals =
                     driver.findElements(
                             By.xpath(
                                     "//*[contains(normalize-space(.), 'Select Lead')]"
                             )
                     );

             for (WebElement modal : modals) {

                 if (modal.isDisplayed()) {
                     return false;
                 }
             }

             return true;

         } catch (StaleElementReferenceException e) {

             return true;

         } catch (Exception e) {

             return true;
         }
     });

     System.out.println("Lead selection modal closed.");

     // ---------------------------------------------------------
     // 14. Verify selected lead
     // ---------------------------------------------------------

     boolean selected = false;

     try {

         String pageText =
                 normalizeText(driver.getPageSource());

         selected =
                 pageText.contains(normalizedLead);

     } catch (Exception e) {

         System.out.println(
                 "Lead verification warning: " + e.getMessage()
         );
     }

     if (selected) {

         System.out.println(
                 "Lead selection verified successfully."
         );

     } else {

         System.out.println(
                 "Lead was clicked. Verification text was not found."
         );
     }

     System.out.println();
 }
//=========================================================
//NORMALIZE TEXT
//=========================================================
 protected String required(String value, String fieldName) {

	    if (value == null || value.trim().isEmpty()) {

	        throw new IllegalArgumentException(
	                fieldName + " cannot be empty");
	    }

	    return value.trim();
	}



private String normalizeText(String value) {

  if (value == null) {
      return "";
  }

  return value
          .replace("\u00A0", " ")
          .replaceAll("\\s+", " ")
          .trim()
          .toLowerCase();
}
//=========================================================
//SCROLL INTO VIEW
//=========================================================

protected void scrollIntoView(WebElement element) {

 try {

     JavascriptExecutor js =
             (JavascriptExecutor) driver;

     js.executeScript(
             "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
             element
     );

 } catch (Exception e) {

     System.out.println(
             "Unable to scroll element into view: "
                     + e.getMessage()
     );
 }
}
//=========================================================
//JAVASCRIPT CLICK
//=========================================================

protected void jsClick(WebElement element) {

 JavascriptExecutor js =
         (JavascriptExecutor) driver;

 js.executeScript(
         "arguments[0].click();",
         element
 );
}
//=========================================================
//ESCAPE STRING FOR XPATH
//=========================================================

private String escapeXPath(String value) {

 if (value == null) {
     return "";
 }

 if (!value.contains("'")) {
     return value;
 }

 if (!value.contains("\"")) {
     return value.replace("'", "");
 }

 StringBuilder result = new StringBuilder("concat(");

 String[] parts = value.split("'");

 for (int i = 0; i < parts.length; i++) {

     if (i > 0) {
         result.append(", \"'\", ");
     }

     result.append("'")
             .append(parts[i])
             .append("'");
 }

 result.append(")");

 return result.toString();
}
//=========================================================
//PRINT VISIBLE LEAD MODAL TEXT
//=========================================================

private void printVisibleLeadModalText11() {

 System.out.println();
 System.out.println("========== VISIBLE MODAL TEXT ==========");

 try {

     List<WebElement> elements =
             driver.findElements(
                     By.xpath(
                             "//*[contains(normalize-space(.), 'Select Lead')]"
                     )
             );

     for (WebElement element : elements) {

         try {

             if (element.isDisplayed()) {

                 String text = element.getText();

                 if (text != null && !text.trim().isEmpty()) {

                     System.out.println(
                             "MODAL TEXT:"
                     );

                     System.out.println(text);
                 }
             }

         } catch (StaleElementReferenceException e) {
             // Ignore stale element
         }
     }

 } catch (Exception e) {

     System.out.println(
             "Unable to print modal text: "
                     + e.getMessage()
     );
 }

 System.out.println(
         "========================================"
 );
}

    // =========================================================
    // LINK TO STAGE
    // =========================================================

    public void selectLinkToStage(String stage) {

        if (stage == null ||
                stage.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Link To Stage cannot be empty");
        }

        String value =
                stage.trim();

        System.out.println();
        System.out.println(
                "Selecting Link To Stage = " + value);

        // =====================================================
        // WAIT FOR NATIVE SELECT
        // =====================================================

        WebElement selectElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                linkToStage
                        )
                );

        scrollIntoView1(selectElement);

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        selectElement
                )
        );

        // =====================================================
        // SELECT BY VISIBLE TEXT
        // =====================================================

        Select select =
                new Select(selectElement);

        select.selectByVisibleText(value);

        // =====================================================
        // VERIFY SELECTION
        // =====================================================

        wait.until(driver -> {

            try {

                String selected =
                        new Select(
                                driver.findElement(
                                        linkToStage
                                )
                        )
                        .getFirstSelectedOption()
                        .getText()
                        .trim();

                return selected.equalsIgnoreCase(value);

            } catch (Exception e) {

                return false;
            }
        });

        System.out.println(
                "Link To Stage selected = " +
                value);
    }

    // =========================================================
    // VERIFY LEAD DISPLAYED AFTER SELECTION
    // =========================================================

    public void verifyLeadSelected(String expectedLead) {

        String expected =
                expectedLead.trim();

        WebElement leadButton =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                leadNameButton
                        )
                );

        String actual =
                leadButton.getText().trim();

        System.out.println(
                "Lead displayed on page = " +
                actual);

        if (!actual.contains(expected)) {

            throw new AssertionError(
                    "Lead selection failed. Expected: " +
                    expected +
                    " but actual value is: " +
                    actual
            );
        }

        System.out.println(
                "Lead selection verified successfully.");
    }

    // =========================================================
    // SCROLL INTO VIEW
    // =========================================================

    private void scrollIntoView1(
            WebElement element) {

        try {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView(" +
                    "{block:'center', inline:'nearest'});",
                    element
            );

        } catch (Exception ignored) {
        }
    }

	

	
}