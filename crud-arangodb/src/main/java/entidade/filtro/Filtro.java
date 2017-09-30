package entidade.filtro;

public class Filtro {

	private String campo;
	private String valor;
	private TipoFiltro tipoFiltro;
	private Condicional condicional;

	public Filtro(String campo, String valor, TipoFiltro tipoFiltro) {
		super();
		this.campo = campo;
		this.valor = valor;
		this.tipoFiltro = tipoFiltro;
	}

	public Filtro(String campo, String valor, TipoFiltro tipoFiltro, Condicional condicional) {
		super();
		this.campo = campo;
		this.valor = valor;
		this.tipoFiltro = tipoFiltro;
		this.condicional = condicional;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public TipoFiltro getTipoFiltro() {
		return tipoFiltro;
	}

	public void setTipoFiltro(TipoFiltro tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}

	public Condicional getCondicional() {
		return condicional;
	}

	public void setCondicional(Condicional condicional) {
		this.condicional = condicional;
	}

}
