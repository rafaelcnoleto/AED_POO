import java.util.Scanner;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static BaseDeDados db = BaseDeDados.getInstancia();
    private static Pessoa usuarioLogado = null;

    public static void main(String[] args) {
        System.out.println("=== SISTEMA ACADÊMICO SOL (CLI) ===");

        boolean rodando = true;
        while (rodando) {
            if (usuarioLogado == null) {
                fazerLogin();
            } else {
                rodando = exibirMenuPrincipal();
            }
        }
        System.out.println("Sistema encerrado.");
    }

    // --- LOGIN ---
    private static void fazerLogin() {
        System.out.println("\n--- TELA DE ACESSO ---");
        System.out.println("1. Administrativo");
        System.out.println("2. Professor");
        System.out.println("3. Aluno");
        System.out.println("0. Sair");
        System.out.print("Opção: ");

        int op = lerInteiro();
        if (op == 0) System.exit(0);

        switch (op) {
            case 1: loginAdmin(); break;
            case 2: loginProfessor(); break;
            case 3: loginAluno(); break;
            default: System.out.println("Inválido.");
        }
    }

    private static void loginAdmin() {
        System.out.print("Usuário: ");
        String user = scanner.nextLine();
        System.out.print("Senha: ");
        String pass = scanner.nextLine();

        for (Administrativo adm : db.getAdmins()) {
            if (adm.autenticar(user, pass)) {
                usuarioLogado = adm;
                System.out.println(">> Bem-vindo " + adm.getNome());
                return;
            }
        }
        System.out.println(">> Credenciais incorretas.");
    }

    private static void loginProfessor() {
        System.out.print("Matrícula ou Nome para Acesso: ");
        String id = scanner.nextLine();
        // Simulação de login
        usuarioLogado = new Professor(id, id, "prof@sol.edu", "Visitante", true);
        System.out.println(">> Acesso Professor: " + id);
    }

    private static void loginAluno() {
        System.out.print("Matrícula ou Nome para Acesso: ");
        String id = scanner.nextLine();
        // Simulação de login
        usuarioLogado = new Aluno(id, id, "aluno@sol.edu", "Visitante", true);
        System.out.println(">> Acesso Aluno: " + id);
    }

    // --- MENUS PRINCIPAIS ---
    private static boolean exibirMenuPrincipal() {
        System.out.println("\n--- MENU (" + usuarioLogado.getClass().getSimpleName() + ") ---");

        if (usuarioLogado instanceof Administrativo) {
            System.out.println("1. Cadastrar Novo Aluno");
            System.out.println("2. Cadastrar Novo Professor");
            System.out.println("3. Criar Turma");
            System.out.println("4. Matricular Aluno em Turma");
            System.out.println("5. Atribuir Professor a Turma");
            System.out.println("6. Relatórios (Listar Tudo)");
            System.out.println("7. Remover Aluno");
        } else {
            System.out.println("1. Consultar Turmas");
            System.out.println("2. Meus Dados");
        }
        System.out.println("0. Logout");
        System.out.print("Escolha: ");

        int op = lerInteiro();
        if (op == 0) { usuarioLogado = null; return true; }

        if (usuarioLogado instanceof Administrativo) {
            tratarOpcaoAdmin(op);
        } else {
            tratarOpcaoGenerica(op);
        }
        return true;
    }

    private static void tratarOpcaoAdmin(int op) {
        switch (op) {
            case 1: cadastrarAluno(); break;
            case 2: cadastrarProfessor(); break;
            case 3: criarTurma(); break;
            case 4: matricularAlunoEmTurma(); break;
            case 5: atribuirProfessorTurma(); break;
            case 6: gerarRelatorios(); break;
            case 7: removerAluno(); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void tratarOpcaoGenerica(int op) {
        switch (op) {
            case 1: db.getTurmas().forEach(Turma::listarTurma); break;
            case 2: usuarioLogado.exibirDados(); break;
            default: System.out.println("Opção inválida.");
        }
    }

    // --- FUNCIONALIDADES DO ADMIN ---

    private static void cadastrarAluno() {
        System.out.println("\n-- CADASTRO DE ALUNO --");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Curso: "); String curso = scanner.nextLine();

        Aluno a = new Aluno(nome, cpf, nome.toLowerCase()+"@sol.edu", curso);
        db.adicionarAluno(a);

        System.out.println(">> Aluno cadastrado com Sucesso!");
        System.out.println(">> MATRÍCULA GERADA: " + a.getMatricula());
    }

    private static void cadastrarProfessor() {
        System.out.println("\n-- CADASTRO DE PROFESSOR --");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Titulação: "); String tit = scanner.nextLine();

        Professor p = new Professor(nome, cpf, nome.toLowerCase()+"@sol.edu", tit);
        db.adicionarProfessor(p);

        System.out.println(">> Professor cadastrado com Sucesso!");
        System.out.println(">> ID FUNCIONAL: " + p.getMatriculaFuncional());
    }

    private static void criarTurma() {
        if (db.getDisciplinas().isEmpty()) {
            System.out.println(">> Erro: Nenhuma disciplina cadastrada.");
            return;
        }
        System.out.println("\n-- NOVA TURMA --");
        listarDisciplinas();
        System.out.print("ID da Disciplina: ");
        int idx = lerInteiro();

        if (idx >= 0 && idx < db.getDisciplinas().size()) {
            System.out.print("Código da Turma (ex: 2025-A): ");
            String cod = scanner.nextLine();
            Turma t = new Turma(cod, 2025, db.getDisciplinas().get(idx));
            db.adicionarTurma(t);
            System.out.println(">> Turma criada!");
        }
    }

    private static void matricularAlunoEmTurma() {
        if (db.getAlunos().isEmpty() || db.getTurmas().isEmpty()) {
            System.out.println(">> Erro: Precisa haver alunos e turmas cadastrados.");
            return;
        }
        System.out.println("\n-- MATRICULAR ALUNO EM TURMA --");

        // 1. Selecionar Aluno
        System.out.println("Selecione o Aluno:");
        List<Aluno> alunos = db.getAlunos();
        for (int i = 0; i < alunos.size(); i++) {
            System.out.println("[" + i + "] " + alunos.get(i).getNome() + " (Mat: " + alunos.get(i).getMatricula() + ")");
        }
        System.out.print("Índice do Aluno: ");
        int idxAluno = lerInteiro();

        // 2. Selecionar Turma
        System.out.println("Selecione a Turma:");
        List<Turma> turmas = db.getTurmas();
        for (int i = 0; i < turmas.size(); i++) {
            System.out.println("[" + i + "] Turma " + turmas.get(i).getCodigoTurma() + " - " + turmas.get(i).getDisciplina().getNome());
        }
        System.out.print("Índice da Turma: ");
        int idxTurma = lerInteiro();

        if (idxAluno >= 0 && idxAluno < alunos.size() && idxTurma >= 0 && idxTurma < turmas.size()) {
            Turma t = turmas.get(idxTurma);
            Aluno a = alunos.get(idxAluno);

            t.adicionarAluno(a);
            System.out.println(">> Sucesso: " + a.getNome() + " matriculado na turma " + t.getCodigoTurma());
        } else {
            System.out.println(">> Índices inválidos.");
        }
    }

    private static void atribuirProfessorTurma() {
        if (db.getProfessores().isEmpty() || db.getTurmas().isEmpty()) {
            System.out.println(">> Erro: Precisa haver professores e turmas cadastrados.");
            return;
        }
        System.out.println("\n-- ATRIBUIR PROFESSOR --");

        // 1. Selecionar Professor
        System.out.println("Selecione o Professor:");
        List<Professor> profs = db.getProfessores();
        for (int i = 0; i < profs.size(); i++) {
            System.out.println("[" + i + "] " + profs.get(i).getNome());
        }
        System.out.print("Índice do Professor: ");
        int idxProf = lerInteiro();

        // 2. Selecionar Turma
        System.out.println("Selecione a Turma:");
        List<Turma> turmas = db.getTurmas();
        for (int i = 0; i < turmas.size(); i++) {
            Turma t = turmas.get(i);
            String atual = (t.getProfessorResponsavel() != null) ? t.getProfessorResponsavel().getNome() : "VAGO";
            System.out.println("[" + i + "] " + t.getCodigoTurma() + " (Atual: " + atual + ")");
        }
        System.out.print("Índice da Turma: ");
        int idxTurma = lerInteiro();

        if (idxProf >= 0 && idxProf < profs.size() && idxTurma >= 0 && idxTurma < turmas.size()) {
            Turma t = turmas.get(idxTurma);
            Professor p = profs.get(idxProf);

            t.setProfessorResponsavel(p); // Vincula na Turma
            p.adicionarTurma(t);          // Vincula no Professor

            System.out.println(">> Sucesso: Professor " + p.getNome() + " atribuído à turma " + t.getCodigoTurma());
        } else {
            System.out.println(">> Índices inválidos.");
        }
    }

    private static void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIO GERAL ---");
        System.out.println("\n> ALUNOS:");
        db.getAlunos().forEach(Pessoa::exibirDados);

        System.out.println("\n> PROFESSORES:");
        db.getProfessores().forEach(Pessoa::exibirDados);

        System.out.println("\n> TURMAS:");
        db.getTurmas().forEach(Turma::listarTurma);
    }

    private static void removerAluno() {
        System.out.print("Digite a matrícula para remover: ");
        String mat = scanner.nextLine();
        db.removerAluno(mat);
        System.out.println(">> Processo finalizado.");
    }

    private static void listarDisciplinas() {
        List<Disciplina> lista = db.getDisciplinas();
        for(int i=0; i<lista.size(); i++) {
            System.out.println("["+i+"] " + lista.get(i));
        }
    }

    private static int lerInteiro() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }
}