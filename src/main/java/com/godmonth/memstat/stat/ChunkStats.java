package com.godmonth.memstat.stat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.TableOrder;

import com.godmonth.memstat.model.Chunk;

public class ChunkStats {
	private List<Chunk> datas;

	public ChunkStats(List<Chunk> datas) {
		this.datas = datas;
	}

	public Map<Long, Long> getIdSizeMappings() {
		Map<Long, Long> result = new HashMap<Long, Long>();
		for (Chunk chunk : datas) {
			result.put(chunk.getId(), chunk.getChunkSize());
		}
		return result;
	}

	public JFreeChart createMultiPie(String title, String... propertyNames) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Chunk chunk : datas) {
			String row = String.valueOf(chunk.getChunkSize());
			for (String propertyName : propertyNames) {
				try {
					dataset.addValue((Long) PropertyUtils.getProperty(chunk,
							propertyName), row, propertyName);
				} catch (Exception e) {
				}
			}

		}
		return ChartFactory.createMultiplePieChart3D(title, dataset,
				TableOrder.BY_COLUMN, true, true, false);
	}

	public JFreeChart createPie(String propertyName) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Chunk chunk : datas) {
			try {
				dataset.setValue(chunk.getChunkSize() + "",
						(Long) PropertyUtils.getProperty(chunk, propertyName));
			} catch (Exception e) {
			}
		}
		return ChartFactory.createPieChart3D("chunk " + propertyName, dataset,
				true, // legend?
				true, // tooltips?
				false // URLs?
				);
	}

	public JFreeChart createBarChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Chunk chunk : datas) {
			String row = chunk.getId() + ":" + chunk.getChunkSize();
			dataset.addValue(chunk.getTotalChunks(), row, "totalChunks");
			dataset.addValue(chunk.getMemRequested(), row, "mmRequested");
		}
		return ChartFactory.createBarChart("Bar Chart Demo", // chart
																// title
				"Category", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
	}
}
