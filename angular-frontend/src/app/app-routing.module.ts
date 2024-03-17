import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { ExampleComponent } from "./example/example-component/example.component";
import { OtherComponent } from "./example/other-component/other.component";

const routes: Routes = [
  {
    path: "",
    component: ExampleComponent
  },
  {
    path: "other",
    component: OtherComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
