package ch.zuehlke.fullstack.hackathon.model.game;

public enum GameEvent {
    // LOBBY
    PLAYER_JOINED,
    ALL_PLAYERS_JOINED,

    // SETUP
    PLACE_BOAT,
    ALL_BOATS_PLACED,

    // PLAYING
    ATTACK,
    ALL_BOATS_DESTROYED,

    // END
}
