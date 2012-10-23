package br.com.sw2.gac.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.sw2.gac.util.ClassLoaderUtils;
import br.com.sw2.gac.util.LoggerUtils;

/**
 * Implementação servlet para impressão dos relatorios jasper no iframe global.
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
@WebServlet("/reportview")
public class JasperPrintServlet extends HttpServlet {

    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Atributo logger. */
    private LoggerUtils logger = LoggerUtils.getInstance(getClass());
    /**
     * Construtor Padrao Instancia um novo objeto JasperPrintServlet.
     * @see HttpServlet#HttpServlet()
     */
    public JasperPrintServlet() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        this.logger.debug("***** Iniciando método doGet do servlet reportview *****");

        Collection<?> beanCollection = (Collection<?>) request.getSession().getAttribute("beanCollection");
        Map<String, Object> beanParameters = (Map<String, Object>) request.getSession()
            .getAttribute("beanParameters");
        String jasperFile = (String) request.getSession().getAttribute("jasperFile");
        String urlBase = request.getScheme() + "://" + request.getServerName() + ":"
            + request.getServerPort() + request.getContextPath();

        // Seta o dataSource
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
            beanCollection);
        // Abre o arquivo .jasper contendo o relatorio
        InputStream inputStream = ClassLoaderUtils.getJasperFileAsStream(jasperFile);
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("LOGO_SMARTANGEL", urlBase
                + "/primefaces-smartangel/images/logo/smartangel-147x87.jpg");
            if (beanParameters != null) {
                parameters.putAll(beanParameters);
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parameters,
                beanCollectionDataSource);

            response.reset();
            response.setContentType("application/pdf");

            response.addHeader("Content-disposition", "inline; filename=relatorio.pdf");

            ServletOutputStream servletOutputStream = (ServletOutputStream) response
                .getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            this.logger.debug("***** Finalizado método doGet do servlet reportview *****");
        } catch (Exception e) {
            logger.error("Erro no processamento do Servlet de visualização de relatórios !");
            logger.error(e);
        }
    }
}