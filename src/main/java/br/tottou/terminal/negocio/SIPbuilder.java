package br.tottou.terminal.negocio;

import java.io.File;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


/**
* Cria Pacotes para ingestão
* 
*
* @author Arthur Heleno - arthur.souza
*/

public class SIPbuilder {
	
	
public void gerarPacoteSIP(String nomeArquivo, String url, String handle, String dir){
	
	try {
		criarDiretorio(dir);
	} catch (IOException e1) {		
		e1.printStackTrace();
	}
	
	try {
		criarDiretorio(dir+handle);
	} catch (IOException e1) {		
		e1.printStackTrace();
	}
	
	try {
		criarDiretorio(dir+handle+"/item_1");
	} catch (IOException e1) {		
		e1.printStackTrace();
	}
	
	
	//contents
	Path contents = Paths.get(dir+handle+"/item_1/contents");
	//List<String> lines = Arrays.asList("The first line", "The second line");
	List<String> lines = Arrays.asList(nomeArquivo);
	try {
		Files.write(contents, lines, Charset.forName("UTF-8"));
	} catch (IOException e) {		
		e.printStackTrace();
	}	
	
	//dublin-core
	Path dublinCore = Paths.get(dir+handle+"/item_1/dublin_core.xml");
	List<String> dublin = Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dublin_core><dcvalue element=\"title\">"
			+ "qr_code_"+handle+"</dcvalue><dcvalue element=\"contributor\" qualifier=\"author\">"
			+ "tse</dcvalue><dcvalue element=\"date\">"
			+ "2018</dcvalue><dcvalue element=\"identifier\" qualifier=\"uri\">"
			+ url+"</dcvalue></dublin_core>");	
	try {
		Files.write(dublinCore, dublin, Charset.forName("UTF-8"));
	} catch (IOException e) {		
		e.printStackTrace();
	}	


}


private void criarDiretorio(final String diretorio) throws IOException{

    final Path path = Paths.get(diretorio);

    if(Files.notExists(path)){    
       //Files.createFile(Files.createDirectories(path));
    	 File dir = new File(diretorio);
    	 dir.mkdir();
   	System.out.println("Diretório \"" + diretorio + "\" criado.");
   	
    }   else{
    	System.out.println( diretorio + "\" já existe.");
    }
    
    
}

}
