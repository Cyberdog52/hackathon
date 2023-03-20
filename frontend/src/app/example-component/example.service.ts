import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ExampleDto } from "../../model/example/ExampleDto";

@Injectable({
  providedIn: "root"
})
export class ExampleService {

  private backendUrl = "api/example";

  constructor(private httpClient: HttpClient) {
  }

  public getExample(): Observable<ExampleDto>  {
    const url = `${this.backendUrl}`;
    return this.httpClient.get<ExampleDto>(url);
  }
}
