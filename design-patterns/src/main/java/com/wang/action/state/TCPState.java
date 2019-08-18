package com.wang.action.state;

/**
 * @author wangzhongke
 */
public abstract class TCPState {

	public abstract void transmit(TCPConnection tcpConnection);
	public abstract void passiveOpen(TCPConnection tcpConnection);
	public abstract void close(TCPConnection tcpConnection);
	public abstract void send(TCPConnection tcpConnection);

	protected void changeState(TCPConnection tcpConnection, TCPState state) {
		tcpConnection.changeState(state);
	}

}
