import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginCourierSuccessTest {
    Courier courier;
    CourierClient courierClient;
    int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier  = Courier.getRandom();
        courierClient.createCourier(courier);
    }

    @After
    public void TearDown() {
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Courier can login")
    public void courierCanLogin(){
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        courierId = loginResponse.extract().path("id");

        assertThat("Ошибка при логине курьера", statusCode, equalTo(SC_OK));
        assertThat("Body is incorrect", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Login courier with invalid login")
    public void loginCourierWithInvalidLogin() {
        courier.setLogin("111");

        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier with invalid password")
    public void loginCourierWithInvalidPassword() {
        courier.setPassword("111");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_NOT_FOUND));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Login courier without login")
    public void loginCourierWithoutLogin() {
        courierClient.createCourier(courier);
        courier.setLogin("");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Login courier without password")
    public void loginCourierWithoutPassword() {
        courierClient.createCourier(courier);
        courier.setPassword("");
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));

        int statusCode = loginResponse.extract().statusCode();
        String bodyMessage = loginResponse.extract().path("message");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Неверное тело ответа при создании курьера", bodyMessage, equalTo("Недостаточно данных для входа"));

    }
}