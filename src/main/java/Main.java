// Arquivo: Main.java
import java.util.List;

public class Main {
    private static BaseDeDados db = BaseDeDados.getInstancia();
    private static Pessoa usuarioLogado = null;

    public static void main(String[] args) {
        System.out.println("=== SISTEMA ACADÊMICO SOL (Cenário Real) ===");

        while (true) {
            try {
                if (usuarioLogado == null) {
                    telaLogin();
                } else {
                    telaPrincipal();
                }
            } catch (Exception e) {
                System.out.println("\n!!! ERRO INESPERADO: " + e.getMessage());
                usuarioLogado = null;
            }
        }
    }

    // --- FLUXO DE LOGIN ---
    private static void telaLogin() {
        System.out.println("\n--- ACESSO RESTRITO ---");
        System.out.println("1. Administrativo");
        System.out.println("2. Professor");
        System.out.println("3. Aluno");
        System.out.println("0. Encerrar");

        int op = ConsoleUtils.lerInteiro("Selecione o perfil");
        if (op == 0) System.exit(0);

        try {
            switch (op) {
                case 1: loginAdmin(); break;
                case 2: loginProf(); break;
                case 3: loginAluno(); break;
                default: System.out.println(">> Opção inválida.");
            }
        } catch (SistemaAcademicoException e) {
            System.out.println("\n>> FALHA DE LOGIN: " + e.getMessage());
        }
    }

    private static void loginAdmin() throws SistemaAcademicoException {
        String user = ConsoleUtils.lerTexto("Usuário");
        String pass = ConsoleUtils.lerTexto("Senha");

        for (Administrativo adm : db.getAdmins()) {
            if (adm.autenticar(user, pass)) {
                usuarioLogado = adm;
                System.out.println("Bem-vindo, " + adm.getNome());
                return;
            }
        }
        throw new SistemaAcademicoException("Credenciais administrativas inválidas.");
    }

    private static void loginProf() throws SistemaAcademicoException {
        String mat = ConsoleUtils.lerTexto("Matrícula Funcional (Ex: DOC-101)");
        usuarioLogado = db.buscarProfessor(mat);
        System.out.println("Bem-vindo Docente " + usuarioLogado.getNome());
    }

    private static void loginAluno() throws SistemaAcademicoException {
        String mat = ConsoleUtils.lerTexto("Matrícula (Ex: 2025001)");
        usuarioLogado = db.buscarAluno(mat);
        System.out.println("Bem-vindo Aluno " + usuarioLogado.getNome());
    }

    // --- MENUS ---
    private static void telaPrincipal() {
        System.out.println("\n--- MENU: " + usuarioLogado.getNome() + " ---");
        if (usuarioLogado instanceof Administrativo) menuAdmin();
        else menuGenerico();
    }

    private static void menuAdmin() {
        System.out.println("1. Cadastrar Aluno");
        System.out.println("2. Cadastrar Professor");
        System.out.println("3. Criar Turma");
        System.out.println("4. Matricular Aluno em Turma");
        System.out.println("5. Atribuir Professor");
        System.out.println("6. Relatórios");
        System.out.println("7. Deletar Aluno");
        System.out.println("8. Remover Aluno de Turma");
        System.out.println("9. Substituir Professor de Turma");
        System.out.println("10. Cadastrar Disciplina (Novo)");
        System.out.println("0. Logout");

        int op = ConsoleUtils.lerInteiro("Opção");

        try {
            switch (op) {
                case 1: cadastrarAluno(); break;
                case 2: cadastrarProfessor(); break;
                case 3: criarTurma(); break;
                case 4: matricularAluno(); break;
                case 5: atribuirProfessor(); break;
                case 6: menuRelatorios(); break;
                case 7: deletarAluno(); break;
                case 8: removerAlunoDeTurma(); break;
                case 9: substituirProfessorTurma(); break;
                case 10: cadastrarDisciplina(); break; // Opção Nova
                case 0: usuarioLogado = null; break;
                default: System.out.println("Opção inválida.");
            }
        } catch (SistemaAcademicoException e) {
            System.out.println("\n>> ERRO DE OPERAÇÃO: " + e.getMessage());
            ConsoleUtils.pausar();
        }
    }

