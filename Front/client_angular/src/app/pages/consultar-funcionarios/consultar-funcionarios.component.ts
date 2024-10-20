import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-consultar-funcionarios',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './consultar-funcionarios.component.html',
  styleUrl: './consultar-funcionarios.component.scss'
})
export class ConsultarFuncionariosComponent implements OnInit{
  
  #apiSerice = inject(ApiService)
  pageOfFuncionarios = this.#apiSerice.getPagedFuncionarios

  #fb = inject(FormBuilder);

  public searchForm = this.#fb.group({
    nome: ['']
  })



  pesquisarFuncionario(){
    const nome = this.searchForm.value.nome!
    if( nome!== ""){this.#apiSerice.httpSearcherFuncionarioByname$(nome).subscribe()}
    else this.#apiSerice.httpListPagedFuncionarios$().subscribe()
  }

  ngOnInit(): void {
    this.#apiSerice.httpListPagedFuncionarios$().subscribe()
  }
}
