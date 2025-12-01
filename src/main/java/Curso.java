// Arquivo: Curso.java
import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nome;
    private List<Disciplina> grade;

    public Curso(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.grade = new ArrayList<>();
    }

    public void adicionarDisciplina(Disciplina d) { grade.add(d); }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public List<Disciplina> getGrade() { return grade; }

    @Override
    public String toString() { return "[" + id + "] " + nome; }
}