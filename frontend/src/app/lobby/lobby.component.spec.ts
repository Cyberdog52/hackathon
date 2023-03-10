import { LobbyComponent } from "./lobby.component";
import { LobbyService } from "../../services/lobby.service";
import { of } from "rxjs";

describe("LobbyComponent", () => {
  let component: LobbyComponent;
  let lobbyServiceSpy: jasmine.SpyObj<LobbyService>;

  beforeEach(async () => {
    lobbyServiceSpy = jasmine.createSpyObj("LobbyService", ["getGames"]);
    component = new LobbyComponent(lobbyServiceSpy);
  });

  it("should init successfully", () => {
    lobbyServiceSpy.getGames.and.returnValue(of([]));
    
    component.ngOnInit();

    expect(lobbyServiceSpy.getGames).toHaveBeenCalledWith();
  });
});
