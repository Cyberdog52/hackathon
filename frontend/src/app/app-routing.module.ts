import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LobbyComponent } from './lobby/lobby.component';
import { RulesComponent } from './rules/rules.component';

const routes: Routes = [
  { path: 'lobby', component: LobbyComponent },
  { path: 'rules', component: RulesComponent },
  { path: '', redirectTo: '/lobby', pathMatch: 'full' },
  // Improve: create PageNotFound component and match to '**'
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
