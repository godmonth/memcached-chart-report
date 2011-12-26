package com.godmonth.memstat;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

public class ShowTest {
	public static void main(String[] args) throws IOException,
			MemcachedException, InterruptedException, TimeoutException {
		Show.main(new String[] { "10.241.14.36:11211", "target" });
	}

}
