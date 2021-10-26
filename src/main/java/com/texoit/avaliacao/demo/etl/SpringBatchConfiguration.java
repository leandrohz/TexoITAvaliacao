package com.texoit.avaliacao.demo.etl;

import com.texoit.avaliacao.model.FilmeConcurso;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

    @Bean
    public TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor("spring_batch");
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<FilmeConcurso> itemReader,
                   //ItemProcessor<FilmeConcurso, FilmeConcurso> itemProcessor,
                   ItemWriter<FilmeConcurso> itemWriter) {

        Step step = stepBuilderFactory.get("Carga de Dados")
                .<FilmeConcurso, FilmeConcurso>chunk(1000)
                .reader(itemReader)
                .faultTolerant()
                .skipPolicy(new FileVerificationSkipper())
                //.processor(itemProcessor)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();

        return jobBuilderFactory.get("ETL")
                                .incrementer(new RunIdIncrementer())
                                .start(step)
                                .build();
    }

    @Bean
    public FlatFileItemReader<FilmeConcurso> fileItemReader(@Value("${arquivoInicial}") Resource resource) {
        FlatFileItemReader<FilmeConcurso> fileItemReader = new FlatFileItemReader<>();
        fileItemReader.setResource(resource);
        fileItemReader.setEncoding("ISO-8859-3");
        fileItemReader.setName("CSV");
        fileItemReader.setLinesToSkip(1);
        fileItemReader.setLineMapper(lineMapper());
        return fileItemReader;
    }

    @Bean
    public LineMapper<FilmeConcurso> lineMapper() {

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("year", "title", "studios", "producers", "winner");

        DefaultLineMapper<FilmeConcurso> mapper = new DefaultLineMapper<>();

        mapper.setLineTokenizer(lineTokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
            FilmeConcurso data = new FilmeConcurso();
            data.setNumeroAnoEvento(fieldSet.readLong(0));
            data.setNomeFilme(fieldSet.readString(1));
            data.setNomeEstudio(fieldSet.readString(2));
            data.setNomeProdutor(fieldSet.readString(3));
            data.setVencedor(
                    Boolean.parseBoolean(
                            String.valueOf(
                                    fieldSet.readString(4).equalsIgnoreCase("yes") ?
                                    true : false)));
            return data;
        });

        return mapper;
    }

}
