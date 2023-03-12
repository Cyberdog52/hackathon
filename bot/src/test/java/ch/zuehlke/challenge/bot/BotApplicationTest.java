package ch.zuehlke.challenge.bot;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Disabled("Backend calls need to be mocked") // Improve: enable it
public class BotApplicationTest {
    
    @Test
    public void contextLoads() {
    }

}