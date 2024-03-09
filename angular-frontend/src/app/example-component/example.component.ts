import { Component, OnInit } from "@angular/core";
import { ExampleService } from "./example.service";
import { ExampleDto } from "../../model/example/ExampleDto";
import { HttpErrorResponse } from "@angular/common/http";
import { forkJoin } from "rxjs";
import { MotdDto } from "src/model/example/MotdDto";

@Component({
  selector: "app-example-component",
  templateUrl: "./example.component.html",
  styleUrls: ["./example.component.scss"]
})
export class ExampleComponent implements OnInit {
  public exampleDto: ExampleDto | null = null;
  public motdDto: MotdDto | null = null;
  public error: HttpErrorResponse | null = null;
  public isLoading = true;

  constructor(private exampleService: ExampleService) {
  }

  ngOnInit(): void {
    this.loadExample();
  }

  hasError(): boolean {
    return !!this.error;
  }

  private loadExample(): void {
    forkJoin({
        exampleDto: this.exampleService.getExample(),
        motdDto: this.exampleService.getMotd()
      })
      .subscribe({
        next: response => {
          this.isLoading = false;
          this.exampleDto = response.exampleDto;
          this.motdDto = response.motdDto;
        },
        error: (error) => {
          this.error = error;
        }
      });    
  }
}
