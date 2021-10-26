package com.texoit.avaliacao.demo.etl;

import com.texoit.avaliacao.model.FilmeConcurso;
import com.texoit.avaliacao.repository.FilmeConcursoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<FilmeConcurso> {

    private final FilmeConcursoRepository filmeConcursoRepository;

    @Autowired
    public DBWriter(FilmeConcursoRepository filmeConcursoRepository) {
        this.filmeConcursoRepository = filmeConcursoRepository;
    }

    @Override
    public void write(List<? extends FilmeConcurso> list) {
        list.forEach(System.out::println);
        filmeConcursoRepository.saveAll(list);
    }
}
