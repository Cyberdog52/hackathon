package ch.zuehlke.fullstack.hackathon.service;

import ch.zuehlke.fullstack.hackathon.model.Card;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RuleService {
    public boolean isWinningHand(Set<Card> hand) {
        return RuleEngine.isWinning(hand);
    }

    public int calculateScore(Set<Card> hand) {
        return RuleEngine.calculateScore(hand);
    }
}
