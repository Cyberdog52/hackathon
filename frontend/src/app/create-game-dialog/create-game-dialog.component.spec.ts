import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateGameDialogComponent } from './create-game-dialog.component';

describe('CreateGameDialogComponent', () => {
  let component: CreateGameDialogComponent;
  let fixture: ComponentFixture<CreateGameDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateGameDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateGameDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
