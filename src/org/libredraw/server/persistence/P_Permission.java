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

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class P_Permission
{
	static int READ = 1;
	static int WRITE = 2;
	static int BRANCH = 4;
	static int MERGE = 8;
	static int EXPORT = 16;
	static int OWNER = 32;
	static int ALL = READ + WRITE + BRANCH + MERGE + EXPORT;
	
	int code;
	
	@Id public long id;
	
	public P_Permission () {
		
	}
	
	public P_Permission(int c) {
		id = P_AutoIncrement.getNextId(this.getClass());
		code = c;
	}
	
	public boolean containsPermission(int permission) {
		if((code & permission) == permission)
			return true;
		return false;
	}
}
