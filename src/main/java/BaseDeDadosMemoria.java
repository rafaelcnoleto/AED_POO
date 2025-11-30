// Arquivo: BaseDeDadosMemoria.java
import java.util.ArrayList;
import java.util.List;

public class BaseDeDadosMemoria {
    private static BaseDeDadosMemoria instancia;

    private List<Aluno> todosAlunos;
    private List<Professor> todosProfessores;
    private List<Curso> todosCursos;
    private List<Turma> todasTurmas;
    private List<Administrativo> todosAdmins; // Nova lista

    private BaseDeDadosMemoria() {
        todosAlunos = new ArrayList<>();
        todosProfessores = new ArrayList<>();
        todosCursos = new ArrayList<>();
        todasTurmas = new ArrayList<>();
        todosAdmins = new ArrayList<>();

        inicializarDados();
    }

    public static BaseDeDadosMemoria getInstancia() {
        if (instancia == null) {
            instancia = new BaseDeDadosMemoria();
        }
        return instancia;
    }

    private void inicializarDados() {
        // Dados iniciais
        todosCursos.add(new Curso("Ciência da Computação", 1, 8));
        todosCursos.add(new Curso("Engenharia de Software", 2, 8));

        // --- ADMIN PADRÃO SOLICITADO ---
        Administrativo adminPadrao = new Administrativo(
                "Super Admin", "000.000.000-00", "admin@sol.com",
                "Diretor", "user", "123" // Username e Senha pedidos
        );
        todosAdmins.add(adminPadrao);
    }

    public void salvar(Object o) {
        if (o instanceof Aluno) todosAlunos.add((Aluno) o);
        else if (o instanceof Professor) todosProfessores.add((Professor) o);
        else if (o instanceof Curso) todosCursos.add((Curso) o);
        else if (o instanceof Turma) todasTurmas.add((Turma) o);
        else if (o instanceof Administrativo) todosAdmins.add((Administrativo) o);
    }

    public List<Aluno> getTodosAlunos() { return todosAlunos; }
    public List<Professor> getTodosProfessores() { return todosProfessores; }
    public List<Curso> getTodosCursos() { return todosCursos; }
    public List<Turma> getTodasTurmas() { return todasTurmas; }
    public List<Administrativo> getTodosAdmins() { return todosAdmins; }
}