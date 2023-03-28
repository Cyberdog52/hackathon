import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LobbyComponent } from "./lobby/lobby.component";
import { GameSpectatorComponent } from "./game-spectator/game-spectator.component";
import { GamePlayingComponent } from "./game-playing/game-playing.component";

const routes: Routes = [
  { path: "play/:gameId", component: GamePlayingComponent },
  { path: "spectator/:gameId", component: GameSpectatorComponent },
  { path: "lobby", component: LobbyComponent },
  { path: "", redirectTo: "/lobby", pathMatch: "full" },
  // Improve: create PageNotFound component and match to '**'
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
