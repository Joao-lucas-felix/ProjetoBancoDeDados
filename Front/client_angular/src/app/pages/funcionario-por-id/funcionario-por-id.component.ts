import { Component, inject, Input, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-funcionario-por-id',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './funcionario-por-id.component.html',
  styleUrl: './funcionario-por-id.component.scss'
})
export class FuncionarioPorIdComponent implements OnInit {
  #apiSerice = inject(ApiService)
  funcionario = this.#apiSerice.getFuncionarioByID
  pageOfPedidos = this.#apiSerice.getPagedPedidos
  //id vindo pela rota 
  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }

  ngOnInit(): void {
    this.#apiSerice.httpGetFuncionarioByID$(this.getID()!).subscribe()
    this.#apiSerice.httpListPagedPedidosDeUmFuncionario$(this.getID()!).subscribe()
  }
}
