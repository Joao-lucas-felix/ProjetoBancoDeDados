export default interface IpagedBook {
    _embedded: {
       livroDtoList: Array<IlivroDto>, 
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

interface IlivroDto{
    key: number,
    titulo: string,
    description: string, 
    preco: number,
    dataLancamento: string,
    qtdEstoque: number,
    nomeEditora: string, 
    autores: Array<string>
    links: {
        rel:{
            linkOfRelation: string
        }
    }
}
