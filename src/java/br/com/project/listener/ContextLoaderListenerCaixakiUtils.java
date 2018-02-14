package br.com.project.listener;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@ApplicationScoped
public class ContextLoaderListenerCaixakiUtils extends ContextLoaderListener implements Serializable {

    private static final long serialVersionUID = 1L;

    private static WebApplicationContext getWc() {

        //retorna todo o contexto do spring em ambiente de execução
        return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
    }

    public static Object getBean(String idNomeBean) {

        return getWc().getBean(idNomeBean);
    }

    public static Object getBean(Class<?> classe) {

        return getWc().getBean(classe);
    }

}
