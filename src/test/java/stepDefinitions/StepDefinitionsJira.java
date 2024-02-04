package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.JiraUtils;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class StepDefinitionsJira extends JiraUtils {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    RequestSpecification requestBuilder;
    Response response;
    @Given("user is logged in and the status code is {string}")
    public void user_is_logged_in_and_the_status_code_is(String string) {
        requestSpecification = setRequestSpecification();
        requestBuilder = given().spec(requestSpecification);
    }

    @Then("user is able to see the project details")
    public void user_is_able_to_see_the_project_details() {
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        response = requestBuilder.when().get("rest/api/3/project/KAN")
                .then().log().all().spec(responseSpecification).extract().response();
        assertEquals(response.getStatusCode(), 200);
    }
}
