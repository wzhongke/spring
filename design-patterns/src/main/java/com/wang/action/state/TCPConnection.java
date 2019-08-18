package com.wang.action.state;

/**
 * @author wangzhongke
 */
public class TCPConnection {
	private TCPState state;

	public TCPConnection() {
	}

	public void passiveOpen(TCPConnection tcpConnection) {
		state.passiveOpen(tcpConnection);
	}

	public void close(TCPConnection tcpConnection) {
		state.close(tcpConnection);
	}

	public void send(TCPConnection tcpConnection) {
		state.send(tcpConnection);
	}

	protected void changeState(TCPState state) {
		this.state = state;
	}
}
