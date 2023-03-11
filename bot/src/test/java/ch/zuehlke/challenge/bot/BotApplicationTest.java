package ch.zuehlke.challenge.bot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BotApplicationTest {

    @Disabled("Backend calls need to be mocked") // Improve: enable it
    @Test
    public void contextLoads() {
    }

}