import { Ifuncionario } from "./ifuncionario"

export interface IPagedFuncionario {
    _embedded: {
        funcionarioDtoList: Array<Ifuncionario>, 
     }
     _links: {
         self:{
             href: string
         }
     },
     page:{
         size: number,
         totalElement: number,
         totalPages: number, 
         number: number
     }
}
