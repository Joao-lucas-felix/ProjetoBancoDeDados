import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserEditedDialogComponent } from './user-edited-dialog.component';

describe('UserEditedDialogComponent', () => {
  let component: UserEditedDialogComponent;
  let fixture: ComponentFixture<UserEditedDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserEditedDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserEditedDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
