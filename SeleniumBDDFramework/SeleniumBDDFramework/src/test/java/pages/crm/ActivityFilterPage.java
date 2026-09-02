package pages.crm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ActivityFilterPage extends ActivityCommonPage {

    // =========================================================
    // LOCATORS
    // =========================================================

    private final By filtersButton = By.xpath(
            "//button[normalize-space()='Filters' or normalize-space()='Filter']"
    );

    private final By activityTypeDropdown = By.xpath(
            "//*[normalize-space()='Activity Type']"
            + "/following::*[self::select or self::button or @role='combobox' "
            + "or @aria-haspopup='listbox' or @aria-haspopup='menu' "
            + "or normalize-space()='All Types' or normalize-space()='Call' "
            + "or normalize-space()='Email' or normalize-space()='Physical Visit' "
            + "or normalize-space()='Virtual Visit'][1]"
    );

    private final By statusDropdown = By.xpath(
            "//*[normalize-space()='Status']"
            + "/following::*[self::select or self::button or @role='combobox' "
            + "or @aria-haspopup='listbox' or @aria-haspopup='menu' "
            + "or normalize-space()='All Statuses' or normalize-space()='Scheduled' "
            + "or normalize-space()='Ongoing' or normalize-space()='Overdue' "
            + "or normalize-space()='Completed' or normalize-space()='Skipped'][1]"
    );

    private final By filtersPanel = By.xpath(
            "//*[normalize-space()='Filters & Sort']"
    );

    private final By sortTab = By.xpath(
            "//*[normalize-space()='Sort' and "
            + "(self::button or self::div or self::span or @role='tab')]"
    );

    private final By applyFiltersButton = By.xpath(
            "//button[normalize-space()='Apply']"
    );

    private final By clearAllButton = By.xpath(
            "//button[normalize-space()='Clear All']"
    );

    private final By activityTypeColumn = By.xpath(
            "//table//tbody//tr//td[contains(@class,'activity-type') "
            + "or contains(@class,'activityType') "
            + "or contains(@class,'type')]"
    );

    private final By activityRows = By.xpath(
            "//table//tbody//tr"
    );

    private final By paginationSummary = By.xpath(
            "//*[contains(normalize-space(),' of ') and contains(normalize-space(),' items')]"
    );

    private final By itemsPerPageDropdown = By.xpath(
            "//*[normalize-space()='Items per page']"
            + "/following::*[self::select or self::button or @role='combobox' "
            + "or @aria-haspopup='listbox' or @aria-haspopup='menu'][1]"
    );

    private final By dateColumnCells = By.xpath(
            "//table//tbody//tr/td["
            + "count(//table//thead//th[normalize-space()='DATE' "
            + "or normalize-space()='Date']/preceding-sibling::th) + 1]"
    );

    private final DateTimeFormatter activityDateFormatter =
            DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH);

    private final By noResultsMessage = By.xpath(
            "//*[contains(normalize-space(),'No activities found') "
            + "or contains(normalize-space(),'No results')]"
    );

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ActivityFilterPage(WebDriver driver) {
        super(driver);
        System.out.println("ActivityFilterPage initialized.");
    }

    // =========================================================
    // CLICK FILTERS BUTTON
    // =========================================================

    public void clickFilters() {

        System.out.println("==========================================");
        System.out.println("Clicking Filters button");
        System.out.println("==========================================");

        if (!driver.findElements(filtersPanel).isEmpty()) {
            System.out.println("Filters panel is already open.");
            return;
        }

        WebElement filterBtn = wait.until(
                ExpectedConditions.elementToBeClickable(filtersButton)
        );

        scrollIntoView(filterBtn);

        try {
            filterBtn.click();
        } catch (Exception e) {
            System.out.println(
                    "Normal click failed on Filters. Using JavaScript click."
            );
            jsClick(filterBtn);
        }

        System.out.println("Filters button clicked.");
    }

    // =========================================================
    // SELECT ACTIVITY TYPE FILTER
    // =========================================================

    public void selectActivityTypeFilter(String activityType) {

        System.out.println("==========================================");
        System.out.println(
                "Selecting Activity Type filter: " + activityType
        );
        System.out.println("==========================================");

        selectDropdownValue(
                activityTypeDropdown,
                activityType,
                "Activity Type dropdown"
        );

        System.out.println(
                "Activity Type '" + activityType + "' selected."
        );
    }

    // =========================================================
    // APPLY ACTIVITY TYPE FILTER
    // =========================================================

    public void applyActivityTypeFilter(String activityType) {

        System.out.println(
                "Applying Activity Type filter: " + activityType
        );

        String previousFirstRowText = getFirstRowText();

        clickApplyFilter();
        waitForFilteredResults(activityType, previousFirstRowText);

        System.out.println(
                "Activity Type filter '" + activityType + "' applied."
        );
    }

        // =========================================================
        // SELECT STATUS FILTER
        // =========================================================

        public void selectStatusFilter(String status) {

                System.out.println("==========================================");
                System.out.println("Selecting Status filter: " + status);
                System.out.println("==========================================");

                selectDropdownValue(statusDropdown, status, "Status dropdown");

                System.out.println("Status '" + status + "' selected.");
        }

        // =========================================================
        // APPLY STATUS FILTER
        // =========================================================

        public void applyStatusFilter(String status) {

                System.out.println("Applying Status filter: " + status);

                String previousFirstRowText = getFirstRowText();

                clickApplyFilter();
                waitForFilteredResults(status, previousFirstRowText);

                System.out.println("Status filter '" + status + "' applied.");
        }

        private void selectDropdownValue(
                        By dropdownLocator, String value, String dropdownName) {

                WebElement dropdown = wait.until(
                                ExpectedConditions.elementToBeClickable(dropdownLocator)
                );

                scrollIntoView(dropdown);

                if ("select".equalsIgnoreCase(dropdown.getTagName())) {
                        if (selectNativeOption(dropdown, value, dropdownName)) {
                                return;
                        }

                        System.out.println(
                                        "Native select did not contain '" + value
                                        + "'. Trying visible dropdown options."
                        );
                }

                clickElement(dropdown, dropdownName);
                System.out.println(dropdownName + " opened.");

                WebElement option = wait.until(webDriver -> findVisibleOption(value));

                scrollIntoView(option);
                clickElement(option, "option '" + value + "'");
        }

        private boolean selectNativeOption(
                        WebElement dropdown, String value, String dropdownName) {

                Select select = new Select(dropdown);

                try {
                        select.selectByVisibleText(value.trim());
                        System.out.println(dropdownName + " selected by visible text.");
                        return true;
                } catch (NoSuchElementException e) {
                        for (WebElement option : select.getOptions()) {
                                String optionText = normalizeSpaces(option.getText());
                                if (optionText.equalsIgnoreCase(normalizeSpaces(value))) {
                                        option.click();
                                        System.out.println(
                                                        dropdownName
                                                        + " selected by case-insensitive text."
                                        );
                                        return true;
                                }
                        }
                        return false;
                }
        }

        private String normalizeSpaces(String value) {

                return value.replace('\u00A0', ' ')
                                .replaceAll("\\s+", " ")
                                .trim();
        }

        private WebElement findVisibleOption(String value) {

                By dropdownOption = By.xpath(
                                "//*[self::li or self::div or self::option or self::button "
                                + "or self::span or @role='option' or @role='menuitem']"
                );

                List<WebElement> options = driver.findElements(dropdownOption);
                String expectedValue = normalizeSpaces(value);

                for (WebElement option : options) {
                        try {
                                String actualValue = normalizeSpaces(option.getText());
                                if (option.isDisplayed()
                                                && option.isEnabled()
                                                && actualValue.equalsIgnoreCase(expectedValue)) {
                                        return option;
                                }
                        } catch (StaleElementReferenceException e) {
                                System.out.println(
                                                "Skipping stale dropdown option while looking for: "
                                                + value
                                );
                        }
                }

                return null;
        }

    private void clickApplyFilter() {

        WebElement applyButton = wait.until(
                ExpectedConditions.elementToBeClickable(applyFiltersButton)
        );

        scrollIntoView(applyButton);
        clickElement(applyButton, "Apply button");
        System.out.println("Apply button clicked.");
    }

    // =========================================================
    // CLEAR ALL FILTERS
    // =========================================================

    public void clickClearAll() {

        WebElement clearButton = wait.until(
                ExpectedConditions.elementToBeClickable(clearAllButton)
        );

        scrollIntoView(clearButton);
        clickElement(clearButton, "Clear All button");
        System.out.println("Clear All button clicked.");
    }

        // =========================================================
        // CHANGE ITEMS PER PAGE
        // =========================================================

        public void changeItemsPerPage(int currentSize, int newSize) {

                System.out.println(
                                "Changing items per page from " + currentSize
                                + " to " + newSize
                );

                selectDropdownValue(
                                itemsPerPageDropdown,
                                String.valueOf(newSize),
                                "Items per page dropdown"
                );

                waitForRecordCountUpTo(newSize);

                System.out.println("Items per page changed to " + newSize + ".");
        }

        // =========================================================
        // VERIFY ACTIVITY RECORD COUNT
        // =========================================================

        public void verifyActivityRecordCount(int expectedCount) {

                int actualCount = driver.findElements(activityRows).size();
                int totalCount = getPaginationTotalCount();
                int expectedVisibleCount = Math.min(expectedCount, totalCount);

                System.out.println("Expected visible activity records: " + expectedVisibleCount);
                System.out.println("Actual visible activity records: " + actualCount);
                System.out.println("Total filtered activity records: " + totalCount);

                if (actualCount != expectedVisibleCount) {
                        throw new AssertionError(
                                        "Expected " + expectedVisibleCount
                                        + " activity records but found " + actualCount
                        );
                }
        }

        private void waitForRecordCountUpTo(int expectedCount) {

                System.out.println(
                                "Waiting for visible activity record count up to: "
                                + expectedCount
                );

                wait.until(webDriver -> {
                        int totalCount = getPaginationTotalCount();
                        int expectedVisibleCount = Math.min(expectedCount, totalCount);
                        return webDriver.findElements(activityRows).size()
                                        == expectedVisibleCount;
                });
        }

        private int getPaginationTotalCount() {

                List<WebElement> summaries = driver.findElements(paginationSummary);

                for (WebElement summary : summaries) {
                        String summaryText = summary.getText().trim();
                        String[] parts = summaryText.split("\\s+");

                        for (int index = 0; index < parts.length - 1; index++) {
                                if ("of".equalsIgnoreCase(parts[index])) {
                                        try {
                                                return Integer.parseInt(
                                                                parts[index + 1].replaceAll("[^0-9]", "")
                                                );
                                        } catch (NumberFormatException e) {
                                                break;
                                        }
                                }
                        }
                }

                return driver.findElements(activityRows).size();
        }

        // =========================================================
        // SORT ACTIVITIES
        // =========================================================

        public void clickSortTab() {

                WebElement sortTabElement = wait.until(
                                ExpectedConditions.elementToBeClickable(sortTab)
                );

                scrollIntoView(sortTabElement);
                clickElement(sortTabElement, "Sort tab");
                System.out.println("Sort tab clicked.");
        }

        public void selectSortOrder(String sortBy, String sortOrder) {

                By sortOrderOption = By.xpath(
                                "//*[normalize-space()=" + xpathLiteral(sortBy.trim()) + "]"
                                + "/following::*[normalize-space()="
                                + xpathLiteral(sortOrder.trim()) + "][1]"
                );

                WebElement option = wait.until(
                                ExpectedConditions.elementToBeClickable(sortOrderOption)
                );

                scrollIntoView(option);
                clickElement(option, sortBy + " " + sortOrder + " sort option");
                System.out.println(
                                sortBy + " " + sortOrder + " sort option clicked."
                );
        }

        public void applySort() {

                String previousFirstRowText = getFirstRowText();

                clickApplyFilter();
                waitForTableToRefresh(previousFirstRowText);

                System.out.println("Sort apply completed.");
        }

        public void verifyActivitiesSorted(String sortBy, String sortOrder) {

                if (!"Date".equalsIgnoreCase(sortBy.trim())) {
                        throw new IllegalArgumentException(
                                        "Sorting validation is implemented only for Date. Requested: "
                                        + sortBy
                        );
                }

                List<LocalDate> dates = getVisibleActivityDates();

                if (dates.size() < 2) {
                        System.out.println(
                                        "Only " + dates.size()
                                        + " date value found. Nothing more to compare."
                        );
                        return;
                }

                boolean descending = "Descending".equalsIgnoreCase(sortOrder.trim());

                for (int index = 1; index < dates.size(); index++) {
                        LocalDate previousDate = dates.get(index - 1);
                        LocalDate currentDate = dates.get(index);
                        boolean outOfOrder = descending
                                        ? previousDate.isBefore(currentDate)
                                        : previousDate.isAfter(currentDate);

                        if (outOfOrder) {
                                throw new AssertionError(
                                                "Activities are not sorted by Date in " + sortOrder
                                                + " order. Previous date: " + previousDate
                                                + ", current date: " + currentDate
                                );
                        }
                }

                System.out.println(
                                "Verified " + dates.size()
                                + " activity dates are sorted in " + sortOrder + " order."
                );
        }

        private void waitForTableToRefresh(String previousFirstRowText) {

                wait.until(webDriver -> {
                        List<WebElement> rows = webDriver.findElements(activityRows);
                        if (rows.isEmpty()) {
                                return false;
                        }

                        String firstRowText = rows.get(0).getText().trim();
                        return previousFirstRowText == null
                                        || !firstRowText.equals(previousFirstRowText)
                                        || getVisibleActivityDates().size() > 1;
                });
        }

        private List<LocalDate> getVisibleActivityDates() {

                List<WebElement> dateCells = driver.findElements(dateColumnCells);
                List<LocalDate> dates = new ArrayList<>();

                for (WebElement dateCell : dateCells) {
                        String dateText = dateCell.getText().trim();
                        if (dateText.isEmpty()) {
                                continue;
                        }

                        try {
                                dates.add(LocalDate.parse(dateText, activityDateFormatter));
                        } catch (DateTimeParseException e) {
                                throw new AssertionError(
                                                "Unable to parse activity date: " + dateText,
                                                e
                                );
                        }
                }

                return dates;
        }

    private void waitForFilteredResults(
            String expectedType, String previousFirstRowText) {

        System.out.println("Waiting for filtered results to update.");

        wait.until(webDriver -> {
            if (!webDriver.findElements(noResultsMessage).isEmpty()) {
                return true;
            }

            List<WebElement> rows = webDriver.findElements(activityRows);
            if (rows.isEmpty()) {
                return false;
            }

            String firstRowText = rows.get(0).getText().trim();
            boolean firstRowChanged = previousFirstRowText == null
                    || !firstRowText.equals(previousFirstRowText);
            boolean anyRowHasExpectedType = rows.stream()
                    .anyMatch(row -> row.getText().toLowerCase()
                            .contains(expectedType.toLowerCase()));

            return firstRowChanged || anyRowHasExpectedType;
        });
    }

    private String getFirstRowText() {

        List<WebElement> rows = driver.findElements(activityRows);
        if (rows.isEmpty()) {
            return null;
        }

        return rows.get(0).getText().trim();
        }

    private void clickElement(WebElement element, String elementName) {

        try {
            element.click();
        } catch (Exception e) {
            System.out.println(
                    "Normal click failed on " + elementName
                    + ". Using JavaScript click."
            );
            jsClick(element);
        }
    }

    private String xpathLiteral(String value) {

                if (!value.contains("'")) {
                        return "'" + value + "'";
                }

                if (!value.contains("\"")) {
                        return "\"" + value + "\"";
                }

                String[] parts = value.split("'");
                StringBuilder literal = new StringBuilder("concat(");

                for (int index = 0; index < parts.length; index++) {
                        if (index > 0) {
                                literal.append(", \"'\", ");
                        }
                        literal.append("'").append(parts[index]).append("'");
                }

                literal.append(")");
                return literal.toString();
    }

    // =========================================================
    // VERIFY FILTERED RESULTS
    // =========================================================

    public void verifyFilteredResults(String expectedType) {

        System.out.println("==========================================");
        System.out.println(
                "Verifying filtered results for type: " + expectedType
        );
        System.out.println("==========================================");

        // Wait for the table to refresh
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table//tbody")
        ));

        // Check if there are any rows
        List<WebElement> rows = driver.findElements(activityRows);

        if (rows.isEmpty()) {
            // If no rows, check for a "no results" message
            List<WebElement> noResults = driver.findElements(noResultsMessage);
            if (!noResults.isEmpty()) {
                System.out.println(
                        "No activities found for type: " + expectedType
                        + ". This may be valid if no such activities exist."
                );
                return;
            }
            throw new AssertionError(
                    "No activity rows found and no 'no results' message displayed."
            );
        }

        System.out.println(
                "Total rows displayed after filter: " + rows.size()
        );

        // Verify each displayed row matches the expected activity type
        List<WebElement> typeCells = driver.findElements(activityTypeColumn);

        if (typeCells.isEmpty()) {
            System.out.println(
                    "Activity type column cells not found by class. "
                    + "Attempting alternate verification..."
            );
            verifyFilteredResultsByText(expectedType, rows);
            return;
        }

        for (WebElement cell : typeCells) {
            String actualType = cell.getText().trim();
            System.out.println(
                    "Row activity type: " + actualType
            );

            if (!actualType.equalsIgnoreCase(expectedType)) {
                throw new AssertionError(
                        "Expected activity type '" + expectedType
                        + "' but found '" + actualType + "'"
                );
            }
        }

        System.out.println(
                "All " + typeCells.size()
                + " displayed activities match type: " + expectedType
        );
    }

    // =========================================================
    // VERIFY STATUS FILTERED RESULTS
    // =========================================================

    public void verifyFilteredResultsByStatus(String expectedStatus) {

        System.out.println("==========================================");
        System.out.println(
                "Verifying filtered results for status: " + expectedStatus
        );
        System.out.println("==========================================");

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table//tbody")
        ));

        List<WebElement> rows = driver.findElements(activityRows);

        if (rows.isEmpty()) {
            List<WebElement> noResults = driver.findElements(noResultsMessage);
            if (!noResults.isEmpty()) {
                System.out.println(
                        "No activities found for status: " + expectedStatus
                        + ". This may be valid if no such activities exist."
                );
                return;
            }
            throw new AssertionError(
                    "No activity rows found and no 'no results' message displayed."
            );
        }

        System.out.println(
                "Total rows displayed after filter: " + rows.size()
        );

        verifyFilteredResultsByText(expectedStatus, rows);
    }

    // =========================================================
    // ALTERNATE VERIFICATION BY ROW TEXT
    // =========================================================

    private void verifyFilteredResultsByText(
            String expectedType, List<WebElement> rows) {

        System.out.println(
                "Using text-based verification for filtered results."
        );

        for (WebElement row : rows) {
            String rowText = row.getText().trim();
            if (!rowText.toLowerCase().contains(
                    expectedType.toLowerCase())) {
                throw new AssertionError(
                        "Row does not contain expected type '"
                        + expectedType + "'. Row text: " + rowText
                );
            }
        }

        System.out.println(
                "All " + rows.size()
                + " rows contain filter value: " + expectedType
        );
    }
}
