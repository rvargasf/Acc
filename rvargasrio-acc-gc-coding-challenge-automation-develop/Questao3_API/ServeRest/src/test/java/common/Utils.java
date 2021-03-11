package common;

import static io.restassured.RestAssured.given;

import java.util.Random;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static final String baseURI = "https://serverest.dev/";
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = baseURI;
	}

	@SuppressWarnings("unchecked")
	public String obterTokenAdministrador() {
		RequestSpecification request;
		
		JSONObject params = new JSONObject();
		params.put("email", "fulano@qa.com");
		params.put("password", "teste");

		request =  given()
					//.log()
					//.all()
					.contentType("application/json")
					.body(params.toJSONString());
		
		String response = request
							.when()
								.post("/login")
							.then()
								.statusCode(200)
								.extract()
								.asString();
		JsonPath json = new JsonPath(response);
		String authorization = json.getString("authorization");

		return authorization;
	}

	@SuppressWarnings({ "unchecked"})
	public String Produto() {
		RequestSpecification request;
		
		JSONObject params = new JSONObject();
	    params.put("nome", "Produto " + randomValue());
	    params.put("preco", "500");
	    params.put("descricao", "Mouse");
	    params.put("quantidade", "10");

		request =  given()
					.contentType("application/json")
					.body(params.toJSONString())
					.header("authorization", obterTokenAdministrador());
		
		String response = request
							.when()
								.post("/produtos")
							.then()
								.statusCode(HttpStatus.SC_CREATED)
								.extract()
								.asString();
		JsonPath json = new JsonPath(response);
		String idProduto = json.getString("_id");

		return idProduto;
	}
	
	public String randomValue() {
		String characters = "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
		String RandomString = "";
		int lenght = 6;
		
		
		Random rand = new Random();
		char[] text = new char[lenght];
		
		for (int i = 0; i < lenght; i++) {
			text[i] = characters.charAt(rand.nextInt(characters.length()));
			
		}
		
		for(int i = 0; i< text.length; i++) {
			RandomString += text[i];
		}
		
		return RandomString;
	}
}
