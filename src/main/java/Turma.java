import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigoTurma;
    private int anoLetivo;
    private Disciplina disciplina;
    private Professor professorResponsavel;
    private List<Aluno> alunosMatriculados;

    public Turma(String codigoTurma, int anoLetivo, Disciplina disciplina, Professor prof) {
        this.codigoTurma = codigoTurma;
        this.anoLetivo = anoLetivo;
        this.disciplina = disciplina;
        this.professorResponsavel = prof;
        this.alunosMatriculados = new ArrayList<>();
    }

    public void adicionarAluno(Aluno a) {
        alunosMatriculados.add(a);
    }

    @Override
    public String toString() {
        return "Turma " + codigoTurma + " - " + disciplina.getNome();
    }
}