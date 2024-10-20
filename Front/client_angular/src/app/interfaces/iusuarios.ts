export interface IUsuarios {
    key: number | null,
    nome: string | null,
    cpf: string | null,
    data: string | null,
    end: string | null, 
    links: {
        rel:{
            linkOfRelation: string
        }
    } | null
}

