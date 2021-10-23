package com.texoit.avaliacao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.texoit.avaliacao.dto.FilmeConcursoManterDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PremiosApiControllerTest {
	private final String apiRoute = "/api/premios/";
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void listarProdutoresMaisPremiadosPorIntervalo_Sucesso_DeveRetornarStatusOk() throws Exception {
		URI uri = new URI(apiRoute + "produtoresMaisPremiados");

		mockMvc
				.perform(MockMvcRequestBuilders
						.get(uri)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(200));
	}

	@Test
	public void cadastrarFilmeConcurso_Sucesso_DeveRetornarStatusCreated() throws Exception {
		URI uri = new URI(apiRoute);
		FilmeConcursoManterDto manterDto = new FilmeConcursoManterDto(
				"2020",
				"Cruising 2",
				"Lorimar Productions, United Artists",
				"Jerry Weintraub",
				"false");

		String requestJson = convertDtoToString(manterDto);

		mockMvc
				.perform(MockMvcRequestBuilders
						.post(uri)
						.content(requestJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(201));
	}

	@Test
	public void cadastrarFilmeConcurso_SemNomeFilmePreenchido_DeveRetornarStatusBadRequest() throws Exception {
		URI uri = new URI(apiRoute);
		FilmeConcursoManterDto manterDto = new FilmeConcursoManterDto(
				"2020",
				"",
				"Lorimar Productions, United Artists",
				"Jerry Weintraub",
				"false");

		String requestJson = convertDtoToString(manterDto);

		mockMvc
				.perform(MockMvcRequestBuilders
						.post(uri)
						.content(requestJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(400));
	}

	@Test
	public void alterarFilmeConcurso_Sucesso_DeveRetornarStatusOk() throws Exception {
		URI uri = new URI(apiRoute + "1");
		FilmeConcursoManterDto manterDto = new FilmeConcursoManterDto(
				"2020",
				"Cruising 2",
				"Lorimar Productions, United Artists",
				"Jerry Weintraub",
				"false");

		String requestJson = convertDtoToString(manterDto);

		mockMvc
				.perform(MockMvcRequestBuilders
						.put(uri)
						.content(requestJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(200));
	}

	@Test
	public void alterarFilmeConcurso_IdInexistente_DeveRetornarStatusNotFound() throws Exception {
		URI uri = new URI(apiRoute + "0");
		FilmeConcursoManterDto manterDto = new FilmeConcursoManterDto(
				"2020",
				"Filme X",
				"Lorimar Productions, United Artists",
				"Jerry Weintraub",
				"false");

		String requestJson = convertDtoToString(manterDto);

		mockMvc
				.perform(MockMvcRequestBuilders
						.put(uri)
						.content(requestJson)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
						.status()
						.is(404));
	}

	private String convertDtoToString(FilmeConcursoManterDto dto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);
	}
}
