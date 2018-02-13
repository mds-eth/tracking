package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Classe responsável por gerenciar combos na tela
 *
 * @author michael
 */
public class ObjetoCampoConsulta implements Serializable, Comparator<ObjetoCampoConsulta>/*comparator utilizado para ordenar listas*/ {

    private static final long serialVersionUID = 1L;

    private String descricao;//descrição do campo
    private String campoBanco;//campo do Banco
    private Object tipoClass;//saber qual é a classe manipulada no momento
    private Integer principal;//saber qual a ordem para se mostrar no combo

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCampoBanco() {
        return campoBanco;
    }

    public void setCampoBanco(String campoBanco) {
        this.campoBanco = campoBanco;
    }

    public Object getTipoClass() {
        return tipoClass;
    }

    public void setTipoClass(Object tipoClass) {
        this.tipoClass = tipoClass;
    }

    public Integer getPrincipal() {
        return principal;
    }

    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }

    @Override
    public int compare(ObjetoCampoConsulta o1, ObjetoCampoConsulta o2) {
        return ((ObjetoCampoConsulta) o1).getPrincipal().compareTo(((ObjetoCampoConsulta) o2).getPrincipal());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.campoBanco);
        return hash;
    }

    //serve para mostrar a descrição do objeto na tela
    @Override
    public String toString() {
        return getDescricao();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjetoCampoConsulta other = (ObjetoCampoConsulta) obj;
        if (!Objects.equals(this.campoBanco, other.campoBanco)) {
            return false;
        }
        return true;
    }

}
