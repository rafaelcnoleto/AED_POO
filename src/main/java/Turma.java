// Arquivo: Turma.java
import java.util.ArrayList;
import java.util.List;

public class Turma {
    private String codigoTurma;
    private int anoLetivo;
    private Disciplina disciplina;
    private Professor professorResponsavel;
    private List<Aluno> alunosMatriculados;

    public Turma(String codigoTurma, int anoLetivo, Disciplina disciplina) throws ValidacaoException {
        if(codigoTurma.isEmpty()) throw new ValidacaoException("Código da turma inválido.");
        this.codigoTurma = codigoTurma;
        this.anoLetivo = anoLetivo;
        this.disciplina = disciplina;
        this.alunosMatriculados = new ArrayList<>();
    }

    public void adicionarAluno(Aluno a) throws RegraNegocioException {
        if(alunosMatriculados.contains(a)) {
            throw new RegraNegocioException("O aluno " + a.getNome() + " já está matriculado nesta turma.");
        }
        alunosMatriculados.add(a);
    }

    public void removerAluno(Aluno a) throws RegistroNaoEncontradoException {
        if (!alunosMatriculados.contains(a)) {
            throw new RegistroNaoEncontradoException("Este aluno não pertence a esta turma.");
        }
        alunosMatriculados.remove(a);
    }

    public void setProfessorResponsavel(Professor p) {
        this.professorResponsavel = p;
    }

    public void listarDetalhes() {
        String profNome = (professorResponsavel != null) ? professorResponsavel.getNome() : "NÃO ATRIBUÍDO";
        System.out.println("\n--- TURMA: " + codigoTurma + " (" + anoLetivo + ") ---");
        System.out.println("Disciplina: " + disciplina.getNome());
        System.out.println("Professor: " + profNome);
        System.out.println("Total Alunos: " + alunosMatriculados.size());
        for(Aluno a : alunosMatriculados) {
            System.out.println("   -> " + a.getMatricula() + " - " + a.getNome());
        }
    }

    public String getCodigoTurma() { return codigoTurma; }
    public Disciplina getDisciplina() { return disciplina; }
    public Professor getProfessorResponsavel() { return professorResponsavel; }
}