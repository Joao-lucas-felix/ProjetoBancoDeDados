import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PedidosDoUsuarioComponent } from './pedidos-do-usuario.component';

describe('PedidosDoUsuarioComponent', () => {
  let component: PedidosDoUsuarioComponent;
  let fixture: ComponentFixture<PedidosDoUsuarioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PedidosDoUsuarioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PedidosDoUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
