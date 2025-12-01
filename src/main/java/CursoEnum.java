enum CursoEnum {
    ENG_SOFTWARE(1, "Engenharia de Software"),
    DIREITO(2, "Direito"),
    ADMINISTRACAO(3, "Administração"),
    MEDICINA(4, "Medicina"),
    PSICOLOGIA(5, "Psicologia"),
    ARQUITETURA(6, "Arquitetura"),
    CONTABEIS(7, "Ciências Contábeis"),
    ENFERMAGEM(8, "Enfermagem");

    private final int id;
    private final String nome;

    CursoEnum(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
}
