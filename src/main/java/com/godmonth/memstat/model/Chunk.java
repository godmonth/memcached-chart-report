package com.godmonth.memstat.model;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Chunk {
	private long id;
	private long chunksPerPage;
	private long freeChunks;
	private long deleteHits;
	private long casHits;
	private long usedChunks;
	private long casBadval;
	private long totalChunks;
	private long freeChunksEnd;
	private long getHits;
	private long chunkSize;
	private long incrHits;
	private long cmdSet;
	private long totalPages;
	private long memRequested;
	private long decrHits;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getChunksPerPage() {
		return chunksPerPage;
	}

	public void setChunksPerPage(long chunksPerPage) {
		this.chunksPerPage = chunksPerPage;
	}

	public long getFreeChunks() {
		return freeChunks;
	}

	public void setFreeChunks(long freeChunks) {
		this.freeChunks = freeChunks;
	}

	public long getDeleteHits() {
		return deleteHits;
	}

	public void setDeleteHits(long deleteHits) {
		this.deleteHits = deleteHits;
	}

	public long getCasHits() {
		return casHits;
	}

	public void setCasHits(long casHits) {
		this.casHits = casHits;
	}

	public long getUsedChunks() {
		return usedChunks;
	}

	public void setUsedChunks(long usedChunks) {
		this.usedChunks = usedChunks;
	}

	public long getCasBadval() {
		return casBadval;
	}

	public void setCasBadval(long casBadval) {
		this.casBadval = casBadval;
	}

	public long getTotalChunks() {
		return totalChunks;
	}

	public void setTotalChunks(long totalChunks) {
		this.totalChunks = totalChunks;
	}

	public long getFreeChunksEnd() {
		return freeChunksEnd;
	}

	public void setFreeChunksEnd(long freeChunksEnd) {
		this.freeChunksEnd = freeChunksEnd;
	}

	public long getGetHits() {
		return getHits;
	}

	public void setGetHits(long getHits) {
		this.getHits = getHits;
	}

	public long getChunkSize() {
		return chunkSize;
	}

	public void setChunkSize(long chunkSize) {
		this.chunkSize = chunkSize;
	}

	public long getIncrHits() {
		return incrHits;
	}

	public void setIncrHits(long incrHits) {
		this.incrHits = incrHits;
	}

	public long getCmdSet() {
		return cmdSet;
	}

	public void setCmdSet(long cmdSet) {
		this.cmdSet = cmdSet;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getMemRequested() {
		return memRequested;
	}

	public void setMemRequested(long memRequested) {
		this.memRequested = memRequested;
	}

	public long getDecrHits() {
		return decrHits;
	}

	public void setDecrHits(long decrHits) {
		this.decrHits = decrHits;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)

		.append("totalChunks", this.totalChunks)
				.append("usedChunks", this.usedChunks)
				.append("freeChunks", this.freeChunks)
				.append("incrHits", this.incrHits)
				.append("memRequested", this.memRequested)
				.append("getHits", this.getHits)
				.append("casBadval", this.casBadval).append("id", this.id)
				.append("casHits", this.casHits)
				.append("decrHits", this.decrHits)
				.append("freeChunksEnd", this.freeChunksEnd)
				.append("chunkSize", this.chunkSize)
				.append("cmdSet", this.cmdSet)
				.append("chunksPerPage", this.chunksPerPage)
				.append("totalPages", this.totalPages)
				.append("deleteHits", this.deleteHits).toString();
	}

}
