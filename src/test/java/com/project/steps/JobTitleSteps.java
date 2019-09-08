package com.project.steps;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.project.pages.JobTitlePage;
import com.project.utils.CommonMethods;
import com.project.utils.Constants;
import com.project.utils.DbUtils;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class JobTitleSteps extends CommonMethods {

	JobTitlePage jtitle;

	List<Map<String, String>> uiResults;
	List<Map<String, String>> dbResults;

	public JobTitleSteps() {
		jtitle = new JobTitlePage();
	}

	@When("I click on job")
	public void i_click_on_job() {
		click(jtitle.job);
	}

	@When("I click on jobtitle")
	public void i_click_on_jobtitle() {
		click(jtitle.jobTitle);
	}

	@When("I click on addJobTitle")
	public void i_click_on_addJobTitle() {
		
		click(jtitle.addJobTitle);
		
	}

	@When("I enter job {string}, {string} and job {string}")
	public void i_enter_job_and_job(String jobTitle, String jobDescription, String specification) {
		
		sendText(jtitle.jobTitleField, jobTitle);
		sendText(jtitle.jobDescription, jobDescription);
		sendText(jtitle.jobSpecification, Constants.FILEPATH + specification);
	}

	@When("I get all job titles from UI")
	public void i_get_all_job_titles_from_UI() {

		uiResults = new ArrayList<>();

		for (WebElement row : jtitle.jobTitleTableRows) {
			
			Map<String, String> uiDataMap = new LinkedHashMap<>();

			String rowText = row.getText().replace("ohrm_edit", "").trim();

			uiDataMap.put("JOB_TITLE", rowText);

			uiResults.add(uiDataMap);
		}
	}

	@When("I execute {string} from Database")
	public void i_execute_from_Database(String sqlQuery) {
		dbResults = DbUtils.getResultSetData(sqlQuery);
	}

	@Then("Job titles are matched")
	public void job_titles_are_matched() {

		Assert.assertTrue(uiResults.equals(dbResults));
	}
}