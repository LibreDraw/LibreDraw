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

import org.libredraw.shared.LDUser;
import org.libredraw.shared.PermissionRecord;


@Entity
public class Permission
{
	static public int READ = 1;
	static public int WRITE = 2;
	static public int BRANCH = 4;
	static public int MERGE = 8;
	static public int EXPORT = 16;
	static public int OWNER = 32;
	static public int ALL = READ + WRITE + BRANCH + MERGE + EXPORT;
	
	public int code;
	
	@Id public long id;
	
	public Permission () {
		
	}
	
	public Permission(int c) {
		id = AutoIncrement.getNextId(this.getClass());
		code = c;
	}
	
	public boolean containsPermission(int permission) {
		if((code & permission) == permission)
			return true;
		return false;
	}
	
	public PermissionRecord getRecord(LDUser u) {
		PermissionRecord result = new PermissionRecord(u);
		if(containsPermission(READ))
			result.READ = true;
		if(containsPermission(WRITE))
			result.WRITE = true;
		if(containsPermission(BRANCH))
			result.BRANCH = true;
		if(containsPermission(MERGE))
			result.MERGE = true;
		if(containsPermission(EXPORT))
			result.EXPORT = true;
		if(containsPermission(OWNER))
			result.OWNER = true;
		return result;
	}
}
