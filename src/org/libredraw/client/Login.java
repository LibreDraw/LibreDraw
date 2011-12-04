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

package org.libredraw.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	static DialogBox dialog;
	static DialogBox errorDialog;
	
	public void onModuleLoad() {
		RootPanel.get("loginBox").add(new LoginBox());
		RootPanel.get("rainbow").add(new Rainbow());
	}
	
	public static void navigateTo() {
		Window.Location.replace("Login.html");
	}
	
	public static void registerDialog(DialogBox d) {
		dialog = d;
	}
	
	public static void registerErrorDialog(DialogBox d)
	{
		errorDialog = d;
	}
}
