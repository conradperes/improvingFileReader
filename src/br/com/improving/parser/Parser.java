package br.com.improving.parser;

import br.com.improving.backend.ProccessFileAdaptor;
import br.com.improving.backend.ProcessFileService;
import br.com.improving.backend.Repository;
import br.com.improving.backend.RepositoryImplMock;
import br.com.improving.backend.Webservice;
import static br.com.improving.util.PrintUtils.print;

public class Parser {
    private static Repository repository;
    private static Webservice webservice;

    public Parser(Repository repository, Webservice webservice) {
        Parser.repository = repository;
        Parser.webservice = webservice;
    }

    public void processFile(String filePath) throws Exception{
    	ProccessFileAdaptor.proccessFile(filePath, repository);
    	webservice.processed(filePath);
    }
    public static void main(String[] args) {
    	repository = new RepositoryImplMock();
    	webservice =  new ProcessFileService();
    	Parser p = new Parser(repository,webservice);
		try {
			p.processFile(args[0]);
		} catch (Exception e) {
			print("Exceção Disparada no sistema de Processamento de arquivos ="+e.getMessage());
			e.printStackTrace();
			print("Favor contactar o administrador do sistema! ");
		}
		
	}
}
