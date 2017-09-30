package entidade.filtro;

public enum TipoFiltro {

	IGUAL("=="), 
	DIFERENTE("!="), 
	MENOR("<"), 
	MENOR_OU_IGUAL("<="), 
	MAIOR(">"), 
	MAIOR_OU_IGUAL(">="), 
	LIKE("LIKE"), 
	IN("IN"), 
	NOT_IN("NOT IN");

	private TipoFiltro(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
