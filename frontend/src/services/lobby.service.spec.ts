import { LobbyService } from "./lobby.service";
import { HttpClient } from "@angular/common/http";
import { EMPTY, of } from "rxjs";
import { GameId } from "../model/lobby";
import { waitForAsync } from "@angular/core/testing";


describe("LobbyService", () => {
  let service: LobbyService;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(async () => {
    httpClientSpy = jasmine.createSpyObj("HttpClient", ["get", "post", "delete"]);
    service = new LobbyService(httpClientSpy);
  });

  it("should get empty list of games successfully", waitForAsync(() => {
    httpClientSpy.get.and.returnValue(of([]));

    service.getGames().subscribe((result) => {
      expect(httpClientSpy.get).toHaveBeenCalledWith("api/lobby/games");
      expect(result).toEqual([]);
    });
  }));

  it("should create a game successfully", waitForAsync(() => {
    const gameId = { value: 123 } as GameId;
    httpClientSpy.post.and.returnValue(of(gameId));

    service.createGame().subscribe((result) => {
      expect(httpClientSpy.post).toHaveBeenCalledWith("api/lobby/game", {});
      expect(result).toBe(gameId);
    });
  }));

  it("should delete a game successfully", waitForAsync(() => {
    const gameId = { value: 123 } as GameId;
    httpClientSpy.delete.and.returnValue(EMPTY);

    service.deleteGame(gameId).subscribe((result) => {
      expect(httpClientSpy.delete).toHaveBeenCalledWith(`api/lobby/game/${gameId.value}`, {});
      expect(result).toBeUndefined();
    });
  }));

  it("should start a game successfully", waitForAsync(() => {
    const gameId = { value: 123 } as GameId;
    httpClientSpy.post.and.returnValue(EMPTY);

    service.startGame(gameId).subscribe((result) => {
      expect(httpClientSpy.post).toHaveBeenCalledWith(`api/lobby/game/${gameId.value}/start`, {});
      expect(result).toBeUndefined();
    });
  }));
});
