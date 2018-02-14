package br.com.project.listener;

import br.com.framework.utils.UtilFramework;
import br.com.project.model.classes.Entidade;
import br.com.project.model.classes.InformacaoRevisao;
import java.io.Serializable;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Service;

@Service
public class CustomListener implements RevisionListener, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    /**
     * intercepta qualquer comunicação da aplicação com o banco, pegando idUser
     * e gravando na tabela de revisão oq esta sendo feito
     */
    public void newRevision(Object revisionEntity) {

        InformacaoRevisao informavaoRevisao = (InformacaoRevisao) revisionEntity;
        Long codUser = UtilFramework.getThreadLocal().get();

        Entidade entidade = new Entidade();

        if (codUser != null && codUser != 0L) {
            entidade.setEnt_codigo(codUser);
            informavaoRevisao.setEntidade(entidade);

        }

    }

}
