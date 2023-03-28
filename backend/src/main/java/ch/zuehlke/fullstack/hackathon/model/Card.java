package ch.zuehlke.fullstack.hackathon.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Card {

    private final Color color;
    private final CardValue value;

    @Override
    public String toString() {
        return "Card{ %s of %s }".formatted(value, color);
    }
}
