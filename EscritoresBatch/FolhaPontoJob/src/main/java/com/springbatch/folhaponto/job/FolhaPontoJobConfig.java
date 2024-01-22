package com.springbatch.folhaponto.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FolhaPontoJobConfig {
	private final JobRepository jobRepository;

	public FolhaPontoJobConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Bean
	Job folhaPontoJob(Step folhaPontoStep) {
		return new JobBuilder("folhaPontoJob", jobRepository)
				.start(folhaPontoStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
