package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class LoginTest extends Utils {
	public static RequestSpecification request;
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = baseURI;
		//RestAssured.port = 3000;
	}

  @SuppressWarnings("unchecked")
  //@Test
  public void loginComSucesso() {
    JSONObject params = new JSONObject();
    params.put("email", "fulano@qa.com");
    params.put("password", "teste");

    given()
        .contentType(ContentType.JSON)
        .body(params.toJSONString())
    .when()
        .post("/login")
    .then()
        .assertThat()
        .statusCode(200)
        .body("message", equalTo("Login realizado com sucesso"))
        .body("authorization", is(notNullValue()));
  }
  
	@SuppressWarnings("unchecked")
	//@Test
	public void loginInvalido() {

	    JSONObject params = new JSONObject();
	    params.put("email", "emailInvalidoInexistente@invalid.com");
	    params.put("password", "senhaInvalida");
	    
	    given()
        .contentType(ContentType.JSON)
        .body(params.toJSONString())
    .when()
        .post("/login")
    .then()
        .assertThat()
        .statusCode(401)
        .body("message", equalTo("Email e/ou senha inválidos"));
	}
	
}
