package dao;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;

import entidade.Contato;
import entidade.filtro.Condicional;
import entidade.filtro.Filtro;
import org.junit.FixMethodOrder;
import entidade.filtro.TipoFiltro;
import junit.framework.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DAOTeste {

	private static final String usuario = "root";
	private static final String senha = "arango@db";
	private static final String DB = "agenda";
	private static final String COLECAO = "contato";
	private static ArangoDB arangoDB;
	private static ContatoDAO dao;

	private static ArangoDatabase getDB() {
		return arangoDB.db(DB);
	}

	@BeforeClass
	public static void setUpClass() {
		arangoDB = new ArangoDB.Builder().user(usuario).password(senha).build();
		arangoDB.createDatabase(DB);
		getDB().createCollection(COLECAO);
		dao = new ContatoDAO();
	}

	@AfterClass
	public static void tearDownClass() {
		getDB().drop();
		dao = null;
	}

	@Test
	public void A_deveSalvarDocumento() {
		Contato c = new Contato("id1", "Contato 1", "c1@mail.com", "1234-5678");
		dao.salvar(c);
		Assert.assertEquals(1, dao.contatos().size());

	}

	@Test
	public void B_deveAtualizarDocumento() {
		Contato c = dao.buscarContatoPorID("id1");
		c = new Contato("id1", "Contato editado", "c1.editado@mail.com", "9876-5432");
		dao.atualizar(c);
		c = dao.buscarContatoPorID("id1");
		Assert.assertEquals("Contato editado", c.getNome());
		Assert.assertEquals("c1.editado@mail.com", c.getEmail());
		Assert.assertEquals("9876-5432", c.getTelefone());

	}

	@Test
	public void C_deveBuscarDocumentoPorChave() {
		Contato c = dao.buscarContatoPorID("id1");
		Assert.assertNotNull(c);
	}

	@Test
	public void D_deveListarDocumentos() {
		Contato c2 = new Contato("id2", "Contato 2", "c2@mail.com", "2234-5678");
		Contato c3 = new Contato("id3", "Contato 3", "c3@mail.com", "3234-5678");

		dao.salvar(c2);
		dao.salvar(c3);

		Assert.assertEquals(3, dao.contatos().size());

	}

	@Test
	public void E_deveFiltrarDocumentoCom1Parametro() {
		ArrayList<Filtro> filtros = new ArrayList<>();
		filtros.add(new Filtro("nome", "\"%2%\"", TipoFiltro.LIKE));
		Assert.assertEquals(1, dao.contatos(filtros).size());
	}

	@Test
	public void E_deveFiltrarDocumentoCom2Parametro() {
		ArrayList<Filtro> filtros = new ArrayList<>();
		filtros.add(new Filtro("nome", "\"%2%\"", TipoFiltro.LIKE, Condicional.OU));
		filtros.add(new Filtro("nome", "\"%3%\"", TipoFiltro.LIKE));
		Assert.assertEquals(2, dao.contatos(filtros).size());
	}

	@Test
	public void F_deveRemoverDocumento() {
		Contato c = new Contato("id1", "Contato 1", "c1@mail.com", "1234-5678");
		dao.deletar(c);
		Assert.assertEquals(2, dao.contatos().size());

	}
}
