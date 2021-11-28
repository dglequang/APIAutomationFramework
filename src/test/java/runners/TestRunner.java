package runners;

import org.junit.BeforeClass;
import utility.Common;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"stepdefinitions"},
        plugin = ("json:target/cucumber.json"),
        monochrome = true,
        strict = true
        )

public class TestRunner {
        private static String executeTime = null;
        private static String outputDirectory = "target/reports";

       
        @AfterClass
        public static void teardown() throws Exception {
                List<String> jsonFiles = new ArrayList<>();
                jsonFiles.add("target/cucumber.json");

                executeTime = Common.getTime();
                String fileReportName = outputDirectory + "/" + executeTime;
                File outputReport = new File(fileReportName);

                Configuration config = new Configuration(outputReport, "API-Automation-Framework");
                config.addClassifications("Owner", "Quang Dang");

                ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
                reportBuilder.generateReports();
        }
}


