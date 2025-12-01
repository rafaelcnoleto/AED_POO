// Arquivo: Disciplina.java
public class Disciplina {
    private int id; // Identificador numérico
    private String nome;
    private String codigo; // Ex: CC01
    private int cargaHoraria;

    public Disciplina(int id, String nome, String codigo, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }

    @Override
    public String toString() { return "[" + id + "] " + nome + " (" + codigo + ")"; }
}