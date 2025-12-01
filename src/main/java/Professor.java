// Arquivo: Professor.java
import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {
    private static int contadorProf = 100;

    private String matriculaFuncional;
    private String titulacao;
    private List<Turma> turmasMinistradas;

    public Professor(String nome, String cpf, String email, String titulacao) throws ValidacaoException {
        super(nome, cpf, email);
        if(titulacao.isEmpty()) throw new ValidacaoException("Titulação obrigatória.");

        this.titulacao = titulacao;
        this.matriculaFuncional = "DOC-" + (++contadorProf);
        this.turmasMinistradas = new ArrayList<>();
    }

    public void adicionarTurma(Turma t) {
        if(!turmasMinistradas.contains(t)) {
            this.turmasMinistradas.add(t);
        }
    }

    public void removerTurma(Turma t) {
        this.turmasMinistradas.remove(t);
    }

    @Override
    public void exibirDados() {
        System.out.println("PROFESSOR | ID: " + matriculaFuncional + " | Nome: " + nome + " | Titulação: " + titulacao);
    }

    public String getMatriculaFuncional() { return matriculaFuncional; }
}