package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import resources.Utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class StepDefinition extends Utils {
    RequestSpecification requestSpecBuilder;
    ResponseSpecification responseSpecBuilder;
    Response response;
    JsonPath js;
    TestDataBuild payload = new TestDataBuild();

    @Given("add place API payload with {string} {string}  {string}")
    public void add_place_API_payload_with(String name, String lang, String address) {
        RequestSpecification requestSpecification = requestSpecification();
        requestSpecBuilder = given().spec(requestSpecification).body(payload.addPlacePayload(name, lang, address));
    }

    @When("user calls {string} with POST http request")
    public void user_calls_with_POST_http_request(String string) {
        responseSpecBuilder =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        response = requestSpecBuilder.when().post("/maps/api/place/add/json")
                .then().log().all().spec(responseSpecBuilder).extract().response();
    }

    @Then("the API call is success with status code {int}")
    public void the_API_call_is_success_with_status_code(int int1) {
        assertEquals(response.getStatusCode(), int1);
    }

    @Then("{string} should in response body is {string}")
    public void should_in_response_body_is(String key, String value) {
        String resp = response.asString();
        js = new JsonPath(resp);
        assertEquals(js.get(key).toString(), value);
    }

}
