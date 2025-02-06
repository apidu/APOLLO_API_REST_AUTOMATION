package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;



/**
 * This utility class manages the creation and lifecycle of Extent Reports.
 * Extent Reports are used for generating detailed and interactive test execution reports.
 */
public class ExtentReportManager {

    ///Singleton instance of ExtentReports to ensure a single report file across the test suite
        private static ExtentReports extent;

    /// Holds the current ExtentTest object to log test steps and results
        private static ExtentTest test;


    /**
     * Initializes ExtentReports with a Spark reporter to generate HTML reports.
     * The report will be saved as "ExtentReports.html" in the project directory.
     */
        public static void initReports() {
            if (extent == null) {
                // Ensure the reports directory exists (create if it doesn't)
                File reportDir = new File("/home/apollo_user/eclipse-workspace/APOLLO_API_REST_AUTOMATION/reports");
                if (!reportDir.exists()) {
                    boolean created = reportDir.mkdirs();  // Create directories if they don't exist
                    if (created) {
                        System.out.println("Reports directory created at: " + reportDir.getAbsolutePath());
                    } else {
                        System.out.println("Reports directory already exists.");
                    }
                }

                // Generate a timestamp to append to the report file name for uniqueness
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

                // Set the path where the report will be stored, including the timestamp
                String reportPath = "/home/apollo_user/eclipse-workspace/APOLLO_API_REST_AUTOMATION/reports/ExtentReport_" + timestamp + ".html";

                // Log the report path
                System.out.println("Report will be saved to: " + reportPath);

                // Initialize ExtentSparkReporter with the desired path for report output
                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

                extent = new ExtentReports();

                /// Attach Spark reporter to ExtentReports for generating reports
                extent.attachReporter(spark);

                // Adding report metadata
                extent.setSystemInfo("Report Generated On", timestamp);
            }
    }
    /**
     * Flushes(errase old report) the report data to the output file.
     * This method ensures that all report information is written to the file.
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }


    /**
     * Creates a new test in the Extent Report with the given name.
     *
     *  @[testName] The name of the test to create in the report.
     * @return An ExtentTest object to log details for the created test.
     */
    public static ExtentTest createTest(String testName) {
        // Make sure reports are initialized before creating tests
        if (extent == null) {
            initReports();
        }
        test = extent.createTest(testName);
        return test;
    }

    /*
     * Returns the current ExtentTest object.
     * This allows other classes to log test steps and results.
     */

    public static ExtentTest getTest() {
        return test;
    }
    }

