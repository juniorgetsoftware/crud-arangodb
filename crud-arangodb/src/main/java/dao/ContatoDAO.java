package dao;

import java.util.ArrayList;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.ArangoDatabase;

import entidade.Contato;
import entidade.filtro.Filtro;

public class ContatoDAO {

	private final String COLECAO = "contato";
	private final String DB = "agenda";
	private ArangoDB arangoDB;

	private ArangoCollection getColecao() {
		return getDB().collection(COLECAO);
	}

	private ArangoDatabase getDB() {
		return arangoDB.db(DB);
	}

	public ContatoDAO() {
		arangoDB = new ArangoDB.Builder().host("localhost", 8529).user("root").password("arango@db").build();
		if (getDB() == null) {
			arangoDB.createDatabase(DB);
			getDB().createCollection(COLECAO);
		}
	}

	public void salvar(Contato contato) {
		getColecao().insertDocument(contato);

	}

	public void atualizar(Contato contato) {
		getColecao().updateDocument(contato.getId(), contato);

	}

	public ArrayList<Contato> contatos(ArrayList<Filtro> filtros) {
		ArrayList<Contato> contatos = new ArrayList<>();
		String var = "c";
		StringBuilder query = new StringBuilder("FOR " + var + " IN " + COLECAO);

		if (!filtros.isEmpty())
			query.append(" filter ");

		for (int i = 0; i < filtros.size(); i++) {
			Filtro filtro = filtros.get(i);
			query.append(" ").append(var + "." + filtro.getCampo());
			query.append(" ").append(filtro.getTipoFiltro().getDescricao());
			query.append(" ").append(filtro.getValor()).append(" ");

			if (i != filtros.size() - 1)
				query.append(" " + filtro.getCondicional().getDescricao() + " ");
		}

		query.append(" RETURN " + var);

		try {
			ArangoCursor<Contato> cursor = getDB().query(query.toString(), null, null, Contato.class);
			while (cursor.hasNext()) {
				contatos.add(cursor.next());
			}

		} catch (ArangoDBException e) {
			System.err.println("Falha ao executar a query. \n'" + query + "'\n\n" + e.getMessage());
		}
		return contatos;
	}

	public ArrayList<Contato> contatos() {
		return contatos(new ArrayList<>());
	}

	public void deletar(Contato contato) {
		getColecao().deleteDocument(contato.getId());
	}

	public Contato buscarContatoPorID(String id) {
		return getColecao().getDocument(id, Contato.class);
	}

}
