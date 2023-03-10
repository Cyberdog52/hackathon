package ch.zuehlke.challenge.bot.domain;

public record GameId(int id) {
    public static GameId fromString(String s) {
        return new GameId(Integer.parseInt(s));
    }
}
