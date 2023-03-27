package ch.zuehlke.fullstack.hackathon.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Card {

    private final Color color;
    private final CardValue value;

    public int getValue() {
        return value.getValue();
    }
}
