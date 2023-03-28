package ch.zuehlke.fullstack.hackathon.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeckTest {

    @Test
    void itShouldCreateAFullDeck(){
        final var deck = Deck.generateNewDeck();
        assertThat(deck).isNotNull()
                .asList()
                .hasSize(52);
    }

}