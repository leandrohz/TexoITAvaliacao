package com.texoit.avaliacao.controller.api;

import com.texoit.avaliacao.dto.FilmeConcursoDto;
import com.texoit.avaliacao.dto.FilmeConcursoManterDto;
import com.texoit.avaliacao.dto.IntervaloRepeticaoPremioDto;
import com.texoit.avaliacao.dto.ProdutorPremiadoDto;
import com.texoit.avaliacao.model.FilmeConcurso;
import com.texoit.avaliacao.repository.FilmeConcursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/premios")
public class PremiosApiController {

	private final String apiRoute = "api/premios/";
	@Autowired
	private FilmeConcursoRepository filmeConcursoRepository;

	@GetMapping()
	public List<FilmeConcursoDto> lista(String nomeFilme) {
		List<FilmeConcurso> lista;
		if (nomeFilme == null) {
			lista = filmeConcursoRepository.findAll();
		} else {
			lista = filmeConcursoRepository.findByNomeFilme(nomeFilme);
		}
		return FilmeConcursoDto.converterParaDto(lista);
	}

	@GetMapping("produtoresMaisPremiadosPorIntervalo")
	public IntervaloRepeticaoPremioDto listaProdutoresMaisPremiadosPorIntervalo() {

		List<FilmeConcurso> lista = filmeConcursoRepository.recuperarMaisPremiados();
		List<ProdutorPremiadoDto> listaIntervalosProdutoresMaisPremiados = getIntervalosProdutoresMaisPremiados(lista);

		return getIntervaloMinimoMaximoProdutoresMaisPremiados(listaIntervalosProdutoresMaisPremiados);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<FilmeConcursoDto> cadastrar(@RequestBody @Valid FilmeConcursoManterDto dto, UriComponentsBuilder uriBuilder) {
		FilmeConcurso filmeConcurso = dto.converterParaEntidade();
		filmeConcursoRepository.save(filmeConcurso);
		URI uri = uriBuilder.path(apiRoute + "{idFilmeConcurso}").buildAndExpand(filmeConcurso.getIdFilmeConcurso()).toUri();
		return ResponseEntity.created(uri).body(new FilmeConcursoDto(filmeConcurso));
	}

	@GetMapping("/{idFilmeConcurso}")
	public ResponseEntity<FilmeConcursoDto> detalhar(@PathVariable Integer idFilmeConcurso) {
		Optional<FilmeConcurso> filmeConcurso = filmeConcursoRepository.findById(idFilmeConcurso);
		if (filmeConcurso.isPresent()) {
			return ResponseEntity.ok(new FilmeConcursoDto(filmeConcurso.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{idFilmeConcurso}")
	@Transactional
	public ResponseEntity<FilmeConcursoDto> atualizar(@PathVariable Integer idFilmeConcurso, @RequestBody @Valid FilmeConcursoManterDto dto) {
		Optional<FilmeConcurso> optional = filmeConcursoRepository.findById(idFilmeConcurso);
		if (optional.isPresent()) {
			FilmeConcurso filmeConcurso = dto.atualizar(idFilmeConcurso, filmeConcursoRepository);
			return ResponseEntity.ok(new FilmeConcursoDto(filmeConcurso));
		}
		
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{idFilmeConcurso}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Integer idFilmeConcurso) {
		Optional<FilmeConcurso> optional = filmeConcursoRepository.findById(idFilmeConcurso);
		if (optional.isPresent()) {
			filmeConcursoRepository.deleteById(idFilmeConcurso);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}

	private List<ProdutorPremiadoDto> getIntervalosProdutoresMaisPremiados(List<FilmeConcurso> lista) {
		Long anoEvento = 0L;
		String nomeProdutor = null;
		int intervaloPremio = 0;
		int countRepeticaoPremiacao = 1;
		List<ProdutorPremiadoDto> listaIntervalos = new ArrayList<>();
		for (FilmeConcurso item:lista) {
			if(countRepeticaoPremiacao == 1){
				anoEvento = item.getNumeroAnoEvento();
				nomeProdutor = item.getNomeProdutor();
			}
			if(countRepeticaoPremiacao % 2 == 0){
				if(nomeProdutor.equals(item.getNomeProdutor())){
					intervaloPremio = anoEvento.intValue() - item.getNumeroAnoEvento().intValue();
					ProdutorPremiadoDto produtorPremiado = new ProdutorPremiadoDto();
					produtorPremiado.setAnoVitoriaAnterior(item.getNumeroAnoEvento());
					produtorPremiado.setAnoVitoriaSeguinte(anoEvento);
					produtorPremiado.setNomeProdutor(nomeProdutor);
					produtorPremiado.setIntervaloPremiacao(intervaloPremio);
					listaIntervalos.add(produtorPremiado);
					countRepeticaoPremiacao = 0;
				}
			}
			if(nomeProdutor.equals(item.getNomeProdutor())){
				countRepeticaoPremiacao++;
			}
			nomeProdutor = item.getNomeProdutor();
			anoEvento = item.getNumeroAnoEvento();

		}
		return listaIntervalos;
	}

	private IntervaloRepeticaoPremioDto getIntervaloMinimoMaximoProdutoresMaisPremiados(List<ProdutorPremiadoDto> intervalos) {

		int intervaloMinimo = intervalos.stream().mapToInt(ProdutorPremiadoDto::getIntervaloPremiacao).min().getAsInt();
		int intervaloMaximo = intervalos.stream().mapToInt(ProdutorPremiadoDto::getIntervaloPremiacao).max().getAsInt();

		List<ProdutorPremiadoDto> listaIntervaloMinimo = intervalos.stream()
				.filter(item -> item.getIntervaloPremiacao() == intervaloMinimo)
				.collect(Collectors.toList());

		List<ProdutorPremiadoDto> listaIntervaloMaximo = intervalos.stream()
				.filter(item -> item.getIntervaloPremiacao() == intervaloMaximo)
				.collect(Collectors.toList());

		IntervaloRepeticaoPremioDto intervaloRepeticao = new IntervaloRepeticaoPremioDto();
		intervaloRepeticao.setIntervaloMinimo(listaIntervaloMinimo);
		intervaloRepeticao.setIntervaloMaximo(listaIntervaloMaximo);

		return intervaloRepeticao;
	}
}







