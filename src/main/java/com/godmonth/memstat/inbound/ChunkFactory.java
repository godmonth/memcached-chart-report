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

import com.godmonth.memstat.model.Chunk;
import com.godmonth.util.StringConverter;

public class ChunkFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(ChunkFactory.class);

	public static List<Chunk> read(Map<String, String> stats) {
		Map<String, Chunk> chunks = new HashMap<String, Chunk>();

		for (String string : stats.keySet()) {
			String[] sign = StringUtils.split(string, ':');
			if (sign.length != 2) {
				logger.trace(string);
				continue;
			}

			String key = sign[0];
			Chunk chunk = chunks.get(key);
			if (chunk == null) {
				chunk = new Chunk();
				chunk.setId(Long.parseLong(key));

				chunks.put(key, chunk);
			}

			try {
				BeanUtils.setProperty(chunk,
						StringConverter.dbColumn2BeanProperty(sign[1]),
						stats.get(string));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		ArrayList<Chunk> arrayList = new ArrayList<Chunk>(chunks.values());

		Collections.sort(arrayList, new BeanComparator("id"));
		return arrayList;
	}

	public static List<Chunk> read(List<String> readLines) {
		Map<String, Chunk> chunks = new HashMap<String, Chunk>();

		for (String string : readLines) {
			String[] sign = StringUtils.split(string, ':');
			if (sign.length != 2) {
				logger.trace(string);
				continue;
			}

			String key = sign[0];
			Chunk chunk = chunks.get(key);
			if (chunk == null) {
				chunk = new Chunk();
				chunk.setId(Long.parseLong(StringUtils.substringAfter(key,
						"STAT ")));

				chunks.put(key, chunk);
			}
			String[] split = sign[1].split(" ");
			if (split.length != 2) {
				if (logger.isTraceEnabled()) {
					logger.trace(ArrayUtils.toString(split));
				}
			} else {
				try {
					BeanUtils.setProperty(chunk,
							StringConverter.dbColumn2BeanProperty(split[0]),
							split[1]);
				} catch (Exception e) {
				}
			}

		}
		ArrayList<Chunk> arrayList = new ArrayList<Chunk>(chunks.values());
		Collections.sort(arrayList, new BeanComparator("id"));
		return arrayList;
	}

	public static List<Chunk> read(InputStream is)
			throws FileNotFoundException, IOException {
		return read(IOUtils.readLines(is));

	}
}
