package br.com.sw2.gac.jasper;

import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRChartCustomizer;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;

/**
 * <b>Descri��o:</b> <br>
 * .
 * @author: SW2
 * @version 1.0 Copyright 2012 SmartAngel.
 */
public class PieLabels implements JRChartCustomizer {

    /**
     * {@inheritDoc}
     */
    public void customize(JFreeChart jFreeChart, JRChart jrChart) {

        PiePlot piePlot = (PiePlot) jFreeChart.getPlot();
        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
                "{1}");
        piePlot.setLabelGenerator(labelGenerator);
    }
}
