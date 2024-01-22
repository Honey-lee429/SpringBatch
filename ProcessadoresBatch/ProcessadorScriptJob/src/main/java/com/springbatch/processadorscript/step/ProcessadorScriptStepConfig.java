package com.springbatch.processadorscript.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.processadorscript.dominio.Cliente;

@Configuration
public class ProcessadorScriptStepConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public ProcessadorScriptStepConfig(JobRepository jobRepository,
			PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@Bean
	Step processadorScriptStep(
			ItemReader<Cliente> processadorScriptReader,
			ItemProcessor<Cliente, Cliente> processadorScriptProcessor,
			ItemWriter<Cliente> processadorScriptWriter) {
		return new StepBuilder("processadorScriptStep", jobRepository)
				.<Cliente, Cliente>chunk(1, transactionManager)
				.reader(processadorScriptReader)
				.processor(processadorScriptProcessor)
				.writer(processadorScriptWriter)
				.build();
	}
}
