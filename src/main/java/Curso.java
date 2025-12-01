import java.util.ArrayList;
import java.util.List;

public class Curso {
    private String nome;
    private int codigoCurso;
    private List<Disciplina> gradeCurricular;

    public Curso(String nome, int codigoCurso) {
        this.nome = nome;
        this.codigoCurso = codigoCurso;
        this.gradeCurricular = new ArrayList<>();
    }

    public void adicionarDisciplina(Disciplina d) {
        gradeCurricular.add(d);
    }

    public String getNome() { return nome; }
}