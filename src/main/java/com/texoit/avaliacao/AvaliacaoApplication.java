package com.texoit.avaliacao;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.texoit.avaliacao.dto.FilmeConcursoManterDto;
import com.texoit.avaliacao.model.FilmeConcurso;
import com.texoit.avaliacao.repository.FilmeConcursoRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableSwagger2
public class AvaliacaoApplication implements CommandLineRunner {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Autowired
	private FilmeConcursoRepository filmeConcursoRepository;

	public static void main(String[] args) {

		SpringApplication.run(AvaliacaoApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
		String delimiters = ",|, and | and ";
		ClassLoader classLoader = getClass().getClassLoader();
		try(CSVReader reader = new CSVReaderBuilder(
				new FileReader(classLoader.getResource("files/movielist.csv").getFile()))
				.withCSVParser(csvParser)
				.withSkipLines(1)
				.build()){
					String numeroAnoEvento;
					String nomeFilme;
					String nomeEstudio;
					String nomeProdutor;
					String vencedor;
					for (String[] linha : reader.readAll()) {
						numeroAnoEvento = linha[0];
						nomeFilme = linha[1];
						nomeEstudio = linha[2];
						nomeProdutor = linha[3];
						vencedor = linha[4].equals("yes") ? "true" : "false";
						boolean desagruparNomeProdutor = nomeProdutor.contains(",") ||
								nomeProdutor.contains(", and ") ||
								nomeProdutor.contains(" and ");
						if(desagruparNomeProdutor){
							for (String novoNomeProdutor : nomeProdutor.split(delimiters)) {
								nomeProdutor = novoNomeProdutor.trim();
								if(!nomeProdutor.isEmpty()){
									prepararParaSalvarDados(numeroAnoEvento, nomeFilme, nomeEstudio, nomeProdutor, vencedor);
								}
							}
						} else {
							prepararParaSalvarDados(numeroAnoEvento, nomeFilme, nomeEstudio, nomeProdutor, vencedor);
						}
					}
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}

	private void prepararParaSalvarDados(String numeroAnoEvento,
										String nomeFilme,
										String nomeEstudio,
										String nomeProdutor,
										String vencedor){

		FilmeConcursoManterDto dto = new FilmeConcursoManterDto(numeroAnoEvento,
				nomeFilme,
				nomeEstudio,
				nomeProdutor,
				vencedor);

		FilmeConcurso filmeConcurso = dto.converterParaEntidade();

		filmeConcursoRepository.save(filmeConcurso);

	}

}
