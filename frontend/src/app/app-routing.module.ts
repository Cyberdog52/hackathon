import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { MatchComponent } from "./match/match.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { MatchesComponent } from "./matches/matches.component";
import { PlayingMatchComponent } from "./playing-match/playing-match.component";
import {RulesComponent} from "./rules/rules.component";

const routes: Routes = [
  { path: "match/:id", component: MatchComponent },
  { path: "matches", component: MatchesComponent },
  { path: "dashboard", component: DashboardComponent },
  { path: "playing-match/:id", component: PlayingMatchComponent },
  { path: "rules", component: RulesComponent },
  { path: "", redirectTo: "/dashboard", pathMatch: "full" },
  // Improve: create PageNotFound component and match to '**'
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
