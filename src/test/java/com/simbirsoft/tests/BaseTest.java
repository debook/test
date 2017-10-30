package com.simbirsoft.tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.simbirsoft.methods.Methods;
import org.testng.annotations.BeforeClass;

public class BaseTest{

    protected String baseURI = "https://cloud-api.yandex.net";
    protected int port = 443;
    protected Methods methods;

    @BeforeClass
    public void init() {
        RestAssured.baseURI = baseURI;
        RestAssured.port = port;
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addHeader("Content-Type", "application/json; charset=utf-8");
        builder.addHeader("Authorization", "OAuth AQAAAAAcaJyiAADLW4i2uij8j0gBo6NrVkxY6_4");
        RestAssured.requestSpecification = builder.build();
        methods = new Methods();
    }
}
