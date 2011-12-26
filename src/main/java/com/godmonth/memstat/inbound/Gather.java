package com.godmonth.memstat.inbound;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class Gather {
	public static Map<InetSocketAddress, Map<String, String>> overview(
			List<InetSocketAddress> addresses) throws IOException,
			MemcachedException, InterruptedException, TimeoutException {
		MemcachedClient mc = null;
		try {
			mc = new XMemcachedClientBuilder(addresses).build();
			return mc.getStats();
		} finally {
			try {
				mc.shutdown();
			} catch (Exception e) {

			}
		}
	}

	public static Map<InetSocketAddress, Map<String, String>> gather(
			List<InetSocketAddress> addresses, String name) throws IOException,
			MemcachedException, InterruptedException, TimeoutException {
		MemcachedClient mc = null;

		try {
			mc = new XMemcachedClientBuilder(addresses).build();
			return mc.getStatsByItem(name);
		} finally {
			try {
				mc.shutdown();
			} catch (Exception e) {

			}
		}
	}

}
