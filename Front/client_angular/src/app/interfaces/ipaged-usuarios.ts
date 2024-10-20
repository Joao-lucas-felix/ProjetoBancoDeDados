import { IUsuarios } from "./iusuarios"

export interface IPagedUsuarios {
    _embedded: {
        usuarioDtoList: Array<IUsuarios>, 
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
