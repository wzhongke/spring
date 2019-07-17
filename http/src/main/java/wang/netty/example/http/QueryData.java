package wang.netty.example.http;

import java.io.Serializable;

public class QueryData implements Serializable {

	private String query;

	private String regQuery;

	private String from;

	private String ip;

	private String wuid;

	private String ipLoc;

	private long flag;

	private String requestId;

	private Integer warnLevel;

	private Integer superWarnLevel;

	public QueryData () {}

	public QueryData( QueryData queryData) {
		this.query = queryData.query;
		this.flag = queryData.flag;
		this.from = queryData.from;
		this.ip = queryData.ip;
		this.warnLevel = queryData.warnLevel;
		this.superWarnLevel = queryData.superWarnLevel;
		this.ipLoc = queryData.ipLoc;
		this.requestId = queryData.requestId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getWuid() {
		return wuid;
	}

	public void setWuid(String wuid) {
		this.wuid = wuid;
	}

	public String getIpLoc() {
		return ipLoc;
	}

	public void setIpLoc(String ipLoc) {
		this.ipLoc = ipLoc;
	}

	public long getFlag() {
		return flag;
	}

	public void setFlag(long flag) {
		this.flag = flag;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getWarnLevel() {
		return warnLevel;
	}

	public void setWarnLevel(Integer warnLevel) {
		this.warnLevel = warnLevel;
	}

	public Integer getSuperWarnLevel() {
		return superWarnLevel;
	}

	public void setSuperWarnLevel(Integer superWarnLevel) {
		this.superWarnLevel = superWarnLevel;
	}

	public String getRegQuery() {
		return regQuery;
	}

	public void setRegQuery(String regQuery) {
		this.regQuery = regQuery;
	}

	@Override
	public String toString() {
		return "QueryData{" +
			"query='" + query + '\'' +
			",from='" + from + '\'' +
			",ip='" + ip + '\'' +
			",wuid='" + wuid + '\'' +
			",ipLoc='" + ipLoc + '\'' +
			",flag=" + flag +
			",requestId='" + requestId + '\'' +
			",warnLevel=" + warnLevel +
			",superWarnLevel=" + superWarnLevel +
			'}';
	}

}

