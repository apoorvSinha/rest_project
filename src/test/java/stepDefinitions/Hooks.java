package stepDefinitions;
import io.cucumber.java.Before;     //different import than junit

public class Hooks {
    @Before("@DeletePlace")
    public void beforeScenario() {
        // run only when it is null
        StepDefinition m = new StepDefinition();
        if (StepDefinition.place_id == null) {
            m.add_place_API_payload_with("roopa", "English", "Asia");
            m.user_calls_with_http_request("AddPlaceAPI", "POST");
            m.verify_place_id_created_maps_to_using("roopa", "getPlaceAPI");
        }
    }
}
