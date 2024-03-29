package com.springbatch.arquivolargurafixa.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class ArquivoLarguraFixaStepConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public ArquivoLarguraFixaStepConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	Step leituraArquivoLarguraFixaStep(ItemReader<Cliente> leituraArquivoLarguraFixaReader,
			ItemWriter<Cliente> leituraArquivoLarguraFixaWriter) {
		return new StepBuilder("arquivoLarguraFixaStep", jobRepository)
				.<Cliente, Cliente>chunk(1, transactionManager) // deve-se considerar a quantidade dos eventos e o tamanho da memoria
				.reader(leituraArquivoLarguraFixaReader)
				.writer(leituraArquivoLarguraFixaWriter)
				.build();
	}
}
