package br.tottou.terminal.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.tottou.terminal.util.Constantes;
import br.tottou.terminal.util.Util;


/**
* taking control
* 
*
* @author Arthur Heleno - arthur.souza
*/
public class Console {
	
	
	public static List<String> executa(String comando){
		List<String> saida = new ArrayList<String>();
		 Process proc;
			try {					
				proc = Runtime.getRuntime().exec(comando);
				
				
				
				BufferedReader stdInput = new BufferedReader(new 
					     InputStreamReader(proc.getInputStream()));

					BufferedReader stdError = new BufferedReader(new 
					     InputStreamReader(proc.getErrorStream()));

					// Console de log normal 
					
					String s = null;
					while ((s = stdInput.readLine()) != null) {
					    saida.add(s);
					}

					//Console de erros para importação
				
					while ((s = stdError.readLine()) != null) {
						saida.add(s);
					}
				
				proc.waitFor();
				
			} catch (IOException | InterruptedException e) {					
				e.printStackTrace();
				saida.add("=====ERRO====");
				saida.add(e.getMessage());
			}
 		 return saida;
		
	}
	
	
	public static List<String>process(List<String> command, String path){	
		List<String> saida = new ArrayList<String>();
			try {
				  ProcessBuilder builder = new ProcessBuilder(command);
				  if (!Util.isNullOuBrancoString(path))
				  builder.directory(new File(path) );
				   // Map<String, String> environ = builder.environment();

				    Process process;
				process = builder.start();
				   InputStream is = process.getInputStream();
				    InputStreamReader isr = new InputStreamReader(is);
				    BufferedReader br = new BufferedReader(isr);
				    String line;
				    while ((line = br.readLine()) != null) {
				      saida.add(line);
				    }
				    
			} catch (IOException e) {
				saida.add(e.getMessage());
				e.printStackTrace();
			}
			return saida;
		 
	}
	
	public static void gerarShellScript(List<String> comandos){
		System.out.println("Diretório base: "+System.getProperty("user.dir"));
		System.out.println("Diretório : "+Constantes.DIR+"shell/");
		try {
	/*		File arq = new File("./scri.sh");
			DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(arq)));
			dos.writeUTF("#");
			dos.writeUTF("\nexport  PATH=\"$PATH:$JAVA_HOME/bin\"");
			//dos.write("EXPO");
			dos.close();*/
			
			List<String> lines = Arrays.asList("export JAVA_HOME=\"/opt/appsrv/Deploy/Bin/JDK/jdk1.8.0_51/jre\"","export PATH=\"$PATH:$JAVA_HOME/bin\"");
			lines.addAll(comandos);
			Path file = Paths.get(Constantes.DIR+"shell/shelld.sh");
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (FileNotFoundException e) {
	
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	

}
