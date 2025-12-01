import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigoTurma;
    private int anoLetivo;
    private Disciplina disciplina;
    private Professor professorResponsavel;
    private List<Aluno> alunosMatriculados;

    public Turma(String codigoTurma, int anoLetivo, Disciplina disciplina) {
        this.codigoTurma = codigoTurma;
        this.anoLetivo = anoLetivo;
        this.disciplina = disciplina;
        this.alunosMatriculados = new ArrayList<>();
    }

    public void adicionarAluno(Aluno a) {
        if(!alunosMatriculados.contains(a)) {
            alunosMatriculados.add(a);
        }
    }

    public void setProfessorResponsavel(Professor p) {
        this.professorResponsavel = p;
    }

    public void listarTurma() {
        String nomeProf = (professorResponsavel != null) ? professorResponsavel.getNome() : "Sem Professor";
        System.out.println("TURMA " + codigoTurma + " | Disc: " + disciplina.getNome() + " | Prof: " + nomeProf);
        System.out.println("   Alunos (" + alunosMatriculados.size() + "):");
        for(Aluno a : alunosMatriculados) {
            System.out.println("   -> " + a.getNome() + " (" + a.getMatricula() + ")");
        }
    }

    public String getCodigoTurma() { return codigoTurma; }
    public Disciplina getDisciplina() { return disciplina; }
    public Professor getProfessorResponsavel() { return professorResponsavel; }
}