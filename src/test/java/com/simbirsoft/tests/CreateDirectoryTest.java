package com.simbirsoft.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public class CreateDirectoryTest extends BaseTest {

    @Test(dataProvider = "validDirectories")
    public void createValidDirectory(String name) throws MalformedURLException, UnsupportedEncodingException {
        methods.createDirectory(name).then().
                statusCode(201).
                body("href", equalTo(baseURI + ":" + port + "/v1/disk/resources?path=disk%3A%2F" +
                        URLEncoder.encode(name, "UTF-8"))).
                assertThat().body(matchesJsonSchemaInClasspath("schema/createDirectory.json"));
        methods.deleteDirectory(name).then().
                statusCode(anyOf(equalTo(204), equalTo(202)));
    }

    @Test(dataProvider = "invalidDirectories")
    public void createInvalidDirectory(String name, int status, String error) throws MalformedURLException, UnsupportedEncodingException {
        methods.createDirectory(name).then().
                statusCode(status).
                body("error", equalTo(error)).
                assertThat().body(matchesJsonSchemaInClasspath("schema/createDirectoryError.json"));
    }

    @DataProvider(name = "validDirectories")
    public Object[][] validDirectories() {
        return new Object[][] {
                {"Hello, World!"},
                {"Привет, Мир!"}
        };
    }

    @DataProvider(name = "invalidDirectories")
    public Object[][] invalidDirectories() {
        return new Object[][] {
                {"test/test1", 409, "DiskPathDoesntExistsError"},
                {"Музыка", 409, "DiskPathPointsToExistentDirectoryError"}
        };
    }
}
