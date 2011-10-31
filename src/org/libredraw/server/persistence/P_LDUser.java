/*
	This file is part of LibreDraw.

    LibreDraw is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    LibreDraw is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with LibreDraw.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.libredraw.server.persistence;

import java.security.MessageDigest;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

public class P_LDUser extends P_AutoIncrement
{

	public String m_authToken;
	public P_Key m_accountConnector;
	
	public P_LDUser(P_Key accountConnector) throws Exception {
		super();
		this.m_accountConnector = accountConnector;
	}
		
}
