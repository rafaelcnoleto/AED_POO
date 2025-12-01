import java.util.ArrayList;
import java.util.List;

public class BaseDeDados {
    private static BaseDeDados instancia;

    private List<Aluno> alunos;
    private List<Professor> professores;
    private List<Administrativo> administrativos;
    private List<Curso> cursos;
    private List<Disciplina> disciplinas;
    private List<Turma> turmas;

    private BaseDeDados() {
        alunos = new ArrayList<>();
        professores = new ArrayList<>();
        administrativos = new ArrayList<>();
        cursos = new ArrayList<>();
        disciplinas = new ArrayList<>();
        turmas = new ArrayList<>();

        inicializarDadosMock();
    }

    public static BaseDeDados getInstancia() {
        if (instancia == null) instancia = new BaseDeDados();
        return instancia;
    }

    private void inicializarDadosMock() {
        // Usuário Admin padrão
        administrativos.add(new Administrativo("Super Admin", "000", "admin@sol.com", "Diretor", "user", "123"));

        // Dados iniciais
        Curso c1 = new Curso("Ciência da Computação", 1);
        Disciplina d1 = new Disciplina("Programação Orientada a Objetos", "CC01", 64);
        Disciplina d2 = new Disciplina("Estrutura de Dados", "CC02", 64);
        c1.adicionarDisciplina(d1);
        c1.adicionarDisciplina(d2);

        cursos.add(c1);
        disciplinas.add(d1);
        disciplinas.add(d2);

        // Turma inicial vazia
        turmas.add(new Turma("2025-A", 2025, d1));
    }

    // CRUD simplificado
    public void adicionarAluno(Aluno a) { alunos.add(a); }
    public void removerAluno(String matricula) {
        alunos.removeIf(a -> a.getMatricula().equals(matricula));
    }

    public void adicionarProfessor(Professor p) { professores.add(p); }
    public void adicionarTurma(Turma t) { turmas.add(t); }

    // Getters
    public List<Aluno> getAlunos() { return alunos; }
    public List<Professor> getProfessores() { return professores; }
    public List<Administrativo> getAdmins() { return administrativos; }
    public List<Curso> getCursos() { return cursos; }
    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public List<Turma> getTurmas() { return turmas; }
}