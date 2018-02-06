/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.framework.implementacao.crud;

import java.io.Serializable;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author michael
 */
@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)

public class SimpleJdbcClassImpl extends SimpleJdbcCall implements Serializable {

    private static final long serialVersionUID = 1L;

    public SimpleJdbcClassImpl(DataSource dataSource) {
        super(dataSource);

    }

}
