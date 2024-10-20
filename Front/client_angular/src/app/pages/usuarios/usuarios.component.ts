import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [RouterLink, ReactiveFormsModule],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.scss'
})
export class UsuariosComponent implements OnInit{
  #apiSerice = inject(ApiService)
  pageOfUsuarios = this.#apiSerice.getPagedUsuarios
  #fb = inject(FormBuilder);

  public searchForm = this.#fb.group({
    nome: ['']
  })

  public pesquisarUsuario(){
    const nome = this.searchForm.value.nome!
    if( nome!== ""){this.#apiSerice.httpSearcherUserByname$(nome).subscribe()}
    else this.#apiSerice.httpListPagedUsuarios$().subscribe()
  }
  ngOnInit(): void {
    this.#apiSerice.httpListPagedUsuarios$().subscribe()
  }
}
