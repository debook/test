package com.simbirsoft.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class GetDirectoryInfoTest extends BaseTest {

    @BeforeMethod
    public void setUp() {
        methods.createDirectory("test_directory").then().statusCode(anyOf(equalTo(201), equalTo(409)));
    }

    @Test
    public void getInfoDirectory() throws MalformedURLException, UnsupportedEncodingException {
        given().
                param("path", "test_directory").
        when().
                get("/v1/disk/resources").
        then().
                statusCode(200).
                body("path", equalTo("disk:/test_directory")).
                body("_embedded.path", equalTo("disk:/test_directory")).
                body("name", equalTo("test_directory")).
                body("type", equalTo("dir")).
                assertThat().body(matchesJsonSchemaInClasspath("schema/getDirectoryInfo.json"));
    }

    @AfterMethod
    public void tearDown() {
        methods.deleteDirectory("test_directory").
                then().statusCode(anyOf(equalTo(204), equalTo(202)));
    }
}
