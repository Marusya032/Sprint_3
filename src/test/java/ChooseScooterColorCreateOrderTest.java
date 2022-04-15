import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class ChooseScooterColorCreateOrderTest {
    CourierClient courierClient;
    private final String color;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    public ChooseScooterColorCreateOrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static List<String> getColor() {
        return List.of("\"BLACK\"",
                "\"GREY\"",
                "\"BLACK, GREY\"",
                "");
    }

    @org.junit.Test
    @DisplayName("Choose color in create order test")
    public void chooseColorInCreateOrderTest() {
        ValidatableResponse loginResponse = courierClient.createOrders(color);
        boolean trackIsCreated = (Integer.toString(loginResponse.extract().path("track"))).isEmpty();

        assertThat("Тело ответа не содержит track", trackIsCreated, equalTo(false));
    }
}

