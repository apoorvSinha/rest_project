package resources;

import io.cucumber.java.bs.A;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.http.HTTPBuilder;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;


public class JiraUtils {
    public static RequestSpecification requestSpecification;
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
