package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.Card;
import ch.zuehlke.fullstack.hackathon.model.exception.CardStackException;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class CardStack {
    private Stack<Card> stack;
    private Stack<Card> discarded;

    public CardStack(final List<Card> deck) {
        this.stack = new Stack<>();
        this.stack.addAll(deck);
        Collections.shuffle(stack);
        this.discarded = new Stack<>();
        final var card = this.stack.pop();
        this.discarded.add(card);
    }

    public Card peekDiscarded() {
        return this.discarded.peek();
    }

    public Card getLastDiscarded() {
        return this.discarded.pop();
    }

    public Card getFromStack() {
        if (stack.isEmpty()) {
            this.stack = this.recycleDiscarded();
        }
        return this.stack.pop();
    }

    public void discard(final Card card) throws CardStackException {
        if (this.stack.contains(card)) {
            throw new CardStackException("The card %s cannot be discarded, it is already in the stack".formatted(card));
        }

        if (this.discarded.contains(card)) {
            throw new CardStackException("The card %s cannot be discarded, it is already in the discarded pile".formatted(card));
        }
        this.discarded.add(card);
    }

    private Stack<Card> recycleDiscarded() {
        Collections.shuffle(this.discarded);
        final var cards = this.discarded;
        this.discarded = new Stack<>();
        return cards;
    }

}
