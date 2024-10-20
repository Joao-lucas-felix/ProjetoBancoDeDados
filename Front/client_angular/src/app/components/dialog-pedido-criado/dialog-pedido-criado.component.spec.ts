import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogPedidoCriadoComponent } from './dialog-pedido-criado.component';

describe('DialogPedidoCriadoComponent', () => {
  let component: DialogPedidoCriadoComponent;
  let fixture: ComponentFixture<DialogPedidoCriadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DialogPedidoCriadoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogPedidoCriadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
