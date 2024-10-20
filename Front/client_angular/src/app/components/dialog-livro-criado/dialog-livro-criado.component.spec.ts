import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogLivroCriadoComponent } from './dialog-livro-criado.component';

describe('DialogLivroCriadoComponent', () => {
  let component: DialogLivroCriadoComponent;
  let fixture: ComponentFixture<DialogLivroCriadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialogLivroCriadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogLivroCriadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
