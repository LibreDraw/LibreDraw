package org.libredraw.server;

import org.libredraw.client.LibreRPC;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EngineRPC extends RemoteServiceServlet implements LibreRPC {

	@Override
	public String login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register(String email, String password, String displayName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

}
