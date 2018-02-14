package br.com.framework.hibernate.session;

import br.com.framework.implementacao.crud.VariavelconexaoUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.faces.bean.ApplicationScoped;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.engine.SessionFactoryImplementor;

/**
 * Responsavel por estabelecer a conexão com o hibernate
 *
 * @author michael
 */
@ApplicationScoped
public class HibernateUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE = "java:/comp/env/jdbc/datasource";

    private static SessionFactory sessionFactory = buildSessionFactory();

    /**
     * responsavel por ler o arquivo de configuracao hibernate.cfg.xml
     *
     * @return SessionFactory
     */
    private static SessionFactory buildSessionFactory() {

        try {

            if (sessionFactory == null) {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            }

            return sessionFactory;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Erro ao criar conexão sessionFacotory");

        }
    }

    /**
     * Retorna o sessionFactory corrente
     *
     * @return SessionFactory
     *
     */
    public static SessionFactory getSessionFactory() {

        return sessionFactory;
    }

    /**
     * Responsavel por retornar a sessão do SessionFactory
     *
     * @return Session
     *
     */
    public static Session getCurrenteSession() {

        return getSessionFactory().getCurrentSession();
    }

    /**
     * Abre uma nova sessão no SessionFactory
     *
     * @return Session
     */
    public static Session openSession() {

        if (sessionFactory == null) {
            buildSessionFactory();
        }

        return sessionFactory.openSession();
    }

    /**
     * Obtem a conextion do provedor de conexões configurado
     *
     * @return Connetion SQL
     * @throws SQLException
     */
    public static Connection getConnectionProvider() throws SQLException {

        return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
    }

    /**
     * @return Connection no InitialContext java:/comp/env/jdbc/datasource
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {

        InitialContext context = new InitialContext();
        DataSource ds = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);

        //caso de pau ao retornar método, verificar pacote associado
        return ds.getConnection();
    }

    /**
     *
     * @return DataSource JNDI Tomcat
     * @throws NamingException
     */
    public DataSource getDataSourceJndi() throws NamingException {

        InitialContext context = new InitialContext();
        return (DataSource) context.lookup(VariavelconexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
    }

}
