package com.springbatch.processadorclassifier.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProcessadorClassifierStepConfig {
	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	public ProcessadorClassifierStepConfig(JobRepository jobRepository,
			PlatformTransactionManager transactionManager) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	Step processadorClassifierStep(
			FlatFileItemReader processadorClassifierReader,
			ItemProcessor processadorClassifierProcessor,
			ItemWriter processadorClassifierWriter) {
		return new StepBuilder("processadorClassifierStep", jobRepository)
				.chunk(1, transactionManager)
				.reader(processadorClassifierReader)
				.processor(processadorClassifierProcessor)
				.writer(processadorClassifierWriter)
				.build();
	}
}
