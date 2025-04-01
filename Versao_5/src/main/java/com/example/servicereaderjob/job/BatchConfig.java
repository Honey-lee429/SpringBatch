package com.example.servicereaderjob.job;

import com.example.servicereaderjob.domain.Pessoa;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    /*
    * O Job Repository do Spring Batch é um componente que armazena o estado de um job, que é compartilhado com outros
    * componentes da solução.
    O Spring Batch é um framework de aplicação em lote que permite criar tarefas automatizadas e repetíveis. É uma ferramenta
    * para o desenvolvimento de aplicações de processamento em lote, como a leitura, transformação e gravação de dados.
    O Job Repository armazena informações como: Duração da execução, Status da execução, Erros, Escritas, Leituras.
    O Spring Batch é baseado em metadados, que são usados para compreender o estado de um job.
    O Spring Batch pode ser usado em casos simples, como emitir SQL uma vez, e em casos complexos, como executar processamento
    * enquanto acessa múltiplos bancos de dados.
    O Spring é um framework de desenvolvimento de aplicativos Java que oferece recursos como Inversão de Controle (IoC),
    * Injeção de Dependência (DI), suporte a transações, AOP (Aspect-Oriented Programming) e muito mais.  */

    /*criado um PlatformTransactionManager para o datasorce não primario, ou seja, não será o banco do spring batch
    * e sim o banco Pessoa*/
    @Autowired
    @Qualifier("transactionManagerApp")
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job job(Step step, JobRepository jobRepository) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step step(ItemReader<Pessoa> reader, ItemWriter<Pessoa> writer, JobRepository jobRepository) {
        return new StepBuilder("step", jobRepository)
                .<Pessoa, Pessoa>chunk(200, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<Pessoa> reader() {
        return new FlatFileItemReaderBuilder<Pessoa>()
                .name("reader")
                .resource(new FileSystemResource("files/pessoas.csv"))
                .comments("--")
                .delimited()
                .names("nome", "email", "dataNascimento", "idade", "id")
                .targetType(Pessoa.class)
                .build();
    }
/*
* dataSource (javax.sql.DataSource): The configured datasource bean used to connect to the database.
* It is injected using Spring's @Qualifier("appDS") annotation to ensure the correct datasource is used.*/
    @Bean
    public ItemWriter<Pessoa> writer(@Qualifier("appDS") DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
                .dataSource(dataSource)
                .sql(
                        "INSERT INTO pessoa (id, nome, email, data_nascimento, idade) VALUES (:id, :nome, :email, :dataNascimento, :idade)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}
