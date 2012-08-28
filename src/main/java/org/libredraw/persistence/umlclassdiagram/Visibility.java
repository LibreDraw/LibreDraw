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

package org.libredraw.persistence.umlclassdiagram;

public enum Visibility {
	Public,
	Private,
	Package,
	Protected;
	
	public String toString() {
		if(this.compareTo(Visibility.Public) == 0) {
			return "+";
		}
		if(this.compareTo(Visibility.Private) == 0) {
			return "-";
		}
		if(this.compareTo(Visibility.Package) == 0) {
			return "~";
		}
		if(this.compareTo(Visibility.Protected) == 0) {
			return "#";
		}
		return null;
	}
}
