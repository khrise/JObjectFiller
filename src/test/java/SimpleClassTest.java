import com.tynamix.objectfiller.Filler;
import dto.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleClassTest {

    @Test
    public void testCreateAndFillUser() {

        Filler<User> filler = new Filler<User>();
        User user = filler.Create(User.class);

        assertNotNull(user.getFirstName());
        assertNotEquals("", user.getFirstName());

        assertNotEquals(0, user.getAge());
        assertNotNull(user.getUserId());

    }
}
