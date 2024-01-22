package com.springbatch.folhaponto.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.folhaponto.dominio.FolhaPonto;
import com.springbatch.folhaponto.dominio.Funcionario;
import com.springbatch.folhaponto.reader.FuncionarioReader;

@Configuration
public class FolhaPontoStepConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public FolhaPontoStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	Step folhaPontoStep(
			JdbcCursorItemReader<Funcionario> funcionarioReaderJdbc,
			ItemProcessor<Funcionario, FolhaPonto> folhaPontoProcessor,
			ClassifierCompositeItemWriter<FolhaPonto> folhaPontoWriter,
			@Qualifier("folhaPontoInvalidaWriter") FlatFileItemWriter<FolhaPonto> folhaPontoInvalidaWriter,
			@Qualifier("arquivoFolhaPontoWriter") FlatFileItemWriter<FolhaPonto> arquivoFolhaPontoWriter) {
		return new StepBuilder("folhaPontoStep", jobRepository)
				.<Funcionario, FolhaPonto>chunk(1, transactionManager)
				.reader(new FuncionarioReader(funcionarioReaderJdbc))
				.processor(folhaPontoProcessor)
				.writer(folhaPontoWriter)
				.stream(folhaPontoInvalidaWriter)
				.stream(arquivoFolhaPontoWriter)
				.build();
	}
}
