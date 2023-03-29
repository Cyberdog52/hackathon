package ch.zuehlke.challenge.bot.brain;

import ch.zuehlke.common.GameAction;
import ch.zuehlke.common.cards.Card;
import ch.zuehlke.common.cards.Rank;
import ch.zuehlke.common.cards.Suit;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("simplebot")
public class SimpleBrain implements Brain {

    private Set<Card> cards;

    private static boolean timeToDraw(final Set<GameAction> possibleActions) {
        return possibleActions.contains(GameAction.DRAW_FROM_DISCARD_STACK) || possibleActions.contains(GameAction.DRAW_FROM_DRAW_STACK);
    }

    @Override
    public GameAction decide(final Set<GameAction> possibleActions) {
        //If simple bot can finish, it will finish
        if (possibleActions.contains(GameAction.FINISH)) {
            return GameAction.FINISH;
        }
        final var topCardFromDiscardStack = new Card(Suit.DIAMOND, Rank.ACE); //TODO: use card from websocket

        if (timeToDraw(possibleActions)) {
            return shouldBotTakeDiscardedCard(topCardFromDiscardStack) ? GameAction.DRAW_FROM_DISCARD_STACK : GameAction.DRAW_FROM_DRAW_STACK;
        }

        return GameAction.DISCARD; //TODO: discard highestCard()
    }

    private Card highestCard() {
        return cards.stream()
                .max((o1, o2) -> o1.getValue() - o2.getValue())
                .orElseThrow();
    }

    private boolean shouldBotTakeDiscardedCard(final Card discardedCard) {
        //If the discarded card is lower than the highest card in the hand, take it
        return discardedCard.getValue() < highestCard().getValue();
    }

    @Override
    public String name() {
        return "SimpleBrain";
    }

    @Override
    public String icon() {
        return "SimpleBrainIcon";
    }
}