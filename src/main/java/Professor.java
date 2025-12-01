import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {
    private static int contadorProf = 1;

    private String matriculaFuncional;
    private String titulacao;
    private List<Turma> turmasMinistradas;

    public Professor(String nome, String cpf, String email, String titulacao) {
        super(nome, cpf, email);
        this.titulacao = titulacao;
        this.matriculaFuncional = "PROF-" + String.format("%03d", contadorProf++);
        this.turmasMinistradas = new ArrayList<>();
    }

    // Construtor auxiliar para login simulado
    public Professor(String matriculaFuncional, String nome, String email, String titulacao, boolean isLogin) {
        super(nome, "000", email);
        this.matriculaFuncional = matriculaFuncional;
        this.titulacao = titulacao;
        this.turmasMinistradas = new ArrayList<>();
    }

    public void adicionarTurma(Turma t) {
        this.turmasMinistradas.add(t);
    }

    @Override
    public void exibirDados() {
        System.out.println(">> PROFESSOR: " + nome + " (" + matriculaFuncional + ") | Titulação: " + titulacao);
        if(!turmasMinistradas.isEmpty()){
            System.out.println("   Ministrando as turmas:");
            for(Turma t : turmasMinistradas) {
                System.out.println("   - " + t.getCodigoTurma() + " (" + t.getDisciplina().getNome() + ")");
            }
        }
    }

    public String getMatriculaFuncional() { return matriculaFuncional; }
}