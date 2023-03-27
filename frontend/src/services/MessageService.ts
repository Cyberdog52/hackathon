import { Injectable } from "@angular/core";
import { Observable, of, Subject } from "rxjs";

@Injectable({
  providedIn: "root",
})
export class MessageService {
  private messageSubject = new Subject<string>();
  messages$ = this.messageSubject.asObservable();

  add(message: string): void {
    this.messageSubject.next(message);
    //this.messages$.subscribe(messages => {messages.push(message)});
  }

  clear(): void {
    this.messages$.subscribe(messages => {messages = ""});
  }
}
