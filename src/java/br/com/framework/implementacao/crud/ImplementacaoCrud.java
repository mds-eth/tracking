package br.com.framework.implementacao.crud;

import br.com.framework.hibernate.session.HibernateUtil;
import br.com.framework.interfaces.crud.InterfaceCrud;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T> {

    private static final long serialVersionUID = 1L;

    private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Autowired
    private JdbcTemplateImpl jdbcTemplate;

    @Autowired
    private SimpleJdbcTemplateImpl simpleJdbTempĺate;

    @Autowired
    private SimpleJdbcInsertImpl simpleJdbcInsert;

    @Autowired
    private SimpleJdbcClassImpl simpleJdbcCallImpl;

    @Override
    public void save(T objeto) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().save(objeto);
        executeFlushSession();

    }

    @Override
    public void persist(T objeto) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().persist(objeto);
        executeFlushSession();
    }

    @Override
    public void saveOrUpdate(T objeto) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().saveOrUpdate(objeto);
        executeFlushSession();
    }

    @Override
    public void update(T objeto) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().update(objeto);
        executeFlushSession();

    }

    @Override
    public void delete(T objeto) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().delete(objeto);
        executeFlushSession();

    }

    @Override
    public T merge(T objeto) throws Exception {

        validaSessionFactory();
        objeto = (T) sessionFactory.getCurrentSession().merge(objeto);
        executeFlushSession();
        return objeto;

    }

    @Override
    public List<T> findList(Class<T> entidade) throws Exception {

        validaSessionFactory();

        StringBuilder query = new StringBuilder();
        query.append(" SELECT distinct(entity) FROM ").append(entidade.getSimpleName()).append(" entity");

        List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list();

        return lista;

    }

    @Override
    public Object findById(Class<T> entidade, Long id) throws Exception {

        validaSessionFactory();
        Object objeto = sessionFactory.getCurrentSession().load(getClass(), id);

        return objeto;

    }

    @Override
    public T findByPorId(Class<T> entidade, Long id) throws Exception {

        validaSessionFactory();
        T objeto = (T) sessionFactory.getCurrentSession().load(getClass(), id);

        return objeto;
    }

    @Override
    public List<T> findListByQueryDinamica(String sql) throws Exception {

        validaSessionFactory();
        List<T> lista = new ArrayList<T>();
        lista = sessionFactory.getCurrentSession().createQuery(sql).list();

        return lista;

    }

    @Override
    public void executeUpdateQueryDinamica(String sql) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().createQuery(sql).executeUpdate();
        executeFlushSession();

    }

    @Override
    public void executeUpdateSqlDinamica(String sql) throws Exception {

        validaSessionFactory();

        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();

        executeFlushSession();
    }

    @Override
    public void clearSession() throws Exception {

        sessionFactory.getCurrentSession().clear();

    }

    @Override
    public void evict(Object object) throws Exception {

        validaSessionFactory();
        sessionFactory.getCurrentSession().evict(object);
    }

    @Override
    public Session getSession() throws Exception {

        validaSessionFactory();

        return sessionFactory.getCurrentSession();

    }

    @Override
    public List<?> getListSqlDinamica(String sql) throws Exception {

        validaSessionFactory();

        List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();

        return lista;

    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcTemplate getSimpleJdbcTemplate() {
        return simpleJdbTempĺate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleJdbcInsert;
    }

    public SimpleJdbcClassImpl getSimpleJdbcClassImpl() {
        return simpleJdbcCallImpl;
    }

    @Override
    public Long totalRegistro(String table) throws Exception {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT count(1) FROM ").append(table);

        return jdbcTemplate.queryForLong(sql.toString());
    }

    @Override
    public Query obterQuery(String query) throws Exception {

        validaSessionFactory();
        Query queryReturn = sessionFactory.getCurrentSession().createQuery(query.toString());

        return queryReturn;

    }

    /**
     * Realiza consulta no banco de dados, iniciando o carregamento a partir do
     * registro passado no parametro -> iniciaNoRegisttro e obtendo maximo de
     * resultados passados em -> maximoResultado
     *
     * @param query
     * @param iniciaNoRegistro
     * @param maximoResultado
     * @return list<T>
     * @throws Exception
     */
    @Override
    public List<T> findListQueryDinamica(String query, int iniciaNoRegistro, int maximoResultado) throws Exception {

        validaSessionFactory();
        List<T> lista = new ArrayList<T>();
        lista = sessionFactory.getCurrentSession().createQuery(query).setFirstResult(iniciaNoRegistro).setMaxResults(maximoResultado).list();

        return lista;

    }

    /**
     * VERIFICA SE HA UMA TRANSAÇÃO ATIVA NO HIBERNATE
     */
    private void validaTransaction() {

        if (!sessionFactory.getCurrentSession().getTransaction().isActive()) {
            sessionFactory.getCurrentSession().beginTransaction();
        }
    }

    /*método para trabalhar futuramente com jquery*/
    private void commitProcessoAjax() {
        sessionFactory.getCurrentSession().beginTransaction().commit();
    }

    /*dar rollback em processos ajax*/
    private void rollBackProcessoAjax() {
        sessionFactory.getCurrentSession().beginTransaction().rollback();
    }

    private void validaSessionFactory() {

        if (sessionFactory == null) {

            sessionFactory = HibernateUtil.getSessionFactory();
        }

        validaTransaction();
    }

    /**
     * Roda instantaneamento o SQL no banco de dados
     */
    private void executeFlushSession() {

        sessionFactory.getCurrentSession().flush();

    }

    public List<Object[]> getListSQLDinamicaArray(String sql) throws Exception {

        validaSessionFactory();

        List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();

        return lista;

    }

}
