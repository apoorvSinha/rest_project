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
import static resources.APIResources.addPlaceAPI;
import static resources.APIResources.getPlaceAPI;

public class StepDefinition extends Utils {
    RequestSpecification requestSpecBuilder;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecBuilder;
    Response response;
    TestDataBuild payload = new TestDataBuild();

    @Given("add place API payload with {string} {string}  {string}")
    public void add_place_API_payload_with(String name, String lang, String address) {
        requestSpecification = requestSpecification();
        requestSpecBuilder = given().spec(requestSpecification).body(payload.addPlacePayload(name, lang, address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_http_request(String method, String httpMethod) {
        responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (httpMethod.equalsIgnoreCase("POST")) {
            response = requestSpecBuilder.when().post(addPlaceAPI.getValue());
        } else if (httpMethod.equalsIgnoreCase("GET")) {
            response = requestSpecBuilder.when().get(getPlaceAPI.getValue());
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
        requestSpecBuilder = given().spec(requestSpecification)
                .queryParam("place_id", getJsonPath(response, "place_id"));
        user_calls_with_http_request(resource, "GET");
        this.response = response.then().log().all().spec(responseSpecBuilder).extract().response();
        String actualName = getJsonPath(response, "name");
        assertEquals(actualName, expectedName);
    }

}
