package com.wang.action.state;

public class TCPClosed extends TCPState {
	private static TCPState closed = new TCPClosed();

	public static TCPState getInstance() {
		return closed;
	}

	@Override
	public void transmit(TCPConnection tcpConnection) {

	}

	@Override
	public void passiveOpen(TCPConnection tcpConnection) {
		changeState(tcpConnection, TCPListen.getInstance());
	}

	@Override
	public void close(TCPConnection tcpConnection) {

	}

	@Override
	public void send(TCPConnection tcpConnection) {

	}
}
