package stepdefinitions;

import apitest.BaseFunctions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps extends BaseFunctions {
    public Steps() throws Exception{
        String env = System.getProperty("env");
        boolean isEmpty = env == null || env.trim().length() == 0;
        if(isEmpty){
            env = "dev";
        }
        super.setEnvironment(env);
    }

    @Given("^A city name: \"(.*?)\"$")
    public void gotCityName(String gotCityName) throws Exception {
        super.setCityName(gotCityName);
    }

    @When("^Use GET method with city name$")
    public void Use_GET_method_with_city_name() throws Exception {
        super.useGetMethodWithCityName();
    }

    @Then("^Response code is \"(\\d+)\"$")
    public void response_Code_Is(int statusCode) throws Exception {
        super.responseCodeIs(statusCode);
    }

    @And("^Response body is correct$")
    public void response_Body_Is_Correct() throws Exception {
        super.resBodyIsCorrectAsSaveObject();
    }

    @Then("^Response body is correct as table$")
    public void response_Body_Is_Correct_As_Table(DataTable dt) throws Exception {
        super.responseBodyIsCorrectAsTable(dt);
    }

}

