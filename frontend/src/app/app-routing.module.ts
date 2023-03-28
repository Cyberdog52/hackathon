import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LobbyComponent } from "./lobby/lobby.component";
import { GameSpectatorComponent } from "./game-spectator/game-spectator.component";

const routes: Routes = [
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
