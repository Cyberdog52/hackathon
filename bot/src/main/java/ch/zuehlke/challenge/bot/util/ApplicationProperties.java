package ch.zuehlke.challenge.bot.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
@ConfigurationProperties(prefix = "bot")
@Data
public class ApplicationProperties {
    private String name;
    private UUID gameId;
    private UUID playerId;
    private String backendRootUri;
    private String backendJoinUrl;
    private String backendPlayUrl;
    private String webSocketUri;
}
