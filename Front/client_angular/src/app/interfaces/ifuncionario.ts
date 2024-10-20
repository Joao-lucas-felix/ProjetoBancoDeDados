export interface Ifuncionario {
    key: number | null,
    nome: string | null | undefined,
    valorVendido:  number | null | undefined,
    links: {
        rel:{
            linkOfRelation: string
        }
    } | null
}
