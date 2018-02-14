package br.com.project.exception;

import br.com.framework.hibernate.session.HibernateUtil;
import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

/**
 * Classe responsável pelo tratamento de Exceções no projeto
 *
 * @author michael
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapperd;
    final FacesContext facesContext = FacesContext.getCurrentInstance();
    final Map<String, Object> requesMap = facesContext.getExternalContext().getRequestMap();

    final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

    public CustomExceptionHandler(ExceptionHandler excepctionHandler) {

        this.wrapperd = excepctionHandler;
    }

    //sobrescreve o método ExceptionHandler que retorna a "pilha" de exceções
    @Override
    public ExceptionHandler getWrapped() {
        return wrapperd;
    }

    //sobrescreve o método handle que é responsavel por manipular as exceções do JSF
    @Override
    public void handle() throws FacesException {

        final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

        while (iterator.hasNext()) {

            ExceptionQueuedEvent event = iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            //Recuperar a exceção do contexto
            Throwable exception = context.getException();

            //aqui manipulamos a exceçção
            try {

                requesMap.put("exceptionMessage", exception.getMessage());

                if (exception != null && exception.getMessage() != null && exception.getMessage().indexOf("ContraintViolationException") != -1) {

                    FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, "Registro não pode ser removido por estar associado.", ""));
                } else if (exception != null && exception.getMessage() != null && exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {

                    FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro foi atualizado ou excluído por outro usuário. Consulte novamente.", ""));
                } else {
                    //avisa o usuário do erro
                    FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "O sistema de recuperou de um erro inesperado.", ""));

                    //tranquiliza o danado para que ele continue usando o sistema
                    FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Você pode continuar usando o sistema normalmente!.", ""));

                    FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, "O erro foi causado por:\n." + exception.getMessage(), ""));

                    //primefaces
                    //alert em javascript se a pagina nao redirecionar
                    RequestContext.getCurrentInstance().execute("alert('O sistema se recuperou de um erro inesperado')");

                    //aqui abre janelinha do primefaces caso haja necessidade
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "O sistema se recuperou de um erro inesperado"));

                    //jsf redireciona para pagina de erro
                    navigationHandler.handleNavigation(facesContext, null, "error/error.jsf?faces-redirect=true&expired=true");
                }

                //renderiza a pagina de erro e exibe as mensagens
                facesContext.renderResponse();

            } finally {

                SessionFactory sf = HibernateUtil.getSessionFactory();
                if (sf.getCurrentSession().getTransaction().isActive()) {

                    sf.getCurrentSession().getTransaction().rollback();

                }

                //imprime o erro no console.
                exception.printStackTrace();
                iterator.remove();
            }

        }

        getWrapped().handle();

    }

}
