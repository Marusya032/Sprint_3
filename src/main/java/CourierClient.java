import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierClient extends ScooterRestClient{

  private static final String COURIER_PATH = "/api/v1/courier/";
  private static final String ORDER_PATH = "/api/v1/orders/";


    @Step("Login with credentials {credentials}")
    public ValidatableResponse login(CourierCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH + "login")
                .then();
    }

    @Step("Create with courier {courier}")
    public ValidatableResponse createCourier(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Delete courier {courierId}")
    public ValidatableResponse deleteCourier(int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then();
    }

    @Step("Create orders with color {color}")
    public ValidatableResponse createOrders(String color) {
        return given()
                .spec(getBaseSpec())
                .body("{\"firstName\":\"Naruto\",\"lastName\":\"Uchiha\",\"address\":\"Konoha, 142 apt.\",\"metroStation\":4,\"phone\":\"+7 800 355 35 35\",\"rentTime\":5,\"deliveryDate\":\"2020-06-06\",\"comment\":\"Saske, come back to Konoha\",\"color\":[" + color + "]}")
                .when()
                .post(ORDER_PATH)
                .then();
    }


    @Step("Get Orders List")
    public ValidatableResponse getOrdersList(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

 }
