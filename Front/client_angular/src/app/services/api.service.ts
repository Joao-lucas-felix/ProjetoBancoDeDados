import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable, shareReplay, tap } from 'rxjs';
import IpagedBook from '../interfaces/ipaged-book';
import IBook from '../interfaces/ibook';
import { IPedido } from '../interfaces/ipedido';
import { IpagedPedidos } from '../interfaces/ipaged-pedidos';
import { IPedidoFeito } from '../interfaces/ipedido-feito';
import { IPagedUsuarios } from '../interfaces/ipaged-usuarios';
import { IUsuarios } from '../interfaces/iusuarios';
import { IPagedFuncionario } from '../interfaces/ipaged-funcionario';
import { Ifuncionario } from '../interfaces/ifuncionario';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  #http = inject(HttpClient)
  #url = signal(environment.apiBaseUrl)
  #endpointLivro = signal("/api/livro/v1")
  #endpointPedido = signal("/api/pedido/v1")
  #endpointUsuario = signal("/api/usuario/v1")
  #endpointFuncionarios = signal("/api/funcionario/v1")
  #setPagedBooks = signal<IpagedBook | null>(null)
  get getPagedBooks() {
    return this.#setPagedBooks.asReadonly()
  }

  public httpListPagedBooks$(): Observable<IpagedBook> {
    this.#setPagedBooks.set(null)
    return this.#http.get<IpagedBook>
      (`${this.#url()}${this.#endpointLivro()}`)
      .pipe(
        shareReplay(),
        tap(
          (response) => this.#setPagedBooks.set(response)
        )
      )
  }

  public httpSearchBookByTitulo$(titulo: string): Observable<IpagedBook> {
    this.#setPagedBooks.set(null)
    return this.#http.get<IpagedBook> (
      `${this.#url()}${this.#endpointLivro()}/findLivroByTitulo/${titulo}`
    ).pipe(
      shareReplay(), 
      tap(
        (response) => this.#setPagedBooks.set(response)
      )
    )
  }
  #setBookById = signal<IBook | null>(null)
  get getBookByID() {
    return this.#setBookById.asReadonly()
  }
  public httpBookByID$(id: string): Observable<IBook> {
    this.#setBookById.set(null)
    console.log(`${this.#url()}${this.#endpointLivro()}/${id}`)
    return this.#http.get<IBook>(`${this.#url()}${this.#endpointLivro()}/${id}`).pipe(
      shareReplay(),
      tap(
        (response) => {
          this.#setBookById.set(response)
        }
      )
    )
  }

  #setCreateBook = signal<IBook | null>(null)
  get getBookCreated() {
    return this.#setCreateBook.asReadonly()
  }
  public httpBookCreate$(book:
    {
      titulo: string | null | undefined,
      description: string | null | undefined,
      preco: number | string | null | undefined,
      dataLancamento: string | null | undefined,
      qtdEstoque: string | number | null | undefined,
    })
    : Observable<IBook> {
    console.log(book)
    return this.#http.post<IBook>(`${this.#url()}${this.#endpointLivro()}`, book).pipe(
      shareReplay(),
      tap((response) => this.#setCreateBook.set(response))
    )
  }

  public httpBookUpdate$(book:
    {
      key: string
      titulo: string | null | undefined,
      description: string | null | undefined,
      preco: number | string | null | undefined,
      dataLancamento: string | null | undefined,
      qtdEstoque: string | number | null | undefined,
    })
    : Observable<IBook> {
    console.log(book)
    return this.#http.put<IBook>(`${this.#url()}${this.#endpointLivro()}`, book).pipe(
      shareReplay(),
      tap((response) => this.#setBookById.set(response))
    )
  }

  public httpBookDelete$(id: string) {
    return this.#http.delete(`${this.#url()}${this.#endpointLivro()}/${id}`).pipe(
      shareReplay(),
    )
  }



  // Pedidos: 


  #setCreatePedido = signal<IPedido | null>(null)
  get getPedidoCreated() {
    return this.#setCreatePedido.asReadonly()
  }
  public httpPedidoCreate$(pedido: {
    idLivro: string | null | undefined,
    userName: string | null | undefined,
    cpf: string | null | undefined,
    end: string | null | undefined,
    quantidade: number | string | null | undefined,
    funcionarioName: string | null | undefined, 
    data: string | null | undefined
  })
    : Observable<IPedidoFeito> {
    this.#setCreatePedido.set(null)
    return this.#http.post<IPedidoFeito>(`${this.#url()}${this.#endpointPedido()}`, pedido).pipe(
      shareReplay(),
      tap((response) => this.#setCreatePedido.set(response))
    )
  }
  #setPagedPedidos = signal<IpagedPedidos | null>(null)
  get getPagedPedidos() {
    return this.#setPagedPedidos.asReadonly()
  }

  // Método assíncrono que se comunica com API e faz o Get ALL 
  public httpListPagedPedidos$(): Observable<IpagedPedidos> {
    this.#setPagedPedidos.set(null)
    return this.#http.get<IpagedPedidos>
      (`${this.#url()}${this.#endpointPedido()}`)
      .pipe(
        shareReplay(),
        tap(
          // setando os dados da resposta HTTP no signal
          (response) => this.#setPagedPedidos.set(response)
        )
      )
  }

  public httpListPagedPedidosDeUmUsuario$(id: string): Observable<IpagedPedidos> {
    this.#setPagedPedidos.set(null)
    return this.#http.get<IpagedPedidos>
      (`${this.#url()}${this.#endpointPedido()}/PedidoUsuario/${id}`)
      .pipe(
        shareReplay(),
        tap(
          (response) => this.#setPagedPedidos.set(response)
        )
      )
  }

  public httpListPagedPedidosDeUmFuncionario$(id: string): Observable<IpagedPedidos> {
    this.#setPagedPedidos.set(null)
    return this.#http.get<IpagedPedidos>
      (`${this.#url()}${this.#endpointPedido()}/PedidoFuncionario/${id}`)
      .pipe(
        shareReplay(),
        tap(
          (response) => this.#setPagedPedidos.set(response)
        )
      )
  }





  //Usuarios 

  #setPagedUsuarios = signal<IPagedUsuarios | null>(null)
  get getPagedUsuarios() {
    return this.#setPagedUsuarios.asReadonly()
  }

  public httpListPagedUsuarios$(): Observable<IPagedUsuarios> {
    this.#setPagedUsuarios.set(null)
    return this.#http.get<IPagedUsuarios>
      (`${this.#url()}${this.#endpointUsuario()}`)
      .pipe(
        shareReplay(),
        tap(
          (response) => this.#setPagedUsuarios.set(response)
        )
      )
  }

  public httpSearcherUserByname$(name: string): Observable<IPagedUsuarios> {
    this.#setPagedUsuarios.set(null)
    return this.#http.get<IPagedUsuarios> (
      `${this.#url()}${this.#endpointUsuario()}/findUsuarioByName/${name}`
    ).pipe(
      shareReplay(), 
      tap(
        (response) => this.#setPagedUsuarios.set(response)
      )
    )
  }

  #setUserById = signal<IUsuarios | null>(null)
  get getUserByID() {
    return this.#setUserById.asReadonly()
  }
  public httpGetUserByID$(id: string): Observable<IUsuarios> {
    this.#setUserById.set(null)
    console.log(`${this.#url()}${this.#endpointLivro()}/${id}`)
    return this.#http.get<IUsuarios>(`${this.#url()}${this.#endpointUsuario()}/${id}`).pipe(
      shareReplay(),
      tap(
        (response) => {
          this.#setUserById.set(response)
        }
      )
    )
  }

  public htppUpdateUser$(user:
    {
      key: string
      nome: string | null | undefined,
      cpf: string | null | undefined,
      end: string | string | null | undefined,
    })
    : Observable<IUsuarios> {
    return this.#http.put<IUsuarios>(`${this.#url()}${this.#endpointUsuario()}`, user).pipe(
      shareReplay(),
      tap((response) => this.#setUserById.set(response))
    )
  }




  //funcionarios 
  #setPagedFuncionarios  = signal<IPagedFuncionario | null>(null)
  get getPagedFuncionarios() {
    return this.#setPagedFuncionarios.asReadonly()
  }

  public httpListPagedFuncionarios$(): Observable<IPagedFuncionario> {
    this.#setPagedFuncionarios.set(null)
    return this.#http.get<IPagedFuncionario>
      (`${this.#url()}${this.#endpointFuncionarios()}`)
      .pipe(
        shareReplay(),
        tap(
          (response) => this.#setPagedFuncionarios.set(response)
        )
      )
  }

  public httpSearcherFuncionarioByname$(name: string): Observable<IPagedFuncionario> {
    this.#setPagedFuncionarios.set(null)
    return this.#http.get<IPagedFuncionario> (
      `${this.#url()}${this.#endpointFuncionarios()}/findFuncionarioByName/${name}`
    ).pipe(
      shareReplay(), 
      tap(
        (response) => this.#setPagedFuncionarios.set(response)
      )
    )
  }
  

  #setFuncionarioById = signal<Ifuncionario | null>(null)
  get getFuncionarioByID() {
    return this.#setFuncionarioById.asReadonly()
  }
  public httpGetFuncionarioByID$(id: string): Observable<Ifuncionario> {
    this.#setFuncionarioById.set(null)
    return this.#http.get<Ifuncionario>(`${this.#url()}${this.#endpointFuncionarios()}/${id}`).pipe(
      shareReplay(),
      tap(
        (response) => {
          this.#setFuncionarioById.set(response)
        }
      )
    )
  }

}

