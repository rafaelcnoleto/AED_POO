// Exceção base do sistema
class SistemaAcademicoException extends Exception {
    public SistemaAcademicoException(String mensagem) {
        super(mensagem);
    }
}

// Erro para buscas que falham (ex: ID inexistente)
class RegistroNaoEncontradoException extends SistemaAcademicoException {
    public RegistroNaoEncontradoException(String msg) {
        super(msg);
    }
}

// Erro para violação de regras (ex: Aluno já matriculado)
class RegraNegocioException extends SistemaAcademicoException {
    public RegraNegocioException(String msg) {
        super(msg);
    }
}

// Erro de validação de dados (ex: CPF inválido, campo vazio)
class ValidacaoException extends SistemaAcademicoException {
    public ValidacaoException(String msg) {
        super(msg);
    }
}