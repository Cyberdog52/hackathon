import { LobbyComponent } from "./lobby.component";
import { LobbyService } from "../../services/lobby.service";

describe("LobbyComponent", () => {
  let component: LobbyComponent;
  let lobbyServiceSpy: jasmine.SpyObj<LobbyService>;

  beforeEach(async () => {
    lobbyServiceSpy = jasmine.createSpyObj("LobbyService", ["getGames"]);
    component = new LobbyComponent(lobbyServiceSpy);
  });

  it("should init successfully", () => {
    component.ngOnInit();

    expect(lobbyServiceSpy.getGames).toHaveBeenCalledWith();
  });
});
