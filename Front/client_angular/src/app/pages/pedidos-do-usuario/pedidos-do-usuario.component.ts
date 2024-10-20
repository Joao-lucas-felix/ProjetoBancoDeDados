import { Component, inject, Input, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-pedidos-do-usuario',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './pedidos-do-usuario.component.html',
  styleUrl: './pedidos-do-usuario.component.scss'
})
export class PedidosDoUsuarioComponent implements OnInit{
  #apiSerice = inject(ApiService)
  usuario = this.#apiSerice.getUserByID
  pageOfPedidos = this.#apiSerice.getPagedPedidos
  //id vindo pela rota 
  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }

  ngOnInit(): void {
    this.#apiSerice.httpGetUserByID$(this.getID()!).subscribe()
    this.#apiSerice.httpListPagedPedidosDeUmUsuario$(this.getID()!).subscribe()
  }
}
