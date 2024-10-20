import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FuncionarioPorIdComponent } from './funcionario-por-id.component';

describe('FuncionarioPorIdComponent', () => {
  let component: FuncionarioPorIdComponent;
  let fixture: ComponentFixture<FuncionarioPorIdComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuncionarioPorIdComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FuncionarioPorIdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
