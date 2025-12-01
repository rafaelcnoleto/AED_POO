public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;

    public Disciplina(String nome, String codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
    }

    public String getNome() { return nome; }

    @Override
    public String toString() { return nome + " (" + codigo + ")"; }
}