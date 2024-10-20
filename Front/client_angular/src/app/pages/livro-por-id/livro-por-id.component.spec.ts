import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LivroPorIdComponent } from './livro-por-id.component';

describe('LivroPorIdComponent', () => {
  let component: LivroPorIdComponent;
  let fixture: ComponentFixture<LivroPorIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LivroPorIdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LivroPorIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
