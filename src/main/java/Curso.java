import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int codigoCurso;
    private int duracaoSemestres;
    private List<Disciplina> gradeCurricular;

    public Curso(String nome, int codigoCurso, int duracaoSemestres) {
        this.nome = nome;
        this.codigoCurso = codigoCurso;
        this.duracaoSemestres = duracaoSemestres;
        this.gradeCurricular = new ArrayList<>();
    }

    public void adicionarDisciplina(Disciplina d) {
        this.gradeCurricular.add(d);
    }

    public String getNome() { return nome; }

    @Override
    public String toString() { return nome; }
}