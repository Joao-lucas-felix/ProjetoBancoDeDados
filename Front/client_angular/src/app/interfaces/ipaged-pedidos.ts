export interface IpagedPedidos {
    _embedded: {
        pedidoDtoList: Array<IpedidoDto>, 
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
 
 interface IpedidoDto{
     key: number,
     idUser: number,
     idLivro: number, 
     data: string,
     quantidade: number,
     nomeUsuario: string, 
     tituloLivro: string,
     funcionarioName: string, 
     links: {
         rel:{
             linkOfRelation: string
         }
     }
}
