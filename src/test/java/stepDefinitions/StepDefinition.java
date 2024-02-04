package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static resources.APIResources.*;

public class StepDefinition extends Utils {
    RequestSpecification requestSpecBuilder;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecBuilder;
    Response response;
    TestDataBuild payload = new TestDataBuild();
    static String place_id;     //static ensures that this doesn't reset to null when next test is started

    @Given("Delete place payload")
    public void delete_place_payload() {
        requestSpecification = requestSpecification();
        requestSpecBuilder = given().spec(requestSpecification).body(payload.deletePlacePayload(place_id));
    }

    @Given("add place API payload with {string} {string}  {string}")
    public void add_place_API_payload_with(String name, String lang, String address) {
        requestSpecification = requestSpecification();
        requestSpecBuilder = given().spec(requestSpecification).body(payload.addPlacePayload(name, lang, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String method, String httpMethod) {
        responseSpecBuilder = responseSpecification();
        if (httpMethod.equalsIgnoreCase("POST")) {
            response = requestSpecBuilder.when().post(addPlaceAPI.getValue());
        } else if (httpMethod.equalsIgnoreCase("GET")) {
            response = requestSpecBuilder.when().get(getPlaceAPI.getValue());
        } else if (httpMethod.equalsIgnoreCase("DELETE")) {
            response = requestSpecBuilder.when().delete(deletePlaceAPI.getValue());
        }
    }

    @Then("the API call is success with status code {int}")
    public void the_API_call_is_success_with_status_code(int int1) {
        this.response = response.then().log().all().spec(responseSpecBuilder).extract().response();
        assertEquals(this.response.getStatusCode(), int1);
    }

    @Then("{string} should in response body is {string}")
    public void should_in_response_body_is(String key, String value) {
        assertEquals(getJsonPath(response, key), value);
    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) {
        place_id = getJsonPath(response, "place_id");
        requestSpecBuilder = given().spec(requestSpecification)
                .queryParam("place_id", place_id);
        user_calls_with_http_request(resource, "GET");
        this.response = response.then().log().all().spec(responseSpecBuilder).extract().response();
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);
    }

}
