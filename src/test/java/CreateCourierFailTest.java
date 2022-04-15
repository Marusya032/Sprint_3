import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierFailTest {
    Courier courier;
    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
    }

    @Test
    @DisplayName("Creating courier without login")
    public void creatingCourierWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse createCourier = courierClient.createCourier(courier);

        int statusCode = createCourier.extract().statusCode();
        String bodyMessage = createCourier.extract().path("message");

        assertThat("Ошибка при создании курьера без обязательного поля Логин", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Неверное сообщение в ответе", bodyMessage, equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Creating courier without password")
    public void creatingCourierWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse createCourier = courierClient.createCourier(courier);

        int statusCode = createCourier.extract().statusCode();
        String bodyMessage = createCourier.extract().path("message");

        assertThat("Ошибка при создании курьера без обязательного поля Пароль", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Неверное сообщение в ответе", bodyMessage, equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Creating courier with exiting login")
    public void createCourierWithExitingLogin() {
        ValidatableResponse createCourier = courierClient.createCourier(courier);
        courier.setPassword("111");
        courier.setFirstName("222");
        ValidatableResponse createCourierWithExitingLogin = courierClient.createCourier(courier);

        int statusCode = createCourierWithExitingLogin.extract().statusCode();
        String bodyMessage = createCourierWithExitingLogin.extract().path("message");
        assertThat("Ошибка при создании курьера с существующим логином", statusCode, equalTo(409));
        assertThat("Неверное сообщение в ответе", bodyMessage, equalTo("Этот логин уже используется. Попробуйте другой."));


    }
}

