package com.springbatch.arquivomultiplosformatos.reader;

import java.util.HashMap;
import java.util.Map;

import com.springbatch.arquivomultiplosformatos.dominio.Cliente;
import com.springbatch.arquivomultiplosformatos.dominio.Transacao;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapeaVariosObjetosLineMapperConfig {

  // componente capaz de usar o padrão para descobrir qual line mapper irá aplicar, ou seja, separador por linhas
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Bean
  public PatternMatchingCompositeLineMapper lineMapper() {
    PatternMatchingCompositeLineMapper lineMapper = new PatternMatchingCompositeLineMapper();
    lineMapper.setTokenizers(identificadorDeEventos());
    lineMapper.setFieldSetMappers(fieldSetMappers());
    return lineMapper;
  }


//mapear os objetos domínio
  @SuppressWarnings("rawtypes")
  private Map<String, FieldSetMapper> fieldSetMappers() {
    Map<String, FieldSetMapper> fieldSetMappers = new HashMap<>();
    fieldSetMappers.put("0*", fieldSetMapper(Cliente.class));
    fieldSetMappers.put("1*", fieldSetMapper(Transacao.class));
    return fieldSetMappers;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private FieldSetMapper fieldSetMapper(Class classe) {
    BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
    fieldSetMapper.setTargetType(classe);
    return fieldSetMapper;
  }

  private Map<String, LineTokenizer> identificadorDeEventos() {
    Map<String, LineTokenizer> tokenizers = new HashMap<>();
    // dentro do arquivo clientes, todo registro que começa com 0 *(alguma coisa) é cliente e 1 é transacao
    tokenizers.put("0*", clienteLineTokenizer());
    tokenizers.put("1*", transacaoLineTokenizer());
    return tokenizers;
  }

  private LineTokenizer clienteLineTokenizer() {
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames("nome", "sobrenome", "idade", "email");
    lineTokenizer.setIncludedFields(1, 2, 3, 4);
    return lineTokenizer;
  }

  private LineTokenizer transacaoLineTokenizer() {
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames("id", "descricao", "valor");
    lineTokenizer.setIncludedFields(1, 2, 3);
    return lineTokenizer;
  }
}
