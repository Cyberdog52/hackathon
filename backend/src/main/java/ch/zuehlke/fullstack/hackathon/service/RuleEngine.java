package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Card;

import java.util.List;

public class RuleEngine {

    public static final int NUMBER_OF_HANDCARDS = 7;

    public static int calculateScore(List<Card> cards) {
        if(cards.size() != NUMBER_OF_HANDCARDS) {
            throw new IllegalArgumentException("Number of cards must be " + NUMBER_OF_HANDCARDS);
        }

        return 0;
    }
}
