package com.godmonth.memstat.model;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Item {
	private long id;
	private long chunkSize;
	private long age;
	private long evicted;
	private long evictedNonzero;
	private long evictedTime;
	private long outofmemory;
	private long tailrepairs;
	private long reclaimed;

	public long getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(long chunkSize) {
		this.chunkSize = chunkSize;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public long getEvicted() {
		return evicted;
	}

	public void setEvicted(long evicted) {
		this.evicted = evicted;
	}

	public long getEvictedNonzero() {
		return evictedNonzero;
	}

	public void setEvictedNonzero(long evictedNonzero) {
		this.evictedNonzero = evictedNonzero;
	}

	public long getEvictedTime() {
		return evictedTime;
	}

	public void setEvictedTime(long evictedTime) {
		this.evictedTime = evictedTime;
	}

	public long getOutofmemory() {
		return outofmemory;
	}

	public void setOutofmemory(long outofmemory) {
		this.outofmemory = outofmemory;
	}

	public long getTailrepairs() {
		return tailrepairs;
	}

	public void setTailrepairs(long tailrepairs) {
		this.tailrepairs = tailrepairs;
	}

	public long getReclaimed() {
		return reclaimed;
	}

	public void setReclaimed(long reclaimed) {
		this.reclaimed = reclaimed;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", this.id).append("outofmemory", this.outofmemory)
				.append("evictedTime", this.evictedTime)
				.append("age", this.age).append("chunkSize", this.chunkSize)
				.append("tailrepairs", this.tailrepairs)
				.append("evictedNonzero", this.evictedNonzero)
				.append("evicted", this.evicted)
				.append("reclaimed", this.reclaimed).toString();
	}

}
