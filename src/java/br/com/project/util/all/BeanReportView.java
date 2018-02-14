/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.project.util.all;

import br.com.project.report.util.ReportUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por chamar o ReportUtil, para facilitar o trabalho. O
 * método getArquivoReport() é que faz toda a chamada com a classe ReportUtil
 *
 * @author michael
 */
@Component
public abstract class BeanReportView extends BeanViewAbstract {

    private static final long serialVersionUID = 1L;

    protected StreamedContent arquivoReport;
    protected int tipoRelatorio;
    protected List<?> listDataBeanCollectionReport;
    protected HashMap<Object, Object> parametrosRelatorio;
    protected String nomeRelatorioJasper = "default";
    protected String nomeRelatorioSaida = "default";

    @Resource
    private ReportUtil reportUtil;

    public BeanReportView() {

        parametrosRelatorio = new HashMap<Object, Object>();
        listDataBeanCollectionReport = new ArrayList();
    }

    public ReportUtil getReportUtil() {

        return reportUtil;
    }

    public void setReportUtil(ReportUtil reportUtil) {

        this.reportUtil = reportUtil;
    }

    public StreamedContent getArquivoReport() throws Exception {

        /**
         * Classe responsavel por chamar o ReportUtil que ira gerar os
         * relatorios
         *
         * @param todos os params que sao obrigatórios na classe geraRelatorio
         * para que possa ser manipulado e exibido o relatório
         */
        return getReportUtil().geraRelatorio(getListDataBeanCollectionReport(), getParametrosRelatorio(), getNomeRelatorioJasper(), getNomeRelatorioSaida(), getTipoRelatorio());
    }

    public int getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(int tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public List<?> getListDataBeanCollectionReport() {
        return listDataBeanCollectionReport;
    }

    public void setListDataBeanCollectionReport(List<?> listDataBeanCollectionReport) {
        this.listDataBeanCollectionReport = listDataBeanCollectionReport;
    }

    public HashMap<Object, Object> getParametrosRelatorio() {
        return parametrosRelatorio;
    }

    public void setParametrosRelatorio(HashMap<Object, Object> parametrosRelatorio) {
        this.parametrosRelatorio = parametrosRelatorio;
    }

    public String getNomeRelatorioJasper() {
        return nomeRelatorioJasper;
    }

    public void setNomeRelatorioJasper(String nomeRelatorioJasper) {

        /*para caso do nome do relatorio vir errado, ira ser gerado um relatorio default*/
        if (nomeRelatorioJasper == null || nomeRelatorioJasper.isEmpty()) {
            nomeRelatorioJasper = "default";
        }
        this.nomeRelatorioJasper = nomeRelatorioJasper;
    }

    public String getNomeRelatorioSaida() {
        return nomeRelatorioSaida;
    }

    public void setNomeRelatorioSaida(String nomeRelatorioSaida) {

        if (nomeRelatorioSaida == null || nomeRelatorioSaida.isEmpty()) {
            nomeRelatorioSaida = "default";
        }
        this.nomeRelatorioSaida = nomeRelatorioSaida;
    }

}
