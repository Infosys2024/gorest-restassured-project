package com.gorest.testsuite;

import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class PostsAssertionTest extends TestBase {
    static ValidatableResponse response;

    @BeforeClass
    public static void start() {
        response = given()
                .queryParam("page", 1)
                .queryParam("per_page", 25)
                .when()
                .get("/posts")
                .then().statusCode(200);
    }

    //1. Verify the if the total record is 20
    @Test
    public void test001() {
        response.body("size()", equalTo(25));
    }

    //2. Verify the if the title of id = 93997 is equal to "Curia crastinus caput accipio dignissimos velit admiratio.”
    @Test
    public void test002() {
        response.body("find{it.id ==93827 }.title", equalTo("Curia crastinus caput accipio dignissimos velit admiratio."));
    }

    //3. Check the single user_id in the Array list (5914161)
    @Test
    public void test003() {
        response.body("[4].user_id", equalTo(5914161));
    }

    //4. Check the multiple ids in the ArrayList (93944, 93943, 93942)
    @Test
    public void test004() {
        response.body("id", hasItems(93944, 93943, 93942));
    }

    //5. Verify the body of userid = 5914254 is equal “Depulso auris vereor. Acceptus suffragium repudiandae.
    // Cotidie cubicularis deprecator. Virtus validus aliquid. Adduco somnus quibusdam. Despecto nihil vinum.
    // Claudeo nam ullus. Sursum tutamen rerum. Cenaculum tabula adultus. Charisma thema super.
    // Vobis cavus clibanus. Quo quod avaritia. Condico apparatus nulla. Textilis depopulo acidus.”

    @Test
    public void test005() {
        response.body("find{it.user_id == 5914254}.body", equalTo("Depulso auris vereor. " +
                "Acceptus suffragium repudiandae. Cotidie cubicularis deprecator. Virtus validus aliquid. " +
                "Adduco somnus quibusdam. Despecto nihil vinum. Claudeo nam ullus. Sursum tutamen rerum. " +
                "Cenaculum tabula adultus. Charisma thema super. Vobis cavus clibanus. Quo quod avaritia. " +
                "Condico apparatus nulla. Textilis depopulo acidus."));
    }

}

