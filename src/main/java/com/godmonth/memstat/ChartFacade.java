package com.godmonth.memstat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.jfree.chart.JFreeChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.memstat.inbound.ChunkFactory;
import com.godmonth.memstat.inbound.Gather;
import com.godmonth.memstat.inbound.ItemFactory;
import com.godmonth.memstat.model.Chunk;
import com.godmonth.memstat.model.Item;
import com.godmonth.memstat.stat.ChunkStats;
import com.godmonth.memstat.stat.ItemStats;

public class ChartFacade {
	public static enum Type {
		age, multiPie, evit;
	}

	public static JFreeChart createJfc(Type type, InetSocketAddress address)
			throws IOException, MemcachedException, InterruptedException,
			TimeoutException {
		if (Type.age.equals(type)) {
			return createAge(address);
		} else if (Type.multiPie.equals(type)) {
			return createMultiPie(address);
		} else if (Type.evit.equals(type)) {
			return createEvit(address);
		} else {
			return null;
		}
	}

	private static final Logger logger = LoggerFactory
			.getLogger(ChartFacade.class);

	public static JFreeChart createMultiPie(InetSocketAddress address)
			throws IOException, MemcachedException, InterruptedException,
			TimeoutException {
		Map<InetSocketAddress, Map<String, String>> slabs = Gather.gather(
				Collections.singletonList(address), "slabs");
		Map<String, String> slab = slabs.get(address);
		List<Chunk> read = ChunkFactory.read(slab);
		ChunkStats cs = new ChunkStats(read);
		JFreeChart createMultiPie = cs.createMultiPie("stats", "totalChunks",
				"memRequested", "getHits", "cmdSet", "deleteHits");
		return createMultiPie;
	}

	public static JFreeChart createAge(InetSocketAddress address)
			throws IOException, MemcachedException, InterruptedException,
			TimeoutException {

		Map<InetSocketAddress, Map<String, String>> overviews = Gather
				.overview(Collections.singletonList(address));

		Map<InetSocketAddress, Map<String, String>> items = Gather.gather(
				Collections.singletonList(address), "items");

		Map<InetSocketAddress, Map<String, String>> slabs = Gather.gather(
				Collections.singletonList(address), "slabs");
		long startTime = Long.parseLong(overviews.get(address).get("time"))
				- Long.parseLong(overviews.get(address).get("uptime"));
		logger.info("startTime:{}", new Date(startTime * 1000));
		Map<String, String> slab = slabs.get(address);
		List<Chunk> read = ChunkFactory.read(slab);
		ChunkStats cs = new ChunkStats(read);
		Map<String, String> item = items.get(address);
		List<Item> read2 = ItemFactory.read(item, cs.getIdSizeMappings());
		ItemStats is = new ItemStats(read2, startTime);

		return is.createBarAgeEt("age");
	}

	public static JFreeChart createEvit(InetSocketAddress address)
			throws IOException, MemcachedException, InterruptedException,
			TimeoutException {

		Map<InetSocketAddress, Map<String, String>> overviews = Gather
				.overview(Collections.singletonList(address));

		Map<InetSocketAddress, Map<String, String>> items = Gather.gather(
				Collections.singletonList(address), "items");

		Map<InetSocketAddress, Map<String, String>> slabs = Gather.gather(
				Collections.singletonList(address), "slabs");
		long startTime = Long.parseLong(overviews.get(address).get("time"))
				- Long.parseLong(overviews.get(address).get("uptime"));
		logger.info("startTime:{}", new Date(startTime * 1000));
		Map<String, String> slab = slabs.get(address);
		List<Chunk> read = ChunkFactory.read(slab);
		ChunkStats cs = new ChunkStats(read);
		Map<String, String> item = items.get(address);
		List<Item> read2 = ItemFactory.read(item, cs.getIdSizeMappings());
		ItemStats is = new ItemStats(read2, startTime);

		return is.createBarEvict("evict");
	}
}
