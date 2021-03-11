package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Utils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;


public class ProdutosTest extends Utils {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = baseURI;
	}
	
	public String identificadorProduto = "";
	
	
	  @SuppressWarnings("unchecked")
	@Test
	  public void cadastrarProdutoSemAutorizacao() {
	    JSONObject params = new JSONObject();
	    params.put("nome", "Mouse XPTO");
	    params.put("preco", "500");
	    params.put("descricao", "Mouse");
	    params.put("quantidade", "10");

	    given()
	        .contentType(ContentType.JSON)
	        .body(params.toJSONString())
	    .when()
	        .post("/produtos")
	    .then()
	        .assertThat()
	        .statusCode(401)
	        .body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
	  }
	  

	  @SuppressWarnings("unchecked")
	  @Test
	  public void cadastrarProdutoComAutorizacao() {

		String authorization = obterTokenAdministrador();
		identificadorProduto = randomValue();
		
		  
	    JSONObject params = new JSONObject();
	    params.put("nome", "Produto " + identificadorProduto);
	    params.put("preco", "500");
	    params.put("descricao", "Mouse");
	    params.put("quantidade", "10");

	    given()
	    	.header("authorization", authorization)
	        .contentType(ContentType.JSON)
	        .body(params.toJSONString())
	    .when()
	        .post("/produtos")
	    .then()
	        .assertThat()
	        .statusCode(201)
	        .body("message", equalTo("Cadastro realizado com sucesso"));
	  }
	  
	  @SuppressWarnings("unchecked")
	@Test
	  public void cadastrarProdutoDuplicado() {

		String authorization = obterTokenAdministrador();
		
		cadastrarProdutoComAutorizacao();
		  
	    JSONObject params = new JSONObject();
	    params.put("nome", "Produto " + identificadorProduto);
	    params.put("preco", "500");
	    params.put("descricao", "Mouse");
	    params.put("quantidade", "10");

	    given()
	    	.header("authorization", authorization)
	        .contentType(ContentType.JSON)
	        .body(params.toJSONString())
	    .when()
	        .post("/produtos")
	    .then()
	        .assertThat()
	        .statusCode(400)
	        .body("message", equalTo("Já existe produto com esse nome"));
	  }
	
	  
	  
	 @Test
	  public void validarProduto() {
		  RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
		  //requestBuilder.log(LogDetail.ALL);
		  RequestSpecification requestSpecification = requestBuilder.build();
		  
	    given()
	    	.spec(requestSpecification)
	    	 .queryParam("nome", "Logitech MX Vertical")
	    .when()
	        .get("/produtos")
	    .then()
	        .assertThat()
	        .statusCode(200)
	        .body("produtos.nome[0]", equalTo("Logitech MX Vertical"))
	        .body("produtos.descricao[0]", equalTo("Mouse"))
	        .body("produtos.quantidade[0]", is(notNullValue()))
	        .body("produtos._id[0]", is(notNullValue()))
	        .body("produtos.preco[0]", is(greaterThanOrEqualTo(0)));
	  }
	 
	  @Test
	  public void ListarTodosProdutos() {
	    given()
	  
	    .when()
	        .get("/produtos")
	    .then()
	        .assertThat()
	        .statusCode(200);
	  }
	 

	  @Test
	  public void ExcluirProduto() {

		String authorization = obterTokenAdministrador(); // Atualizar token de sessão
		String _produto = Produto(); // Gerar novo produto para deleção
		  
	    given()
	    	.header("authorization", authorization) // token de administrador necessário
	        .contentType(ContentType.JSON)
	    .when()
	        .delete("/produtos/" + _produto) // id do produto a ser deletado
	    .then()
	        .assertThat()
	        .statusCode(HttpStatus.SC_OK)
	        .body("message", equalTo("Registro excluído com sucesso"));
	  }
	  
	  @Test
	  public void validaSchemaProduto() {
		    given()
		    .when()
		        .get("/produtos")
		    .then()
		        .assertThat()
		        .statusCode(200)
		        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("resources/schemas/Produtos.json"));
	  }

}
