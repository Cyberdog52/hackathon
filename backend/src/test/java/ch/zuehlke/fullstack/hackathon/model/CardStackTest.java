package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.fullstack.hackathon.model.exception.CardStackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CardStackTest {

    private CardStack cardStack;

    @BeforeEach
    void setUp() {
        this.cardStack = new CardStack(Deck.generateNewDeck());
    }

    @Test
    void itShouldRememberDiscardedCards_whenValid() throws CardStackException {
        final var card = this.cardStack.getFromStack();
        assertThat(card).isNotEqualTo(this.cardStack.peekDiscarded());
        this.cardStack.discard(card);
        assertThat(card).isEqualTo(this.cardStack.peekDiscarded());
    }

    @Test
    void itShouldThrowAnException_whenACardIsDiscardedTwice() throws CardStackException {
        final var card = this.cardStack.getFromStack();
        this.cardStack.discard(card);
        assertThatThrownBy(() -> this.cardStack.discard(card)).isInstanceOf(CardStackException.class);
    }

    @Test
    void itShouldThrowAnException_whenACardIsStillOnTheStack(){
        final var myDeck = Deck.generateNewDeck();
        myDeck.add(new Card(Color.SPADE, CardValue.KING));
        final var myStack = new CardStack(myDeck);
        assertThatThrownBy(() -> myStack.discard(new Card(Color.SPADE, CardValue.KING))).isInstanceOf(CardStackException.class);
    }
}