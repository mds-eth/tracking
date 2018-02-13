package br.com.project.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Clase responsável por gerenciar campos de pesquisa do sistema de uma forma
 * genérica
 *
 * @author michael
 */
@Target(value = ElementType.FIELD)//para ser tratado apena em classes
@Retention(value = RetentionPolicy.RUNTIME)
@Documented

public abstract @interface IdentificaCampoPesquisa {

    String descricaoCampo();//descrição do campo para a tela

    String campoConsulta();// campo no  BD

    int principal() default 10000;//posição que irá aparecer no combo

}
