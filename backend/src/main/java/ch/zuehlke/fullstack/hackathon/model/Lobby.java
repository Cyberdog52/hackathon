package ch.zuehlke.fullstack.hackathon.model;

public record Lobby(Game game, int minPlayerCount, int maxPlayerCount) {
  public boolean addPlayer(ThunderShipsPlayer player) {
    if (game.getPlayers().size() < maxPlayerCount) {
      return game.getPlayers().add(player);
    }
    return false;
  }

  public boolean canStartGame() {
    return game.getPlayers().size() >= minPlayerCount;
  }

}
