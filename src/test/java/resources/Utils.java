package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils {
    public static RequestSpecification requestSpecification; //static will make it initialised for all 5 cases
    public static ResponseSpecification responseSpecification;
    PrintStream log;
    static Properties prop;
    JsonPath js;

    public RequestSpecification requestSpecification() {
        if (requestSpecification == null){
            try {
                log = new PrintStream(Files.newOutputStream(Paths.get("./src/main/java/LOG/logs.txt")));
            } catch (IOException E) {
                E.printStackTrace();
            }
            requestSpecification = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
        }
        return requestSpecification;
    }

    public ResponseSpecification responseSpecification() {
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return responseSpecification;
    }

    public static String getGlobalValue(String key) {
        try{
            prop = new Properties();
            FileInputStream fis = new FileInputStream("./src/test/java/resources/global.properties");
            prop.load(fis);
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        js = new JsonPath(resp);
        return js.get(key).toString();
    }

}
