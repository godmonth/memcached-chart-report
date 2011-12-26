package com.godmonth.memstat.outbound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.MultiplePiePlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.util.Rotation;

public class Output {
	

	public static void write2File(JFreeChart chart, String file, int width,
			int height) throws FileNotFoundException, IOException {
		FileUtils.deleteQuietly(new File(file));
		ChartUtilities.writeChartAsPNG(new FileOutputStream(file), chart,
				width, height);
	}

	public static void writePngOnePie(JFreeChart chart, String file, int width,
			int height) throws FileNotFoundException, IOException {
		setLabelAndLegend((PiePlot) chart.getPlot());
		write2File(chart, file, width, height);
	}

	private static void setLabelAndLegend(PiePlot plot) {
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{1}:{2}"));
		plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
				"size:{0}"));
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		// plot.setLabelFont(new Font("雅黑", Font.PLAIN, 16));

	}

	public static void writePngMultiPie(JFreeChart chart, String file,
			int width, int height) throws FileNotFoundException, IOException {
		MultiplePiePlot plot = (MultiplePiePlot) chart.getPlot();
		JFreeChart subchart = plot.getPieChart();
		setLabelAndLegend((PiePlot) subchart.getPlot());

		write2File(chart, file, width, height);
	}
}
