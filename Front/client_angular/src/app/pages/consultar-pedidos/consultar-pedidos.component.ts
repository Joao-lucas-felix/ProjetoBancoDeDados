import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-consultar-pedidos',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './consultar-pedidos.component.html',
  styleUrl: './consultar-pedidos.component.scss'
})
export class ConsultarPedidosComponent implements OnInit {
  #apiSerice = inject(ApiService)
  pageOfPedidos = this.#apiSerice.getPagedPedidos

  ngOnInit(): void {
    this.#apiSerice.httpListPagedPedidos$().subscribe()
    
  }
}
