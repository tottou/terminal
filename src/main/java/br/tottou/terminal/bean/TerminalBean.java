package br.tottou.terminal.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.tottou.terminal.bean.util.AbstractBean;
import br.tottou.terminal.negocio.Console;
import br.tottou.terminal.util.Constantes;
import lombok.Getter;
import lombok.Setter;


@ManagedBean
@SessionScoped
public class TerminalBean extends AbstractBean {

	private static final long serialVersionUID = 1L;
	
	private @Getter @Setter Integer					abaPainel;
	
	private boolean auth = false;
	private String password;
	private List<String> saidaConsole = new ArrayList<String>();
	private List<String> saidaConsoleProc = new ArrayList<String>();
	private List<String> entradaConsoleProc = new ArrayList<String>();
	private String entrada;
	private String entradaProc;
	private String path;
	
	private String versao;
	

	

    public void autoriza (){
    	if (password.equals(Constantes.PASSWORD)){
    		auth=Boolean.TRUE;
    	}
    }	

	 
	 
	 public void exec(){
		 saidaConsole.addAll(Console.executa(entrada));
	 }
	 
	 public void limpar(){
		 saidaConsole = new ArrayList<String>();
	 }


	 // processo
	 
	 public void execProc(){
		 if (entradaConsoleProc.isEmpty()){
			 entradaConsoleProc.add("whoami");
		 }
		 saidaConsoleProc.addAll(Console.process(getEntradaConsoleProc(),path));
		 entradaConsoleProc = new ArrayList<String>();
		 
	 }
	 
	 public void add(){
		 entradaConsoleProc.add(entradaProc);
	 }
	 
	 
	 public void limparProc(){
		 saidaConsoleProc = new ArrayList<String>();
	 }
	 
	 public void limparEntrada(){
		entradaConsoleProc = new ArrayList<String>();
	 }
	 
	 
	 

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getSaidaConsole() {
		return saidaConsole;
	}

	public void setSaidaConsole(List<String> saidaConsole) {
		this.saidaConsole = saidaConsole;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public List<String> getSaidaConsoleProc() {
		return saidaConsoleProc;
	}

	public void setSaidaConsoleProc(List<String> saidaConsoleProc) {
		this.saidaConsoleProc = saidaConsoleProc;
	}

	public String getEntradaProc() {
		return entradaProc;
	}

	public void setEntradaProc(String entradaProc) {
		this.entradaProc = entradaProc;
	}

	public List<String> getEntradaConsoleProc() {
		return entradaConsoleProc;
	}

	public void setEntradaConsoleProc(List<String> entradaConsoleProc) {
		this.entradaConsoleProc = entradaConsoleProc;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersao() {
		versao=Constantes.VERSAO_SISTEMA;
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}
	
	
	
	
	
	
	
}