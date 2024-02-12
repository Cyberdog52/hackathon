package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.ExampleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ExampleService {

    private static final List<String> POSSIBLE_VALUES = List.of("Example", "Beispiel", "Exemple", "Ejemplar");
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public ExampleDto getExampleDto() {
        int randomInt = RANDOM.nextInt(POSSIBLE_VALUES.size());

        return new ExampleDto(POSSIBLE_VALUES.get(randomInt), randomInt);
    }

}
