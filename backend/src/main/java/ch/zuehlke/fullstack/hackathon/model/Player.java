package ch.zuehlke.fullstack.hackathon.model;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class Player {
    private final UUID id;
    private final String name;
    private final String icon;
    private final List<Card> cards;
}
