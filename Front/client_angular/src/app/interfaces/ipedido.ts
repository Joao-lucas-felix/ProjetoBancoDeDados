export interface IPedido {
    key: number | null,
    idUser: number | null,
    idLivro: number | null,
    data: string | null,
    funcionarioName: string | null
    quantidade: number | null, 
    links: {
        rel:{
            linkOfRelation: string
        }
    } | null
}
