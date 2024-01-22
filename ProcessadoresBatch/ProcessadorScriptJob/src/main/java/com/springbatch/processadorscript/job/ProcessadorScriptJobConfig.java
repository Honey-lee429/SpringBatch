package com.springbatch.processadorscript.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorScriptJobConfig {
	private final JobRepository jobRepository;

	public ProcessadorScriptJobConfig(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Bean
	Job processadorScriptJob(Step processadorScriptStep) {
		return new JobBuilder("processadorScriptJob", jobRepository)
				.start(processadorScriptStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
