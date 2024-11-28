package report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

        private static ExtentReports extent;
        private static ExtentTest test;

        public static void initReports() {
            if (extent == null) {
                ExtentSparkReporter spark = new ExtentSparkReporter("ExtentReports.html");
                extent = new ExtentReports();
                extent.attachReporter(spark);
            }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }

    public static ExtentTest createTest(String testName) {
        // Make sure reports are initialized before creating tests
        if (extent == null) {
            initReports();
        }
        test = extent.createTest(testName);
        return test;
    }

    public static ExtentTest getTest() {
        return test;
    }
    }

