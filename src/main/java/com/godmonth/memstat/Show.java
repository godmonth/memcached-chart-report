package com.godmonth.memstat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godmonth.memstat.inbound.ChunkFactory;
import com.godmonth.memstat.inbound.Gather;
import com.godmonth.memstat.inbound.ItemFactory;
import com.godmonth.memstat.model.Chunk;
import com.godmonth.memstat.model.Item;
import com.godmonth.memstat.outbound.Output;
import com.godmonth.memstat.stat.ChunkStats;
import com.godmonth.memstat.stat.ItemStats;

public class Show {

	private static final Logger logger = LoggerFactory.getLogger(Show.class);

	public static void main(String[] args) throws IOException,
			MemcachedException, InterruptedException, TimeoutException {
		logger.info(ArrayUtils.toString(args));
		Show.look(StringUtils.replace(args[0], ",", " "), args[1]);
	}

	public static void look(String addressesStr, String dir)
			throws IOException, MemcachedException, InterruptedException,
			TimeoutException {
		logger.info(addressesStr);
		List<InetSocketAddress> addresses = AddrUtil.getAddresses(addressesStr);

		Map<InetSocketAddress, Map<String, String>> overviews = Gather
				.overview(addresses);

		Map<InetSocketAddress, Map<String, String>> items = Gather.gather(
				addresses, "items");

		Map<InetSocketAddress, Map<String, String>> slabs = Gather.gather(
				addresses, "slabs");
		for (InetSocketAddress inetSocketAddress : addresses) {
			long startTime = Long.parseLong(overviews.get(inetSocketAddress)
					.get("time"))
					- Long.parseLong(overviews.get(inetSocketAddress).get(
							"uptime"));
			logger.info("startTime:{}", new Date(startTime * 1000));
			Map<String, String> slab = slabs.get(inetSocketAddress);
			List<Chunk> read = ChunkFactory.read(slab);
			ChunkStats cs = new ChunkStats(read);
			Map<String, String> item = items.get(inetSocketAddress);
			List<Item> read2 = ItemFactory.read(item, cs.getIdSizeMappings());
			ItemStats is = new ItemStats(read2, startTime);

			String dd = dir + "/"
					+ inetSocketAddress.getAddress().getHostAddress() + "_"
					+ inetSocketAddress.getPort() + "/";
			FileUtils.forceMkdir(new File(dd));
			Show.show(cs, is, dd);
		}

	}

	public static void show(ChunkStats stat, ItemStats is, String dir)
			throws FileNotFoundException, IOException {
		Output.writePngMultiPie(stat.createMultiPie("stats", "totalChunks",
				"memRequested", "getHits", "cmdSet", "deleteHits"), dir
				+ "/stats.png", 2100, 1000);
		Output.write2File(is.createBarAgeEt("age"), dir + "/age.png", 800, 600);
		Output.write2File(is.createBarEvict("evict"), dir + "/evict.png", 800,
				600);

	}
}
