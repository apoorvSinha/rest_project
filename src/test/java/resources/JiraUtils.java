package resources;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;


public class JiraUtils {
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;
    PrintStream jiraLog;
    public static Properties prop;
    static FileInputStream fis;
    PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();

    public RequestSpecification setRequestSpecification(){
        authScheme.setUserName(getHiddenProperties("user"));
        authScheme.setPassword(getHiddenProperties("password"));
        requestSpecification = new RequestSpecBuilder().setBaseUri(getHiddenProperties("baseUri"))
                .setAuth(authScheme)
                .setContentType(ContentType.JSON)
                .build();
        return requestSpecification;
    }

    public ResponseSpecification setResponseSpecification() {
        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        return responseSpecification;
    }

    public static String getHiddenProperties(String key){
        prop = new Properties();
        try {
            fis = new FileInputStream("./src/test/java/resources/hidden.properties");
            prop.load(fis);
        }catch (IOException e){
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
