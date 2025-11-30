import java.util.ArrayList;
import java.util.List;

public class Professor extends Pessoa {
    private String matriculaFuncional;
    private String titulacao;
    private List<Disciplina> disciplinasMinistradas;

    public Professor(String nome, String cpf, String email, String matriculaFuncional, String titulacao) {
        super(nome, cpf, email);
        this.matriculaFuncional = matriculaFuncional;
        this.titulacao = titulacao;
        this.disciplinasMinistradas = new ArrayList<>();
    }

    @Override
    public void exibirDados() {
        System.out.println("Prof: " + nome + " | Titulação: " + titulacao);
    }

    public String getMatriculaFuncional() { return matriculaFuncional; }

    @Override
    public String toString() { return nome + " [" + titulacao + "]"; }
}