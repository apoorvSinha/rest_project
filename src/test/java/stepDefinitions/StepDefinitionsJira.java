package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.JiraUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinitionsJira extends JiraUtils {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    RequestSpecification requestBuilder;
    Response response;
    static String issueId = String.valueOf(10004);

    @Given("user is logged in")
    public void user_is_logged_in() {
        requestSpecification = setRequestSpecification();
        requestBuilder = given().spec(requestSpecification).log().all();
    }

    @When("user is able to see the project details and the status code is {string}")
    public void user_is_able_to_see_the_project_details_and_the_status_code_is(String string) {
        responseSpecification = setResponseSpecification("GET");
        response = requestBuilder.when().get("rest/api/3/project/KAN")
                .then().log().all().spec(responseSpecification).extract().response();
        assertEquals(response.getStatusCode(), Integer.parseInt(string));
    }

    @Then("user is able to create an issue")
    public void user_is_able_to_create_an_issue() {
        String fs = "";
        try {
            fs = new String(Files.readAllBytes(Paths.get("./src/test/java/resources/jsons/createIssue.json")));
        }catch (IOException R){
            R.printStackTrace();
        }
        requestBuilder = given().spec(requestSpecification).log().all()
                .queryParam("updateHistory", false)
                .body(fs);
        responseSpecification = setResponseSpecification("POST");
        response = requestBuilder.when().post("/rest/api/3/issue")
                .then().log().all().spec(responseSpecification).extract().response();
        issueId = getJsonParsed(response.asString(), "id");
    }

    @When("user is able to get the issue")
    public void user_is_able_to_get_the_issue() {
        requestBuilder =  given().spec(requestSpecification).log().all();
        responseSpecification = setResponseSpecification("GET");
        response = requestBuilder.when().get("/rest/api/3/issue/"+ issueId)
                .then().log().all().spec(responseSpecification).extract().response();
    }

    @When("user is able to udpate the issue")
    public void user_is_able_to_update_the_issue() {
        requestBuilder = given().spec(requestSpecification).log().all();
        responseSpecification = setResponseSpecification("PUT");
        response = requestBuilder.when().put("/rest/api/3/issue/"+issueId)
                .then().log().all().spec(responseSpecification).extract().response();
    }

}
