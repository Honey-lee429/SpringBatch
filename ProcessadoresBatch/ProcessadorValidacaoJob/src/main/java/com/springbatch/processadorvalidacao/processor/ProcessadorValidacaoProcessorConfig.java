package com.springbatch.processadorvalidacao.processor;

import java.util.HashSet;
import java.util.Set;

import com.springbatch.processadorvalidacao.dominio.Cliente;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessadorValidacaoProcessorConfig {
	private Set<String> emails = new HashSet<>();

	@Bean
	ItemProcessor<Cliente, Cliente> procesadorValidacaoProcessor() throws Exception {
		return new CompositeItemProcessorBuilder<Cliente, Cliente>()
				.delegates(beanValidatingProcessor(), emailValidatingProcessor())
				.build();
	}

	//validação do batch hibernate
	private BeanValidatingItemProcessor<Cliente> beanValidatingProcessor() throws Exception {
		BeanValidatingItemProcessor<Cliente> processor = new BeanValidatingItemProcessor<>();
		processor.setFilter(true); // para nao interromper o processamento dos registros caso haja um dado inválido
		processor.afterPropertiesSet(); // quando usa um processador composto ex CompositeItemProcessorBuilder
		return processor;
	}

	//validação personalizada
	private ValidatingItemProcessor<Cliente> emailValidatingProcessor() {
		ValidatingItemProcessor<Cliente> processor = new ValidatingItemProcessor<>();
		processor.setValidator(validator());
		processor.setFilter(true);
		return processor;
	}

	private Validator<Cliente> validator() {
		return new Validator<Cliente>() {

			@Override
			public void validate(Cliente cliente) throws ValidationException {
				if (emails.contains(cliente.getEmail()))
					throw new ValidationException(String.format("O cliente %s já foi processado!", cliente.getEmail()));
				emails.add(cliente.getEmail());
			}

		};
	}
}
