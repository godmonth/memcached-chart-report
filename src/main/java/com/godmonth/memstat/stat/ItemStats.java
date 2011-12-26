package com.godmonth.memstat.stat;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.godmonth.memstat.model.Item;

public class ItemStats {
	private List<Item> items;
	private final long createTime;

	public ItemStats(List<Item> items, long createTime) {
		this.items = items;
		this.createTime = createTime;
	}

	public JFreeChart createBarEvict(String title) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Item item : items) {
			try {
				Long amount = (Long) PropertyUtils.getProperty(item, "evicted");
				dataset.addValue(amount, "evicted",
						String.valueOf(item.getChunkSize()));
			} catch (Exception e) {
			}

		}
		return ChartFactory.createBarChart(title, // chart
				"chunksize", // domain axis label
				"amount", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
	}

	public JFreeChart createBarAgeEt(String title) {
		long current = System.currentTimeMillis() / 1000;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Item item : items) {
			try {
				Long seconds = (Long) PropertyUtils.getProperty(item, "age");
				long period = current - (createTime + seconds);
				int days = (int) (period / 60 / 60 / 24);
				dataset.addValue(new Integer(days), "age",
						String.valueOf(item.getChunkSize()));

				Long duration = (Long) PropertyUtils.getProperty(item,
						"evictedTime");
				dataset.addValue(
						new Integer(duration.intValue() / 60 / 60 / 24),
						"evictedTime", String.valueOf(item.getChunkSize()));
			} catch (Exception e) {
			}

		}
		return ChartFactory.createBarChart(title, // chart
				"chunksize", // domain axis label
				"day", // range axis label
				dataset, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
	}

}
