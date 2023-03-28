import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";

import { AppRoutingModule } from "./app-routing.module";
import { AppComponent } from "./app.component";
import { HttpClientModule } from "@angular/common/http";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatCardModule } from "@angular/material/card";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HeaderComponent } from "./header/header.component";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatChipsModule } from "@angular/material/chips";
import { MatListModule } from "@angular/material/list";
import { MatchComponent } from "./match/match.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MessagesComponent } from "./messages/messages.component";
import { MatchesComponent } from "./matches/matches.component";
import { CreateMatchComponent } from "./create-match/create-match.component";
import { MatTableModule } from "@angular/material/table";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import { FormsModule } from "@angular/forms";
import { PlayingMatchComponent } from "./playing-match/playing-match.component";
import { CardComponent } from "./card/card.component";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MatchComponent,
    DashboardComponent,
    MessagesComponent,
    MatchesComponent,
    CreateMatchComponent,
    PlayingMatchComponent,
    CardComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatGridListModule,
        MatCardModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        MatChipsModule,
        MatListModule,
        MatFormFieldModule,
        MatTableModule,
        MatSnackBarModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
