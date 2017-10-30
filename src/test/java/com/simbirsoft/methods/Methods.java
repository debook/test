package com.simbirsoft.methods;

import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by DeBooK on 19.10.2017.
 */
public class Methods {

    public Response deleteDirectory(String dirName) {
        return given().
                    param("path", dirName).
                    param("permanently", true).
               when().
                    delete("/v1/disk/resources");
    }

    public Response createDirectory(String dirName) {
        return given().
                    param("path", dirName).
               when().
                    put("/v1/disk/resources");
    }
}
