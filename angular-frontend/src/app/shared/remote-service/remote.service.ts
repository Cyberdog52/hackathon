import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { catchError, firstValueFrom, of } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class RemoteService {
  private backendBaseUrl = "api/example";

  constructor(
    private httpClient: HttpClient,
    private toastr: ToastrService) {
  }

  get<T>(path: string): Promise<T | null> {
    return firstValueFrom(this.httpClient
      .get<T>(`${this.backendBaseUrl}/${path}`)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.toastr.error(error.message);
          return of(null);
        })
      ));
  }

  post<T>(path: string, data?: any): Promise<T | null> {
    return firstValueFrom(this.httpClient
      .post<T>(`${this.backendBaseUrl}/${path}`, data)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.toastr.error(error.message);
          return of(null);
        })
      ));
  }

  put<T>(path: string, data?: any): Promise<T | null> {
    return firstValueFrom(this.httpClient
      .put<T>(`${this.backendBaseUrl}/${path}`, data)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.toastr.error(error.message);
          return of(null);
        })
      ));
  }

  delete<T>(path: string): Promise<T | null> {
    return firstValueFrom(this.httpClient
      .delete<T>(`${this.backendBaseUrl}/${path}`)
      .pipe(
        catchError((error: HttpErrorResponse) => {
          this.toastr.error(error.message);
          return of(null);
        })
      ));
  }
}
