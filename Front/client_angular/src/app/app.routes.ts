import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: "",
        title: "Home Page",
        loadComponent: 
            () => import('./pages/home/home.component')
                        .then(p => p.HomeComponent)
    },
    {
        path: "livro/:id",
        title: "Livro", 
        loadComponent: 
            () => import('./pages/livro-por-id/livro-por-id.component')
                            .then(p => p.LivroPorIdComponent)
    },

    {
        path: "livro/edit/:id",
        title: "Livro", 
        loadComponent: 
            () => import('./pages/edit-book/edit-book.component')
                            .then(p => p.EditBookComponent)
    },
    {
        path: "livro/delete/:id",
        title: "Livro", 
        loadComponent: 
            () => import('./pages/excluir-livro/excluir-livro.component')
                            .then(p => p.ExcluirLivroComponent)
    },
    {
        path: "clientes",
        title: "Clientes",
        loadComponent: 
            () => import('./pages/clientes/clientes.component')
                        .then(p => p.ClientesComponent)
    },
    {
        path: "pedido/:id",
        title: "pedidos",
        loadComponent: 
            () => import('./pages/pedidos/pedidos.component')
                        .then(p => p.PedidosComponent)
    },
    {
        path: "pedidos",
        title: "pedidos",
        loadComponent: 
            () => import('./pages/consultar-pedidos/consultar-pedidos.component')
                        .then(p => p.ConsultarPedidosComponent)
    },
    {
        path: "usuarios",
        title: "usuarios",
        loadComponent: 
            () => import('./pages/usuarios/usuarios.component')
                        .then(p => p.UsuariosComponent)
    },
    {
        path: "usuarios/:id",
        title: "usuarios",
        loadComponent: 
            () => import('./pages/pedidos-do-usuario/pedidos-do-usuario.component')
                        .then(p => p.PedidosDoUsuarioComponent)
    },
    {
        path: "usuarios/edit/:id",
        title: "usuarios",
        loadComponent: 
            () => import('./pages/edit-usuario/edit-usuario.component')
                        .then(p => p.EditUsuarioComponent)
    },
    {
        path: "funcionarios",
        title: "funcionarios",
        loadComponent: 
            () => import('./pages/consultar-funcionarios/consultar-funcionarios.component')
                        .then(p => p.ConsultarFuncionariosComponent)
    },
    {
        path: "funcionarios/:id",
        title: "funcionarios",
        loadComponent: 
            () => import('./pages/funcionario-por-id/funcionario-por-id.component')
                        .then(p => p.FuncionarioPorIdComponent)
    },
];
