import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginCourierFailTest {
   Courier courier;
   CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier  = Courier.getRandom();
    }

    @Test
    @DisplayName("Non existent user login courier ")
    public void nonExistentUserLoginCourier() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with invalid login")
    public void loginCourierWithInvalidLogin() {
        courierClient.createCourier(courier);
        courier.setLogin("111");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with invalid login")
    public void loginCourierWithInvalidPassword() {
        courierClient.createCourier(courier);
        courier.setPassword("111");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Учетная запись не найдена"));
            }
}
