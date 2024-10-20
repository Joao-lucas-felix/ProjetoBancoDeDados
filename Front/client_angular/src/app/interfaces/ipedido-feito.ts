export interface IPedidoFeito {
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
