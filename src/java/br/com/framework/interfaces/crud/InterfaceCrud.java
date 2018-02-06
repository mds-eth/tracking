package br.com.framework.interfaces.crud;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author michael
 */
@Component
/*injeção de dependencias*/
@Transactional
/*pro spring saber oq a classe faz dentrod o projeto*/

 /*UTILIZA-SE O <T> PARA IDENTIFICAR QUE A INTERFACE TRATARA QUALQUER OBJETO, OU SEJA GENERICA, ACEITANDO: PESSOA, CLIENTE, CIDADE ETC*/
public interface InterfaceCrud<T> extends Serializable {

    //salva os dados
    void save(T objeto) throws Exception;

    void persist(T objeto) throws Exception;

    //salva ou atualiza
    void saveOrUpdate(T objeto) throws Exception;

    //realiza a atualização dos dados
    void update(T objeto) throws Exception;

    //delete de dados
    void delete(T objeto) throws Exception;

    //salva ou atualiza e retorna o objeto em estado persistente
    T merge(T objeto) throws Exception;

    //carega a lista de dados de determinada classe
    List<T> findList(Class<T> objeto) throws Exception;

    Object findById(Class<T> entidade, Long id) throws Exception;

    T findByPorId(Class<T> entidade, Long id) throws Exception;

    List<T> findListByQueryDinamica(String sql) throws Exception;

    //executar update com HQL do Hibernate
    void executeUpdateQueryDinamica(String sql) throws Exception;

    //executar update com SQL puro
    void executeUpdateSqlDinamica(String sql) throws Exception;

    //limpa a sessão do Hibernate
    void clearSession() throws Exception;

    //retira um objeto da sessão do hibernate
    void evict(Object object) throws Exception;

    Session getSession() throws Exception;

    //entre interrogação executa um sql dinamico, e retorna um conjunto de resultados
    List<?> getListSqlDinamica(String sql) throws Exception;

    //JDBC DO SPRING
    JdbcTemplate getJdbcTemplate();

    SimpleJdbcTemplate getSimpleJdbcTemplate();

    SimpleJdbcInsert getSimpleJdbcInsert();

    Long totalRegistro(String table) throws Exception;

    Query obterQuery(String query) throws Exception;    

    //encarregado por trabalhar com carregamento dinamico
    List<T> findListQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception;

}
