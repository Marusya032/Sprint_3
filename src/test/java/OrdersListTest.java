import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class OrdersListTest {

    CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Choose color in create order test")
    public void chooseColorInCreateOrderTest() {
        ValidatableResponse loginResponse = courierClient.getOrdersList();
        List<ArrayList> idList = loginResponse.extract().body().path("orders.id");
        assertThat("Ответ не содержит заказов", idList.isEmpty(), equalTo(false));
    }
    }



