package ch.zuehlke.common.shared.event;

public enum EventType {
    SETUP_GAME, // GameConfigEvent
    START_PLAYING, // GameStartPlayingEvent
    TAKE_TURN, // TakeTurnEvent
    PLAYER_JOINED, // PlayerJoinEvent
    PLAYER_ATTACKED, // AttackEvent
    BOAT_PLACED, // PlaceBoatEvent
    GAME_ENDED // GameEndEvent
}
