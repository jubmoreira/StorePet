//1 - Pacote
package storepet;

//2 - Biblioteca

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3 - Classe
public class Pet {
    //3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; //Endereço da entidade pet

    //3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {

        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    //Incluir - Create - Post
    @Test(priority = 1) //Identifica  o método ou função com um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        //Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - when - Then

        given()// Dado
                .contentType("application/json") //Comum em API REST - antigas eram "txt/xml"
                .log().all()
                .body(jsonBody)//inclui
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Atena"))
                .body("status",is("available"))
                .body("category.name", is("AX2345LORT"))
                .body("tags.name",contains("data"))
        ;
    }
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "1986021135";

        String token =

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atena"))
                .body("category.name", is("AX2345LORT"))
                .body("status", is("available"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token é " + token);

    }
    @Test(priority = 3)
    public void alterarPet()throws IOException{
        String jsonBody = lerJson("db/pet1.json");

        given()// Dado
                .contentType("application/json") //Comum em API REST - antigas eram "txt/xml"
                .log().all()
                .body(jsonBody)

        .when()
                .put(uri)//altera

        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Atena"))
                .body("status", is("sold"))
        ;
    }
    @Test
    public void excluirPet(){
        String petId = "1986021135";

        given()// Dado
                .contentType("application/json") //Comum em API REST - antigas eram "txt/xml"
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))

        ;
    }
}
