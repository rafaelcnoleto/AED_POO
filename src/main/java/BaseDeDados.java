// Arquivo: BaseDeDados.java
import java.util.ArrayList;
import java.util.List;

public class BaseDeDados {
    private static BaseDeDados instancia;

    // Listas de Armazenamento
    private List<Aluno> alunos = new ArrayList<>();
    private List<Professor> professores = new ArrayList<>();
    private List<Administrativo> admins = new ArrayList<>();
    private List<Curso> cursos = new ArrayList<>();
    private List<Turma> turmas = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>(); // Lista global

    private BaseDeDados() {
        seed();
    }

    public static BaseDeDados getInstancia() {
        if (instancia == null) instancia = new BaseDeDados();
        return instancia;
    }

    // --- BUSCA ---
    public Aluno buscarAluno(String matricula) throws RegistroNaoEncontradoException {
        return alunos.stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Aluno matrícula " + matricula + " não encontrado."));
    }

    public Professor buscarProfessor(String matriculaFuncional) throws RegistroNaoEncontradoException {
        return professores.stream()
                .filter(p -> p.getMatriculaFuncional().equalsIgnoreCase(matriculaFuncional))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Professor " + matriculaFuncional + " não encontrado."));
    }

    public Turma buscarTurma(String codigo) throws RegistroNaoEncontradoException {
        return turmas.stream()
                .filter(t -> t.getCodigoTurma().equalsIgnoreCase(codigo))
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Turma " + codigo + " não encontrada."));
    }

    public Curso buscarCurso(int id) throws RegistroNaoEncontradoException {
        return cursos.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RegistroNaoEncontradoException("Curso ID " + id + " não encontrado."));
    }

    // --- ESCRITA ---
    public void adicionarAluno(Aluno a) throws RegraNegocioException {
        if(alunos.stream().anyMatch(al -> al.getCpf().equals(a.getCpf()))) {
            throw new RegraNegocioException("CPF já cadastrado para outro aluno.");
        }
        alunos.add(a);
    }

    public void adicionarProfessor(Professor p) throws RegraNegocioException {
        if(professores.stream().anyMatch(pr -> pr.getCpf().equals(p.getCpf()))) {
            throw new RegraNegocioException("CPF já cadastrado para outro professor.");
        }
        professores.add(p);
    }

    public void adicionarTurma(Turma t) throws RegraNegocioException {
        if(turmas.stream().anyMatch(tm -> tm.getCodigoTurma().equals(t.getCodigoTurma()))) {
            throw new RegraNegocioException("Código de turma já existente.");
        }
        turmas.add(t);
    }

    // NOVO: Método para criar disciplinas dinamicamente
    public void adicionarDisciplinaEmCurso(int idCurso, Disciplina d) throws RegistroNaoEncontradoException {
        Curso c = buscarCurso(idCurso);
        c.adicionarDisciplina(d);
        disciplinas.add(d); // Adiciona na lista global também
    }

    public void removerAluno(String matricula) throws RegistroNaoEncontradoException {
        Aluno a = buscarAluno(matricula);
        alunos.remove(a);
    }

    public void adicionarAdmin(Administrativo adm) { admins.add(adm); }

    // --- GETTERS ---
    public List<Administrativo> getAdmins() { return admins; }
    public List<Aluno> getAlunos() { return alunos; }
    public List<Professor> getProfessores() { return professores; }
    public List<Turma> getTurmas() { return turmas; }
    public List<Curso> getCursos() { return cursos; }
    public List<Disciplina> getDisciplinas() { return disciplinas; }

    // --- SEED (DADOS FIXOS) ---
    private void seed() {
        try {
            // 1. Admin
            admins.add(new Administrativo("Super Admin", "00000000000", "admin@sol.edu", "Diretor", "user", "123"));

            // 2. Inicializar TODOS os cursos do Enum
            for (CursoEnum e : CursoEnum.values()) {
                Curso c = new Curso(e.getId(), e.getNome());
                cursos.add(c);
            }

            // 3. Adicionar disciplinas básicas apenas para alguns cursos (Exemplo)
            int dId = 1;

            // Engenharia
            Curso eng = buscarCurso(CursoEnum.ENG_SOFTWARE.getId());
            addDisc(eng, new Disciplina(dId++, "POO Java", "CC01", 64));
            addDisc(eng, new Disciplina(dId++, "Banco de Dados", "CC02", 64));

            // Direito
            Curso dir = buscarCurso(CursoEnum.DIREITO.getId());
            addDisc(dir, new Disciplina(dId++, "Direito Civil", "DIR01", 60));
            addDisc(dir, new Disciplina(dId++, "Direito Penal", "DIR02", 60));

            // Medicina
            Curso med = buscarCurso(CursoEnum.MEDICINA.getId());
            addDisc(med, new Disciplina(dId++, "Anatomia", "MED01", 80));

            // Psicologia
            Curso psi = buscarCurso(CursoEnum.PSICOLOGIA.getId());
            addDisc(psi, new Disciplina(dId++, "Psicanálise", "PSI01", 60));

        } catch (Exception e) {
            System.err.println("Erro crítico no seed: " + e.getMessage());
        }
    }

    private void addDisc(Curso c, Disciplina d) {
        c.adicionarDisciplina(d);
        disciplinas.add(d);
    }
}