package com.go.rest.runner;

import com.go.rest.utils.Config;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;


@CucumberOptions(
		plugin = {"pretty","html:target/cucumber-reports"},
		tags="@SmokeTest",
		features = "src/test/resources/features",
		glue={"com.go.rest.stepdef", "com.go.rest.hooks"},
		dryRun = false
)
public class TestNGRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider (parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@BeforeSuite()
	public void setup(){

		Config.init();
		String logfolderpath = System.getProperty("user.dir");
		File f1 = new File(logfolderpath+"/target/logs");
		if(!f1.exists()){
			f1.mkdir();
		}

		File f2 = new File(logfolderpath+"/target/cucumberReport");
		if(!f2.exists()){
			f2.mkdir();
		}
	}
	@AfterSuite()
	public void tearDown(){

	}

}
