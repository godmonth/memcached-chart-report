package com.godmonth.memstat.inbound;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.memstat.model.Item;
import com.godmonth.util.StringConverter;

public class ItemFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(ItemFactory.class);

	public static List<Item> read(Map<String, String> stats,
			Map<Long, Long> idSizeMappings) {
		Map<String, Item> items = new HashMap<String, Item>();

		for (String string : stats.keySet()) {
			String[] sign = StringUtils.split(string, ':');
			if (sign.length != 3) {
				logger.trace(string);
				continue;
			}

			String key = sign[1];
			Item item = items.get(key);
			if (item == null) {
				item = new Item();
				item.setId(Long.parseLong(key));
				item.setChunkSize(idSizeMappings.get(item.getId()));
				items.put(key, item);
			}

			try {
				BeanUtils.setProperty(item,
						StringConverter.dbColumn2BeanProperty(sign[2]),
						stats.get(string));
			} catch (Exception e) {
			}

		}
		ArrayList<Item> arrayList = new ArrayList<Item>(items.values());
		Collections.sort(arrayList, new BeanComparator("id"));
		return arrayList;
	}

	public static List<Item> read(List<String> readLines,
			Map<Long, Long> idSizeMappings) {
		Map<String, Item> items = new HashMap<String, Item>();

		for (String string : readLines) {
			String[] aaa = StringUtils.split(string, ':');
			if (aaa.length != 3) {
				logger.trace(string);
				continue;
			}

			String key = aaa[1];
			Item item = items.get(key);
			if (item == null) {
				item = new Item();
				item.setId(Long.parseLong(key));
				item.setChunkSize(idSizeMappings.get(item.getId()));
				items.put(key, item);
			}
			String[] split = aaa[2].split(" ");
			if (split.length != 2) {
				if (logger.isTraceEnabled()) {
					logger.trace(ArrayUtils.toString(split));
				}
			} else {
				try {
					BeanUtils.setProperty(item,
							StringConverter.dbColumn2BeanProperty(split[0]),
							split[1]);
				} catch (Exception e) {
				}
			}

		}
		ArrayList<Item> arrayList = new ArrayList<Item>(items.values());
		Collections.sort(arrayList, new BeanComparator("id"));
		return arrayList;
	}

	public static List<Item> read(InputStream is, Map<Long, Long> idSizeMappings)
			throws FileNotFoundException, IOException {
		return read(IOUtils.readLines(is), idSizeMappings);
	}
}
