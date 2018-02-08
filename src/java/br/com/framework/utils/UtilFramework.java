package br.com.framework.utils;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 *
 * @author michael
 */
@Component
public class UtilFramework implements Serializable {

    private static final long serialVersionUID = 1L;

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    /**
     * classe responsavel por identifcar o usuário que esta inserindo,
     * cadastrado, editando dados no bd
     *
     * @return threadLocal atual
     */
    public synchronized static ThreadLocal<Long> getThreadLocal() {
        return threadLocal;

    }
}
