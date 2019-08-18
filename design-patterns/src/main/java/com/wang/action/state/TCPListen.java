package com.wang.action.state;

public class TCPListen extends TCPState {

	private static TCPState listen = new TCPEstablished();

	public static TCPState getInstance() {
		return listen;
	}

	@Override
	public void transmit(TCPConnection tcpConnection) {
	}


	@Override
	public void passiveOpen(TCPConnection tcpConnection) {
	}

	@Override
	public void close(TCPConnection tcpConnection) {
	}

	@Override
	public void send(TCPConnection tcpConnection) {
		changeState(tcpConnection, TCPEstablished.getInstance());
	}
}