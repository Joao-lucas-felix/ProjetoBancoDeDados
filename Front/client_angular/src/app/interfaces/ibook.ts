export default interface IBook{
    key: number | null,
    titulo: string | null | undefined,
    description: string | null | undefined, 
    preco:  number |string | null | undefined,
    dataLancamento: string | null | undefined,
    qtdEstoque: string | number | null | undefined,
    nomeEditora: string | number | null | undefined,
    autores: Array<string> | null | undefined,
    links: {
        rel:{
            linkOfRelation: string
        }
    } | null
}