import { Component, OnInit } from "@angular/core";
import { RemoteService } from "../../shared/remote-service/remote.service";
import { ExampleDto } from "../model/example-dto.model";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
  selector: "example-component",
  templateUrl: "./example.component.html",
  styleUrls: ["./example.component.scss"]
})
export class ExampleComponent implements OnInit {
  public exampleDto?: ExampleDto;
  public error?: HttpErrorResponse;
  public isLoading = true;

  constructor(private exampleService: RemoteService) {
  }

  public ngOnInit(): void {
    this.loadExample();
  }

  public hasError(): boolean {
    return !!this.error;
  }

  public async loadExample(): Promise<void> {
    this.isLoading = true;
    const exampleDto = await this.exampleService.get<ExampleDto>("");
    if (exampleDto) {
      this.exampleDto = exampleDto;
    }
    this.isLoading = false;
  }
}
