package br.udesc.edu.demandatech.view;

import br.udesc.edu.demandatech.model.dto.criar.*;
import br.udesc.edu.demandatech.model.dto.editar.*;
import br.udesc.edu.demandatech.model.entity.*;
import br.udesc.edu.demandatech.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class DemandaConsoleView implements CommandLineRunner {

    private final PrioridadeService prioridadeService;
    private final StatusService statusService;
    private final DepartamentoService departamentoService;
    private final FuncionarioService funcionarioService;
    private final TipoDemandaService tipoDemandaService;
    private final DemandaService demandaService;
    private final EstornoDemandaService estornoDemandaService;
    private final FuncionarioEnvolvidoService funcionarioEnvolvidoService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        Funcionario usuarioLogado = telaLogin();

        int opcao;
        do {
            System.out.println("\n============================================");
            System.out.println("    DEMANDA TECH: Sistema de Gestão");
            System.out.println("    Usuário: " + usuarioLogado.getNome()
                    + (usuarioLogado.getAdmin() ? " [ADMIN]" : ""));
            System.out.println("============================================");
            System.out.println("1. Prioridades");
            System.out.println("2. Status");
            System.out.println("3. Departamentos");
            System.out.println("4. Funcionários");
            System.out.println("5. Tipos de Demanda");
            System.out.println("6. Demandas");
            System.out.println("7. Estornos de Demanda");
            System.out.println("8. Funcionários Envolvidos");
            System.out.println("9. Relatórios");
            System.out.println("0. Sair");
            System.out.println("============================================");
            System.out.print("Escolha: ");
            opcao = lerInt();
            try {
                switch (opcao) {
                    case 1 -> menuPrioridade(usuarioLogado);
                    case 2 -> menuStatus(usuarioLogado);
                    case 3 -> menuDepartamento(usuarioLogado);
                    case 4 -> menuFuncionario(usuarioLogado);
                    case 5 -> menuTipoDemanda(usuarioLogado);
                    case 6 -> menuDemanda(usuarioLogado);
                    case 7 -> menuEstorno(usuarioLogado);
                    case 8 -> menuFuncionarioEnvolvido();
                    case 9 -> menuRelatorios();
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (opcao != 0);
        System.exit(0);
    }

    // -------------------------------------------------------
    // TELA DE LOGIN
    // -------------------------------------------------------
    private Funcionario telaLogin() {
        while (true) {
            System.out.println("\n============================================");
            System.out.println("        DEMANDA TECH: Login");
            System.out.println("============================================");
            System.out.print("Matrícula: ");
            String matricula = lerString();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            try {
                Funcionario f = funcionarioService.login(matricula, senha);
                System.out.println("Bem-vindo(a), " + f.getNome() + "!");
                return f;
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage() + " Tente novamente.\n");
            }
        }
    }

    // -------------------------------------------------------
    // MENUS
    // -------------------------------------------------------
    private void menuPrioridade(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- PRIORIDADES ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Descrição: ");
                        String d = scanner.nextLine();
                        Prioridade p = prioridadeService.criar(new PrioridadeCriarDTO(d), usuario);
                        System.out.println("Criado: ID=" + p.getIdPrioridade());
                    }
                    case 2 -> prioridadeService.buscarTudo().forEach(
                            p -> System.out.println("ID: " + p.getIdPrioridade() + " | " + p.getDescricao()));
                    case 3 -> {
                        System.out.print("ID: ");
                        Prioridade p = prioridadeService.buscarPorId(lerString());
                        System.out.println("ID: " + p.getIdPrioridade() + " | " + p.getDescricao());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        String id = lerString();
                        System.out.print("Nova descrição: ");
                        prioridadeService.atualizar(id, new PrioridadeEditarDTO(scanner.nextLine()), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        prioridadeService.removerPorId(lerString(), usuario);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuStatus(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- STATUS ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Descrição: ");
                        Status s = statusService.criar(new StatusCriarDTO(scanner.nextLine()), usuario);
                        System.out.println("Criado: ID=" + s.getIdStatus());
                    }
                    case 2 -> statusService.buscarTudo().forEach(
                            s -> System.out.println("ID: " + s.getIdStatus() + " | " + s.getDescricao()));
                    case 3 -> {
                        System.out.print("ID: ");
                        Status s = statusService.buscarPorId(lerString());
                        System.out.println("ID: " + s.getIdStatus() + " | " + s.getDescricao());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        String id = lerString();
                        System.out.print("Nova descrição: ");
                        statusService.atualizar(id, new StatusEditarDTO(scanner.nextLine()), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        statusService.removerPorId(lerString(), usuario);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuDepartamento(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- DEPARTAMENTOS ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Descrição: ");
                        String d = scanner.nextLine();
                        System.out.print("Matrícula do gerente (vazio=sem gerente): ");
                        String gId = lerString();
                        Funcionario ger = !gId.isBlank() ? funcionarioService.buscarPorId(gId) : null;
                        Departamento dep = departamentoService.criar(new DepartamentoCriarDTO(d, ger), usuario);
                        System.out.println("Criado: ID=" + dep.getIdDepartamento());
                    }
                    case 2 -> departamentoService.buscarTudo().forEach(d -> System.out.println(
                            "ID: " + d.getIdDepartamento() + " | " + d.getDescricao()
                                    + " | Gerente: " + (d.getGerente() != null ? d.getGerente().getNome() : "N/A")));
                    case 3 -> {
                        System.out.print("ID: ");
                        Departamento d = departamentoService.buscarPorId(lerString());
                        System.out.println("ID: " + d.getIdDepartamento() + " | " + d.getDescricao()
                                + " | Gerente: " + (d.getGerente() != null ? d.getGerente().getNome() : "N/A"));
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        String id = lerString();
                        System.out.print("Nova descrição: ");
                        String d = scanner.nextLine();
                        System.out.print("Matrícula do gerente (vazio=sem gerente): ");
                        String gId = lerString();
                        Funcionario ger = !gId.isBlank() ? funcionarioService.buscarPorId(gId) : null;
                        departamentoService.atualizar(id, new DepartamentoEditarDTO(d, ger), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        departamentoService.removerPorId(lerString(), usuario);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuFuncionario(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- FUNCIONÁRIOS ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();
                        System.out.print("ID Departamento: ");
                        Departamento dep = departamentoService.buscarPorId(lerString());
                        System.out.print("Admin (true/false): ");
                        String adminInput = scanner.nextLine().trim();
                        boolean admin = "true".equalsIgnoreCase(adminInput);
                        Funcionario f = funcionarioService.criar(
                                new FuncionarioCriarDTO(nome, email, senha, dep, admin), usuario);
                        System.out.println("Criado: Matrícula=" + f.getMatricula());
                    }
                    case 2 -> funcionarioService.buscarTudo().forEach(f -> System.out.println(
                            "Matrícula: " + f.getMatricula()
                                    + " | " + f.getNome() + " | " + f.getEmail()
                                    + " | Departamento: "
                                    + (f.getDepartamento() != null ? f.getDepartamento().getDescricao() : "N/A")
                                    + " | Admin: " + f.getAdmin()));
                    case 3 -> {
                        System.out.print("Matrícula: ");
                        Funcionario f = funcionarioService.buscarPorId(lerString());
                        System.out.println("Matrícula: " + f.getMatricula() + " | " + f.getNome() + " | "
                                + f.getEmail() + " | Departamento: "
                                + (f.getDepartamento() != null ? f.getDepartamento().getDescricao() : "N/A")
                                + " | Admin: " + f.getAdmin());
                    }
                    case 4 -> {
                        System.out.print("Matrícula: ");
                        String id = lerString();
                        System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Nova senha (deixe em branco para manter): ");
                        String senha = scanner.nextLine().trim();
                        System.out.print("ID Departamento: ");
                        Departamento dep = departamentoService.buscarPorId(lerString());
                        System.out.print("Admin (true/false): ");
                        boolean admin = "true".equalsIgnoreCase(scanner.nextLine().trim());
                        if (senha.isBlank()) {
                            senha = funcionarioService.buscarPorId(id).getSenha();
                        }
                        funcionarioService.atualizar(id, new FuncionarioEditarDTO(nome, email, senha, dep, admin),
                                usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("Matrícula: ");
                        funcionarioService.removerPorId(lerString(), usuario);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuTipoDemanda(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- TIPOS DE DEMANDA ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Descrição: ");
                        String d = scanner.nextLine();
                        System.out.print("ID Departamento: ");
                        Departamento dep = departamentoService.buscarPorId(lerString());
                        TipoDemanda t = tipoDemandaService.criar(new TipoDemandaCriarDTO(d, dep), usuario);
                        System.out.println("Criado: ID=" + t.getIdTipo());
                    }
                    case 2 -> tipoDemandaService.buscarTudo().forEach(t -> System.out.println(
                            "ID=" + t.getIdTipo() + " | " + t.getDescricao()
                                    + " | Departamento: "
                                    + (t.getDepartamento() != null ? t.getDepartamento().getDescricao() : "N/A")));
                    case 3 -> {
                        System.out.print("ID: ");
                        TipoDemanda t = tipoDemandaService.buscarPorId(lerString());
                        System.out.println("ID=" + t.getIdTipo() + " | " + t.getDescricao());
                    }
                    case 4 -> {
                        System.out.print("ID: ");
                        String id = lerString();
                        System.out.print("Descrição: ");
                        String d = scanner.nextLine();
                        System.out.print("ID Departamento: ");
                        Departamento dep = departamentoService.buscarPorId(lerString());
                        tipoDemandaService.atualizar(id, new TipoDemandaEditarDTO(d, dep), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        tipoDemandaService.removerPorId(lerString(), usuario);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuDemanda(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- DEMANDAS ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Título (max 30): ");
                        String titulo = scanner.nextLine();
                        System.out.print("Descrição (max 250): ");
                        String desc = scanner.nextLine();
                        System.out.print("ID Prioridade: ");
                        Prioridade pri = prioridadeService.buscarPorId(lerString());
                        System.out.print("ID Tipo Demanda: ");
                        TipoDemanda tipo = tipoDemandaService.buscarPorId(lerString());
                        System.out.print("Matrículas envolvidos (vírgula, ou vazio): ");
                        String envStr = scanner.nextLine();
                        List<Funcionario> envolvidos = new ArrayList<>();
                        if (!envStr.isBlank()) {
                            for (String m : envStr.split(","))
                                envolvidos.add(funcionarioService.buscarPorId(m.trim()));
                        }
                        Demanda dem = demandaService.criar(
                                new DemandaCriarDTO(titulo, desc, pri, tipo, usuario, envolvidos));
                        System.out.println("Demanda criada: ID: " + dem.getIdDemanda()
                                + " | Responsável: "
                                + (dem.getResponsavel() != null ? dem.getResponsavel().getNome() : "auto"));
                    }
                    case 2 -> demandaService.buscarTudo().forEach(d -> System.out.println(
                            "ID:" + d.getIdDemanda()
                                    + " | " + d.getTitulo()
                                    + " | Status:" + (d.getStatus() != null ? d.getStatus().getDescricao() : "N/A")
                                    + " | Responsável:"
                                    + (d.getResponsavel() != null ? d.getResponsavel().getNome() : "N/A")));
                    case 3 -> {
                        System.out.print("ID: ");
                        Demanda d = demandaService.buscarPorId(lerString());
                        System.out.println(
                                "\n| ID: " + d.getIdDemanda()
                                        + "\n| Título: " + d.getTitulo()
                                        + "\n| Descrição: " + d.getDescricao()
                                        + "\n| Data: " + d.getData() + " " + d.getHora()
                                        + "\n| Status: "
                                        + (d.getStatus() != null ? d.getStatus().getDescricao() : "N/A")
                                        + "\n| Prioridade: "
                                        + (d.getPrioridade() != null ? d.getPrioridade().getDescricao() : "N/A")
                                        + "\n| Responsável: "
                                        + (d.getResponsavel() != null ? d.getResponsavel().getNome() : "N/A")
                                        + "\n| Criador: " + (d.getCriador() != null ? d.getCriador().getNome() : "N/A")
                                        + "\n| Envolvidos: "
                                        + (d.getMatriculasEnvolvidos() != null ? d.getMatriculasEnvolvidos() : "[]"));
                    }
                    case 4 -> {
                        System.out.print("ID demanda: ");
                        String id = lerString();
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.print("ID Prioridade: ");
                        Prioridade pri = prioridadeService.buscarPorId(lerString());
                        System.out.print("ID Tipo: ");
                        TipoDemanda tipo = tipoDemandaService.buscarPorId(lerString());
                        demandaService.atualizar(id, new DemandaEditarDTO(titulo, desc, pri, tipo), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID demanda: ");
                        demandaService.removerPorId(usuario, lerString());
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuEstorno(Funcionario usuario) {
        int op;
        do {
            System.out.println("\n--- ESTORNOS DE DEMANDA ---");
            System.out.println("1.Cadastrar\n2.Listar\n3.Buscar\n4.Atualizar\n5.Remover\n0.Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        System.out.print("ID Demanda: ");
                        Demanda dem = demandaService.buscarPorId(lerString());
                        EstornoDemanda e = estornoDemandaService.criar(new EstornoDemandaCriarDTO(desc, dem));
                        System.out.println("Estorno criado: ID=" + e.getIdEstorno());
                    }
                    case 2 -> estornoDemandaService.buscarTudo().forEach(e -> System.out.println(
                            "ID: " + e.getIdEstorno()
                                    + " | " + e.getDescricao()
                                    + " | Data: " + e.getData()
                                    + " | Demanda: "
                                    + (e.getDemanda() != null ? e.getDemanda().getIdDemanda() : "N/A")));
                    case 3 -> {
                        System.out.print("ID: ");
                        EstornoDemanda e = estornoDemandaService.buscarPorId(lerString());
                        System.out.println("ID: " + e.getIdEstorno()
                                + " | Descrição: " + e.getDescricao()
                                + " | Data: " + e.getData()
                                + " | Demanda: " + (e.getDemanda() != null ? e.getDemanda().getIdDemanda() : "N/A"));
                    }
                    case 4 -> {
                        System.out.print("ID estorno: ");
                        String id = lerString();
                        System.out.print("Descrição: ");
                        String desc = scanner.nextLine();
                        estornoDemandaService.atualizar(id, new EstornoDemandaEditarDTO(desc), usuario);
                        System.out.println("Atualizado!");
                    }
                    case 5 -> {
                        System.out.print("ID: ");
                        estornoDemandaService.removerPorId(lerString());
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuFuncionarioEnvolvido() {
        int op;
        do {
            System.out.println("\n--- FUNCIONÁRIOS ENVOLVIDOS ---");
            System.out.println("1. Associar\n2. Listar por demanda\n3. Remover\n0. Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Matrícula funcionário: ");
                        Funcionario func = funcionarioService.buscarPorId(lerString());
                        System.out.print("ID demanda: ");
                        Demanda dem = demandaService.buscarPorId(lerString());
                        funcionarioEnvolvidoService.associar(func, dem);
                        System.out.println("Associado: " + func.getNome() + " -> " + dem.getTitulo());
                    }
                    case 2 -> {
                        System.out.print("ID demanda: ");
                        Demanda dem = demandaService.buscarPorId(lerString());
                        funcionarioEnvolvidoService.buscarPorDemanda(dem).forEach(
                                f -> System.out.println("Matrícula: " + f.getMatricula() + " | " + f.getNome()));
                    }
                    case 3 -> {
                        System.out.print("Matrícula funcionário: ");
                        Funcionario func = funcionarioService.buscarPorId(lerString());
                        System.out.print("ID demanda: ");
                        Demanda dem = demandaService.buscarPorId(lerString());
                        funcionarioEnvolvidoService.remover(func, dem);
                        System.out.println("Removido!");
                    }
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private void menuRelatorios() {
        int op;
        do {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println(
                    "1. Demandas por Departamento\n2. Top 10 Funcionários mais Produtivos\n3. Estornos por Responsável\n0. Voltar");
            op = lerInt();
            try {
                switch (op) {
                    case 1 -> System.out.println(demandaService.relatorioQtdDemandasPorDepartamento());
                    case 2 -> System.out.println(demandaService.relatorioDezFuncionariosMaisProdutivos());
                    case 3 -> System.out.println(estornoDemandaService.relatorioQtdEstornosPorResponsavel());
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        } while (op != 0);
    }

    private int lerInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private String lerString() {
        return scanner.nextLine().trim();
    }
}
