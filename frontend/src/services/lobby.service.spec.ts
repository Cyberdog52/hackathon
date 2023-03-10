import { LobbyService } from "./lobby.service";
import { HttpClient } from "@angular/common/http";
import { of } from "rxjs";


describe("LobbyService", () => {
  let service: LobbyService;
  let httpClientSpy: jasmine.SpyObj<HttpClient>;

  beforeEach(async () => {
    httpClientSpy = jasmine.createSpyObj("HttpClient", ["get"]);
    service = new LobbyService(httpClientSpy);
  });

  it("should get empty list of games successfully", () => {
    httpClientSpy.get.and.returnValue(of([]));

    service.getGames();

    expect(httpClientSpy.get).toHaveBeenCalledWith("api/lobby/games");
  });
});
