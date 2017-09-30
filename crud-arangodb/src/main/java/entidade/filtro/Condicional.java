package entidade.filtro;

public enum Condicional {

	E("&&"), OU("||");

	private Condicional(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
