import { ComponentFixture, TestBed } from "@angular/core/testing";

import { PlayingMatchComponent } from "./playing-match.component";

describe("PlayingMatchComponent", () => {
  let component: PlayingMatchComponent;
  let fixture: ComponentFixture<PlayingMatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayingMatchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayingMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
