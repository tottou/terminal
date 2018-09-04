package br.tottou.terminal.bean.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import br.tottou.terminal.util.Msg;
import br.tottou.terminal.util.Util;


@SuppressWarnings("serial")
public class AbstractBean implements Serializable {
	
	public static final String MSG_CAMPO_OBRIGATORIO = "O campo %s é de preenchimento obrigatório";	
	
	protected void adicionarMensagemSucessoGrowl(String idGrowl, String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		context.addMessage(idGrowl,  new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, null));
		irTopoTela();
	}
	
	protected void adicionarMensagemErroGrowl(String idGrowl, String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		context.addMessage(idGrowl,  new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, null));
		irTopoTela();
	}
	
	protected void adicionarMensagemAlertaGrowl(String idGrowl, String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance(); 
		context.addMessage(idGrowl,  new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, null));
		irTopoTela();
	}
	
	
	protected void adicionarMensagemAlerta(String mensagem, String... parametros) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", getMensagem(mensagem, parametros)));
	}
	
	protected void adicionarMensagemAlerta(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", getMensagem(mensagem)));
	}
	
	protected void adicionarMensagemSucesso(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMensagem(mensagem)));
	}
	
	protected void adicionarMensagemSucesso(String mensagem, String... parametros) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", getMensagem(mensagem, parametros)));
	}

	protected void adicionarMensagemErroFatal(String mensagem, Throwable e) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensagem(mensagem) + "[" + e.getMessage() + "]"));
	}

	protected void adicionarMensagemErro(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensagem(mensagem)));
	}
	
	protected void adicionarMensagemErro(String mensagem, String... parametros) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensagem(mensagem, parametros)));
	}
	

	protected void adicionarMensagemErro(List<String> componentID, String mensagem, String form) { 
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", getMensagem(mensagem)));
		context.validationFailed();
		
		for (String id : componentID) {
			UIInput base = (UIInput) context.getViewRoot().findComponent(form+":"+id);	
			
			if (base != null) {			
				base.setValid(false);
			}		
		}
	}
	
	protected void adicionarMensagemCampo(String id, Severity severity, String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(id, new FacesMessage(severity, mensagem, null));
		context.validationFailed();

		UIInput base = (UIInput) context.getViewRoot().findComponent(id);
		
		if (base != null) {
			base.setValid(false);
			base.setValidatorMessage(mensagem);
		}
	}
	
	protected void adicionarMarcacaoCampoObrigatorio(String id) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.validationFailed();
		UIInput base = (UIInput) context.getViewRoot().findComponent(id);
		if (base != null) {
			base.setValid(false);			
		}	
	}
	
	protected void adicionarMensagemCampoObrigatorio(String id, String nomeCampo) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(id, new FacesMessage(FacesMessage.SEVERITY_ERROR, String.format(MSG_CAMPO_OBRIGATORIO, nomeCampo), null));
		context.validationFailed();

		UIInput base = (UIInput) context.getViewRoot().findComponent(id);
		if (base != null) {
			base.setValid(false);
			base.setValidatorMessage(String.format(MSG_CAMPO_OBRIGATORIO, nomeCampo));
		}
	}

	
	protected String getMensagem(String mensagem) {
		if(mensagem.contains("%")){
			List<String> mensagemParamentro = Arrays.asList(mensagem.split("%"));
			return Msg.getMensagem(mensagemParamentro.get(0), mensagemParamentro.get(1));
		}
		
		return Msg.getMensagem(mensagem);
	}
	
	protected String getMensagem(String mensagem, String... parametros) {
		return Msg.getMensagem(mensagem, parametros);
	}
	

	protected void atualizarComponente(String idComponente) {
		RequestContext.getCurrentInstance().update(idComponente);
//		PrimeFaces.current().ajax().update(idComponente);
	}
	
	protected void abrirDialog(String idDialog) {
		RequestContext.getCurrentInstance().execute("PF('"+ idDialog + "').show()");
//		PrimeFaces.current().executeScript("PF('"+ idDialog + "').show()");
	}
	
	protected void fecharDialog(String idDialog) {
		RequestContext.getCurrentInstance().execute("PF('"+ idDialog + "').hide()");
//		PrimeFaces.current().executeScript("PF('"+ idDialog + "').hide()");
	}
	
	protected void alterarTituloDialog(String widgetWar, String titulo) {
		RequestContext.getCurrentInstance().execute("PF('"+widgetWar+"').getJQ().find('.ui-dialog-title').text('"+titulo+"')");
//		PrimeFaces.current().executeScript("PF('"+widgetWar+"').getJQ().find('.ui-dialog-title').text('"+titulo+"')");
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T getBean(String name) {
		if (FacesContext.getCurrentInstance() == null){
			return null;
		}
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, name);
	}
	
	@SuppressWarnings("unchecked")
	protected static <T> T getBean(String beanName, Class<T> clazz) {
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, beanName);
	}
	
	protected Object getValueComEL(String exp) {
		ValueExpression valueExpression =  FacesContext.getCurrentInstance().getApplication().getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(),"#{" + exp + "}", Object.class);
		return valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
	}
	
	protected final void irTopoTela() {
		RequestContext.getCurrentInstance().execute("window.location = '#'");
	}
	
	protected void executarScript(String script) {
		RequestContext.getCurrentInstance().execute(script);
	}
	
	public void redirecionaPagina(String url) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect(context.getRequestContextPath() + url);
	}
	

	
	/*
	 * VALIDAR INTRANET , INTERNET, LOCALHOST
	 * 
	 */
	

	protected String getRequestParametro(String parametro) {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		return params.get(parametro);
	}
	
	protected Boolean getParametroTRUE(String parametro) {
		String valor = getRequestParametro(parametro);
		
		if (Util.isNullOuBranco(valor)) {
			return Boolean.FALSE;
		} else {
			return new Boolean(valor);
		}
		
	}
	
	protected String getValorComponenteEvento(FacesEvent event) {
		UIInput componente =  (UIInput) event.getSource();
		return (String) componente.getValue();
	}
	
	
	/*
	 * REQUEST e RESPONDE
	 */
	protected HttpServletRequest getRequest() {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) extContext.getRequest();
		return request;
	}
	
	protected HttpServletResponse getResponse() {
		ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) extContext.getResponse();
		return response;
	}
	
	
}