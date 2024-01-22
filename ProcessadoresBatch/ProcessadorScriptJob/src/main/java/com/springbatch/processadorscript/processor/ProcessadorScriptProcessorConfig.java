package com.springbatch.processadorscript.processor;

import com.springbatch.processadorscript.dominio.Cliente;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.ScriptItemProcessorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ProcessadorScriptProcessorConfig {
	@Bean
	ItemProcessor<Cliente, Cliente> processadorScriptProcessor() {
		return new ScriptItemProcessorBuilder<Cliente, Cliente>()
				.language("nashorn") //javasctitp, shell, ruby
				.scriptResource(
						new FileSystemResource("files/script.js"))
				// aquivo configurado para se houver o cliente, ignorar e continuar o processamento
				.build();
	}
}
