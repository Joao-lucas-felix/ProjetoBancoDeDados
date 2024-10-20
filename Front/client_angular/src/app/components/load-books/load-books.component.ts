import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CreateBookPageComponent } from "../../pages/create-book-page/create-book-page.component";
import { CommonModule } from '@angular/common';
import { ListaLivrosComponent } from "../lista-livros/lista-livros.component";
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-load-books',
  standalone: true,
  imports: [CreateBookPageComponent, CommonModule, RouterLink, ListaLivrosComponent, ReactiveFormsModule],
  templateUrl: './load-books.component.html',
  styleUrl: './load-books.component.scss'
})
export class LoadBooksComponent {
  #service = inject(ApiService)
  #fb = inject(FormBuilder);

  public searchForm = this.#fb.group({
    titulo: ['']
  })

  criarLivro = signal<Boolean>(false)
  criarLivroText = signal<String>("Criar Livro")

  carregarCriarLivro() {
    this.criarLivro.set( !this.criarLivro() )
    if (!this.criarLivro()){
      this.criarLivroText.set("Criar Livro")
    
    }else{
      this.criarLivroText.set("Voltar")
    }
  }
  pesquisarLivro(){
    const titulo = this.searchForm.value.titulo!
    if( titulo!== ""){this.#service.httpSearchBookByTitulo$(titulo).subscribe()}
    else this.#service.httpListPagedBooks$().subscribe()
  }
}
