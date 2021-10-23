package com.texoit.avaliacao;

import com.texoit.avaliacao.model.FilmeConcurso;
import com.texoit.avaliacao.repository.FilmeConcursoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class FilmeConcursoRepositoryTest {

	@Autowired
	private FilmeConcursoRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void consultaProdutoresMaisPremiados_Sucesso_DeveriaRetornarDados() {

		FilmeConcurso entity = new FilmeConcurso();
		entity.setNumeroAnoEvento(2018L);
		entity.setNomeFilme("Leonard");
		entity.setNomeEstudio("MGM");
		entity.setNomeProdutor("Jerry Leider");
		entity.setVencedor(true);
		em.persist(entity);

		entity = new FilmeConcurso();
		entity.setNumeroAnoEvento(2020L);
		entity.setNomeFilme("Leonard 2");
		entity.setNomeEstudio("MGM");
		entity.setNomeProdutor("Jerry Leider");
		entity.setVencedor(true);
		em.persist(entity);

		entity = new FilmeConcurso();
		entity.setNumeroAnoEvento(2004L);
		entity.setNomeFilme("Glitter 3");
		entity.setNomeEstudio("20th Century Fox, Columbia Pictures");
		entity.setNomeProdutor("Laurence Mark");
		entity.setVencedor(true);
		em.persist(entity);

		entity = new FilmeConcurso();
		entity.setNumeroAnoEvento(2015L);
		entity.setNomeFilme("Glitter 4");
		entity.setNomeEstudio("20th Century Fox, Columbia Pictures");
		entity.setNomeProdutor("Laurence Mark");
		entity.setVencedor(true);
		em.persist(entity);

		List<FilmeConcurso> listaMaisPremiados = repository.recuperarMaisPremiados();
		Assert.assertTrue(!listaMaisPremiados.isEmpty());
	}

}
