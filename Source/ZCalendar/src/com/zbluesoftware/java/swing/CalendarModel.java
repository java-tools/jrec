/*
Copyright 2006 zBlueSoftware, LLC

This file is part of ZCalendar.

The ZCalendar is free software; you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation; either version 2.1 of the License, or
(at your option) any later version.

ZCalendar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Lesser eneral Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with ZCalendar; if not, write to the Free Software Foundation, Inc.,
51 Franklin St, Fifth Floor, Boston, MA  02110-1301 USA

*/

package com.zbluesoftware.java.swing;

import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.HashMap;

/**
 * This interface defines all methods that must be implemented by any calendar models
 * that will be used in the ZCalendar class.
 */
public interface CalendarModel {
	
	/**
	 * This method returns the date that should be initially highlighted in the calendar
	 * 
	 * @return		The date that should be initially highlighted in the calendar.
	 */
	public Date getStartDate();
	
	/**
	 * This method sets the date that should be initially highlighted in the calendar.
	 * After the calendar is displayed for the first time setting the startDate has no
	 * effect.
	 * 
	 * @param startDate		The date to be initially displayed.
	 */
	public void setStartDate(Date startDate);
	
	/**
	 * This method returns the date that acts as the lowest date that can be selected
	 * by the user.  The calendar only takes the month and year into account when determining
	 * the lowest date to display.  If the lowDisplayDate is null, then there is no highest
	 * date limit.
	 * 
	 * @return	The low display date for the calendar.
	 */
	public Date getLowDisplayDate();
	
	/**
	 * This method sets the lowest date that can be selected by the user.  The calendar
	 * only takes the month and year into account when determining the lowest date to display.
	 * If the lowDisplayDate is null, then there is no lower date limit.<br>
	 * <br>
	 * The lowDisplayDate must be lower than both the startDate and the highDisplayDate.<br>
	 * 
	 * @param lowDisplayDate	The lowest date that will be displayed by the calendar.
	 */
	public void setLowDisplayDate(Date lowDisplayDate);
	
	/**
	 * This method returns the highest date that can be selected by the user.  The calendar
	 * only takes the month and year into account when determining the highest date to display.
	 * If the highDisplayDate is null, then there is no highest date limit.
	 * 
	 * @return	The high display date for the calendar.
	 */
	public Date getHighDisplayDate();
	
	/**
	 * This method sets the highest date that can be selected by the user.  The calendar only
	 * takes the month and year into account when determining the highest date to display.  If
	 * the highDisplayDate is null, then there is no highest date limit.<br>
	 * <br>
	 * The highDisplayDate must be higher than both the startDate and the lowDisplayDate.<br>
	 * 
	 * @param highDisplayDate	The highest date that will be displayed by the calendar.
	 */
	public void setHighDisplayDate(Date highDisplayDate);
	
	/**
	 * Returns any custom text that will be displayed along with the month and year at
	 * the top of the calendar.
	 * 
	 * @return	The custom text to be displayed next to the month and year.
	 */
	public String getCustomText();
	
	/**
	 * Sets any custom text that will be displayed along with the month and year at the
	 * top of the calendar.
	 * 
	 * @param customText	The custom text to display.
	 */
	public void setCustomText(String customText);
	
	/**
	 * Returns the HashMap of Strings that represent dates in which the day item image
	 * should be displayed.  The Strings are in the form of year-month-day.  For example,
	 * February 3rd, 2004 would be displayed as "2004-2-3".
	 * 
	 * @return	The HashMap containing the String dates that should display day item images
	 */
	@SuppressWarnings("unchecked")
	public HashMap getDayItemDates();
	
	/**
	 * This method sets the HashMap of Strings representing dates in which the day item image
	 * should be displayed.  The Strings are in the form of year-month-day.  For example,
	 * February 3rd, 2004 would be displayed as "2004-2-3".  The key value of the HashMap is
	 * the important part of the HashMap.  This is where the String representation should be
	 * stored.  The value corrisponding to the key can have any value.
	 * 
	 * @param dayItemDates		The HashMap of Strings
	 */
	@SuppressWarnings("unchecked")
	public void setDayItemDates(HashMap dayItemDates);
	
	/**
	 * This method adds the specified PropertyChangeListener to the list of PropertyChangeListeners.
	 * 
	 * @param listener	The PropertyChangeListener to add
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener);
	
	/**
	 * This method removes the specified PropertyChangeListener from the list of
	 * PropertyChangeListeners.
	 * 
	 * @param listener	The PropertyChangeListener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);
}