    private static void menuGenerico() {
        System.out.println("1. Minhas Turmas / Turmas Disponíveis");
        System.out.println("2. Meus Dados");
        System.out.println("0. Logout");

        int op = ConsoleUtils.lerInteiro("Opção");
        if (op == 0) { usuarioLogado = null; return; }

        if (op == 1) db.getTurmas().forEach(Turma::listarDetalhes);
        else if (op == 2) usuarioLogado.exibirDados();
    }

    // --- RELATÓRIOS ---
    private static void menuRelatorios() {
        System.out.println("\n=== RELATÓRIOS ===");
        System.out.println("1. Listar Alunos");
        System.out.println("2. Listar Professores");
        System.out.println("3. Listar Turmas");
        System.out.println("4. Relatório Geral");
        System.out.println("0. Voltar");

        int op = ConsoleUtils.lerInteiro("Opção");
        String sep = "----------------------------------------------------------------------";

        switch(op) {
            case 1:
                db.getAlunos().forEach(a -> { a.exibirDados(); System.out.println(sep); }); break;
            case 2:
                db.getProfessores().forEach(p -> { p.exibirDados(); System.out.println(sep); }); break;
            case 3:
                db.getTurmas().forEach(t -> { t.listarDetalhes(); System.out.println(sep); }); break;
            case 4:
                System.out.println("ALUNOS (" + db.getAlunos().size() + "):");
                db.getAlunos().forEach(a -> { a.exibirDados(); System.out.println(sep); });
                System.out.println("\nPROFESSORES (" + db.getProfessores().size() + "):");
                db.getProfessores().forEach(p -> { p.exibirDados(); System.out.println(sep); });
                System.out.println("\nTURMAS (" + db.getTurmas().size() + "):");
                db.getTurmas().forEach(t -> { t.listarDetalhes(); System.out.println(sep); });
                break;
            case 0: return;
            default: System.out.println("Inválido.");
        }
        ConsoleUtils.pausar();
    }

    // --- CONTROLLERS ---

    private static void cadastrarAluno() throws SistemaAcademicoException {
        System.out.println("\n-- NOVO ALUNO --");
        String nome = ConsoleUtils.lerTexto("Nome Completo");
        String cpf = ConsoleUtils.lerTexto("CPF (apenas números)");

        System.out.println("Selecione o Curso:");
        for(Curso c : db.getCursos()) {
            System.out.println(c.toString());
        }
        int idCurso = ConsoleUtils.lerInteiro("ID do Curso");
        Curso cursoSelecionado = db.buscarCurso(idCurso);

        Aluno a = new Aluno(nome, cpf, nome.toLowerCase().split(" ")[0] + "@sol.edu", cursoSelecionado);
        db.adicionarAluno(a);
        System.out.println(">> Sucesso! Matrícula gerada: " + a.getMatricula());
    }

    private static void cadastrarProfessor() throws SistemaAcademicoException {
        System.out.println("\n-- NOVO PROFESSOR --");
        String nome = ConsoleUtils.lerTexto("Nome Completo");
        String cpf = ConsoleUtils.lerTexto("CPF");
        String titulo = ConsoleUtils.lerTexto("Titulação");

        Professor p = new Professor(nome, cpf, "docente@sol.edu", titulo);
        db.adicionarProfessor(p);
        System.out.println(">> Sucesso! ID Funcional: " + p.getMatriculaFuncional());
    }

