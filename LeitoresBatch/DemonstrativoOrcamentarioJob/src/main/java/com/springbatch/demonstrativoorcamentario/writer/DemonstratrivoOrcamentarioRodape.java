package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeWrite;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.stereotype.Component;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;

@Component
public class DemonstratrivoOrcamentarioRodape implements FlatFileFooterCallback {
  private Double totalGeral = 0.0;

  @Override
  public void writeFooter(Writer writer) throws IOException {
    writer.append("\n");
    writer.append(String.format("\t\t\t\t\t\t\t  Total: %s\n", NumberFormat.getCurrencyInstance().format(totalGeral)));
    writer.append(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s\n", "fkyew6868fewjfhjjewf"));
  }

  @BeforeWrite
  public void beforeWrite(Chunk<GrupoLancamento> chunk) {
    for (GrupoLancamento grupoLancamento : chunk.getItems()) {
      totalGeral += grupoLancamento.getTotal();
    }
  }

  @AfterChunk
  public void afterChunk(ChunkContext context) {
    totalGeral = 0.0;
  }
}
