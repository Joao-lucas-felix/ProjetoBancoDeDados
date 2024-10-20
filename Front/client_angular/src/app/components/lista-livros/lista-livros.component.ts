import { Component, inject, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common'
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-lista-livros',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './lista-livros.component.html',
  styleUrl: './lista-livros.component.scss'
})
export class ListaLivrosComponent implements OnInit  {
  #apiSerice = inject(ApiService)
  pageOfBooks = this.#apiSerice.getPagedBooks

  ngOnInit(): void {
    this.#apiSerice.httpListPagedBooks$().subscribe()
  }
}
