package com.wang.action.state;

public class TCPEstablished extends TCPState {

	private static TCPState established = new TCPEstablished();

	public static TCPState getInstance() {
		return established;
	}

	@Override
	public void transmit(TCPConnection tcpConnection) {
	}

	@Override
	public void passiveOpen(TCPConnection tcpConnection) {
	}

	@Override
	public void close(TCPConnection tcpConnection) {
		changeState(tcpConnection, TCPClosed.getInstance());
	}

	@Override
	public void send(TCPConnection tcpConnection) {
	}
}
