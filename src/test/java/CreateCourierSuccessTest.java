import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateCourierSuccessTest {
    Courier courier;
    CourierClient courierClient;
    int courierId;

    @Before
    public void setUp() {
      courierClient = new CourierClient();
    }

    @After
    public void TearDown() {
        ValidatableResponse loginResponse = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        courierId = loginResponse.extract().path("id");
        courierClient.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Test courier can be created")
        public void courierCanBeCreated(){
        courier  = Courier.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(courier);

        int statusCode = createResponse.extract().statusCode();
        boolean isCourierCreated = createResponse.extract().path("ok");

        assertThat("Ошибка при создании курьера", statusCode, equalTo(SC_CREATED));
        assertThat("Неверное тело ответа при создании курьера", isCourierCreated, equalTo(true));
    }

    @Test
    @DisplayName("Cannot create two identical couriers")
    public void cannotCreateTwoIdenticalCouriers(){

        courier  = Courier.getRandom();
        ValidatableResponse createResponse = courierClient.createCourier(courier);
        ValidatableResponse createCourierDublicate = courierClient.createCourier(courier);

        int statusCode = createCourierDublicate.extract().statusCode();
        String body = createCourierDublicate.extract().path("message");

        assertThat("Ошибка при создании курьера с дублирующими  данными", statusCode, equalTo(409));
        assertThat("Неверное сообщение в ответе", body, equalTo("Этот логин уже используется. Попробуйте другой."));
    }

}
