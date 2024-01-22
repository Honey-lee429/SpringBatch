package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

public class ArquivoMultiplosReader implements ItemStreamReader<Cliente>, ResourceAwareItemReaderItemStream<Cliente> {
	private Object objAtual;
	private FlatFileItemReader<Object> delegate;
	
	public ArquivoMultiplosReader(FlatFileItemReader<Object> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		delegate.update(executionContext);
	}

	@Override
	public void close() throws ItemStreamException {
		delegate.close();
	}
	// método que lê o arquivo, identifica se há um cliente e se a próxima linha for de
	// transação, adiciona o vento no cliente até chegar a um novo cliente
	@Override
	public Cliente read() throws Exception {
		if (objAtual == null) // se objetoAtual não for lido, ou seja, não foi carregado
			objAtual = delegate.read(); // ler objeto
			
		Cliente cliente = (Cliente) objAtual;
		objAtual = null;
		
		if (cliente != null) {
			while (peek() instanceof Transacao)
				cliente.getTransacoes().add((Transacao) objAtual);
		}
		return cliente;
	}

	private Object peek() throws Exception {
		objAtual = delegate.read(); // = leitura do próximo item
		return objAtual;
	}

	@Override
	public void setResource(Resource resource) {
		delegate.setResource(resource);
	}

}
