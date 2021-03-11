package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Utils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UsuariosTest extends Utils {

	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = baseURI;
	}
	
	@SuppressWarnings("unchecked")
	@Test
	  public void cadastrarNovoUsuario() {
		String authorization = obterTokenAdministrador();
		
	    JSONObject params = new JSONObject();
	    params.put("nome", "LoremIpsum");
	    params.put("email", "Email_".concat(randomValue()) +"@mail.com");
	    params.put("password", "1234567");
	    params.put("administrador", "false");

	    given()
    	    .header("authorization", authorization)
	        .contentType(ContentType.JSON)
	        .body(params.toJSONString())
	    .when()
	        .post("/usuarios")
	    .then()
	        .assertThat()
	        .statusCode(201)
	        .body("message", equalTo("Cadastro realizado com sucesso"));
	  }
	
	@SuppressWarnings("unchecked")
	@Test
	  public void cadastrarUsuarioDuplicado() {
	    JSONObject params = new JSONObject();
	    params.put("nome", "Fulano da Silva");
	    params.put("email", "fulano@qa.com");
	    params.put("password", "teste");
	    params.put("administrador", "true");

	    given()
	        .contentType(ContentType.JSON)
	        .body(params.toJSONString())
	    .when()
	        .post("/usuarios")
	    .then()
	        .assertThat()
	        .statusCode(400)
	        .body("message", equalTo("Este email já está sendo usado"));
	  }
	  

	  
	  @Test
	  public void validarUsuario() {
	    given()
	    .queryParam("nome", "Fulano da Silva")
	    .when()
	        .get("/usuarios")
	    .then()
	        .assertThat()
	        .statusCode(200)
	        .body("usuarios.nome[0]", equalTo("Fulano da Silva"))
	        .body("usuarios.email[0]", equalTo("fulano@qa.com"))
	        .body("usuarios.password[0]", equalTo("teste"))
	        .body("usuarios.administrador[0]", equalTo("true"));
	  }
	  
	  @Test
	  public void ListarUsuariosCadastrados() {
	    given()
	  
	    .when()
	        .get("/usuarios")
	    .then()
	        .assertThat()
	        .statusCode(200);
	  }
	  
	  @Test
	  public void validaSchemaUsuario() {
		    given()
		    .when()
		        .get("/usuarios")
		    .then()
		        .assertThat()
		        .statusCode(200)
		        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("resources/schemas/Usuarios.json"));
	  }
	  	  
}
