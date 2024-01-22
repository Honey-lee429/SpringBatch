package com.springbatch.processadorclassifier.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorClassifierJobConfig {
	private final JobRepository jobRepository;

	public ProcessadorClassifierJobConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Bean
	Job processadorClassifierJob(Step processadorClassifierStep) {
		return new JobBuilder("processadorClassifierJob", jobRepository)
				.start(processadorClassifierStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
