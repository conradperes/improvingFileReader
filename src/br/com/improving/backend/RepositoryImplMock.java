package br.com.improving.backend;

import java.math.BigDecimal;
import java.util.Random;

public class RepositoryImplMock implements Repository {

	@Override
	public Long generateFileId(String fileName) {
		//PrintUtils.print("Nome do Arquivo =" + fileName + " Registrado com sucesso!");
		return (long) new Random().nextInt(10_000);
	}

	@Override
	public void reportInvalidLine(Long fileId, int lineNumber, Field field, String message) {
		/*PrintUtils.print("***reportInvalidLine*** Arquivo =" + fileId +
				" Linha "+
				lineNumber +
				"Campo "+field+
				"Mensagem de Erro "+message);*/

	}

	@Override
	public Long getClientId(String description) {
		// TODO Auto-generated method stub
		if(description!=null)
			return 1l;
		return null;
	}

	@Override
	public void save(Long fileId, Long lineNumber, Long clientId, BigDecimal value) {
		/*PrintUtils.print(" ***SAVE *** Arquivo =" + fileId +
				" Linha "+
				lineNumber +
				"Cliente "+clientId+
				"Valor monetário persistido "+value);*/

	}

}
