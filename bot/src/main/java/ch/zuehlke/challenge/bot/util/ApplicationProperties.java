package ch.zuehlke.challenge.bot.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bot")
@Data
public class ApplicationProperties {
    private String name;
    private Integer gameId;
    private String backendRootUri;
    private String backendMatchBaseUrl;
    private String backendWaitingBaseUrl;
    private String backendCreatePlayerUrl;
    private String backendPlayUrl;
    private String webSocketUri;
}