    private static void cadastrarDisciplina() throws SistemaAcademicoException {
        System.out.println("\n-- NOVA DISCIPLINA --");
        System.out.println("Selecione o Curso para adicionar a disciplina:");
        for(Curso c : db.getCursos()) {
            System.out.println(c.toString());
        }
        int idCurso = ConsoleUtils.lerInteiro("ID do Curso");

        // Verifica se curso existe
        db.buscarCurso(idCurso);

        String nome = ConsoleUtils.lerTexto("Nome da Disciplina");
        String codigo = ConsoleUtils.lerTexto("Código (ex: MED05)");
        int ch = ConsoleUtils.lerInteiro("Carga Horária");

        // Gera ID simples baseado no tamanho da lista global (apenas para exemplo)
        int novoId = db.getDisciplinas().size() + 100;
        Disciplina d = new Disciplina(novoId, nome, codigo, ch);

        db.adicionarDisciplinaEmCurso(idCurso, d);
        System.out.println(">> Disciplina cadastrada com sucesso!");
    }

    private static void criarTurma() throws SistemaAcademicoException {
        System.out.println("\n-- NOVA TURMA --");
        System.out.println("Selecione o Curso da Turma:");
        for(Curso c : db.getCursos()) {
            System.out.println(c.toString());
        }
        int idCurso = ConsoleUtils.lerInteiro("ID do Curso");
        Curso curso = db.buscarCurso(idCurso);

        System.out.println("Selecione a Disciplina de " + curso.getNome() + ":");
        List<Disciplina> grade = curso.getGrade();
        if(grade.isEmpty()) throw new RegraNegocioException("Curso sem disciplinas cadastradas.");

        for(Disciplina d : grade) {
            System.out.println(d.toString());
        }

        int idDisc = ConsoleUtils.lerInteiro("ID da Disciplina");
        Disciplina disciplinaSelecionada = grade.stream()
                .filter(d -> d.getId() == idDisc)
                .findFirst()
                .orElseThrow(() -> new ValidacaoException("ID de disciplina inválido para este curso."));

        String cod = ConsoleUtils.lerTexto("Código da Turma (ex: T01-2025)");
        Turma t = new Turma(cod, 2025, disciplinaSelecionada);
        db.adicionarTurma(t);
        System.out.println(">> Turma criada.");
    }

    private static void matricularAluno() throws SistemaAcademicoException {
        System.out.println("\n-- MATRÍCULA --");
        String mat = ConsoleUtils.lerTexto("Matrícula do Aluno");
        Aluno a = db.buscarAluno(mat);

        String codT = ConsoleUtils.lerTexto("Código da Turma");
        Turma t = db.buscarTurma(codT);

        t.adicionarAluno(a);
        System.out.println(">> Aluno matriculado com sucesso.");
    }

    private static void atribuirProfessor() throws SistemaAcademicoException {
        System.out.println("\n-- ATRIBUIÇÃO --");
        String idProf = ConsoleUtils.lerTexto("ID Funcional Professor");
        Professor p = db.buscarProfessor(idProf);

        String codT = ConsoleUtils.lerTexto("Código da Turma");
        Turma t = db.buscarTurma(codT);

        Professor antigo = t.getProfessorResponsavel();
        if (antigo != null) antigo.removerTurma(t);

        t.setProfessorResponsavel(p);
        p.adicionarTurma(t);
        System.out.println(">> Professor atribuído.");
    }

    private static void deletarAluno() throws SistemaAcademicoException {
        String mat = ConsoleUtils.lerTexto("Matrícula para deletar");
        db.removerAluno(mat);
        System.out.println(">> Registro deletado do sistema.");
    }

    private static void removerAlunoDeTurma() throws SistemaAcademicoException {
        String codT = ConsoleUtils.lerTexto("Código da Turma");
        Turma t = db.buscarTurma(codT);

        String mat = ConsoleUtils.lerTexto("Matrícula do Aluno");
        Aluno a = db.buscarAluno(mat);

        t.removerAluno(a);
        System.out.println(">> Aluno removido da turma.");
    }

    private static void substituirProfessorTurma() throws SistemaAcademicoException {
        atribuirProfessor();
    }
}