import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadBooksComponent } from './load-books.component';

describe('LoadBooksComponent', () => {
  let component: LoadBooksComponent;
  let fixture: ComponentFixture<LoadBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoadBooksComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoadBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
