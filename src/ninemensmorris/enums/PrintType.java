/* 
 * Copyright (C) 2021 elton
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ninemensmorris.enums;

/**
 * An Enum for the print type Holds Value or location
 *
 * @author eltojaro
 */
public enum PrintType {
    /** Prints the values of the board w/ the board grid */
    VALUE,
    /** Prints the slot locations of the board w/ the board grid */
    LOC,
    /** Prints the values of the board w/o the board grid */
    RAW_VALUE,
    /** Prints the slot locations of the board w/o the board grid */
    RAW_LOC
}
