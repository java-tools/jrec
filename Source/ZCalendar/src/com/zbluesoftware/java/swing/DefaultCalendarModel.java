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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

/**
 * This class is the DefaultCalendarModel for the ZCalendar class.  It implements all the
 * methods required by the CalendarModel interface, as well as implementing the
 * PropertyChangeListener events.
 */
public class DefaultCalendarModel implements CalendarModel {
	// Global variables
	/**
	 * The start date to use
	 */
	protected Date startDate;
	
	/**
	 * The lowest displayable date
	 */
	protected Date lowDisplayDate;
	
	/**
	 * The highest displayable date
	 */
	protected Date highDisplayDate;
	
	/**
	 * The custom text to display, if any
	 */
	protected String customText;
	
	/**
	 * The HashMap of Dates that should display the dayItemImage
	 */
	@SuppressWarnings("unchecked")
	protected HashMap dayItemDates;
	
	/**
	 * The Vector of PropertyChangeListeners
	 */
	@SuppressWarnings("unchecked")
	protected Vector listeners;
	
	
	/**
	 * This constructor creates a new DefaultCalendarModel using the current date, no low
	 * or high date display limit, no custom text and no day item display flags.
	 */
	@SuppressWarnings("unchecked")
	public DefaultCalendarModel() {
		this(new Date(), null, null, "", new HashMap());
	}
	
	/**
	 * This constructor create a new DefaultCalendarModel using the specified start date,
	 * no low or high date display limit, no custom text and no day item display flags.
	 * 
	 * @param startDate		The start date to use
	 */
	@SuppressWarnings("unchecked")
	public DefaultCalendarModel(Date startDate) {
		this(startDate, null, null, "", new HashMap());
	}
	
	/**
	 * This constructor creates a new DefaultCalendarModel using the specified start date,
	 * low display date and high display date.  No custom text or day item display flags
	 * are used.
	 * 
	 * @param startDate			The start date to use
	 * @param lowDisplayDate	The low display date to use
	 * @param highDisplayDate	The high display date to use
	 */
	@SuppressWarnings("unchecked")
	public DefaultCalendarModel(Date startDate, Date lowDisplayDate, Date highDisplayDate) {
		this(startDate, lowDisplayDate, highDisplayDate, "", new HashMap());
	}
	
	/**
	 * This constructor creates a new DefaultCalendarModel using the specified start date,
	 * low display date, high display date and custom text.  No day item display flags are used.
	 * 
	 * @param startDate			The start date to use
	 * @param lowDisplayDate	The low display date to use
	 * @param highDisplayDate	The hight display date to use
	 * @param customText		The custom text to be displayed
	 */
	@SuppressWarnings("unchecked")
	public DefaultCalendarModel(Date startDate, Date lowDisplayDate, Date highDisplayDate, String customText) {
		this(startDate, lowDisplayDate, highDisplayDate, customText, new HashMap());
	}
	
	/**
	 * This constructor creates a new DefaultCalendarModel using the specified start date,
	 * low display date, high display date, custom text and day item HashMap flags.
	 * 
	 * @param startDate			The start date to use
	 * @param lowDisplayDate	The low display date to use
	 * @param highDisplayDate	The hight display date to use
	 * @param customText		The custom text to be displayed
	 * @param dayItemDates		The HashMap of String dates in which the day item image will be displayed
	 */
	@SuppressWarnings("unchecked")
	public DefaultCalendarModel(Date startDate, Date lowDisplayDate, Date highDisplayDate, String customText, HashMap dayItemDates) {
		listeners = new Vector();
		setStartDate(startDate);
		setLowDisplayDate(lowDisplayDate);
		setHighDisplayDate(highDisplayDate);
		setCustomText(customText);
		setDayItemDates(dayItemDates);
	}
	
	/**
	 * This method returns the date that should be initially highlighted in the calendar
	 * 
	 * @return		The date that should be initially highlighted in the calendar.
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/**
	 * This method sets the date that should be initially highlighted in the calendar.
	 * After the calendar is displayed for the first time setting the startDate has no
	 * effect.  If the specified start date is null then the current date will be used
	 * instead.
	 * 
	 * @param startDate		The date to be initially displayed.
	 */
	public void setStartDate(Date startDate) {
		if(startDate != null)
			this.startDate = startDate;
		else
			this.startDate = new Date();
	}
	
	/**
	 * This method returns the date that acts as the lowest date that can be selected
	 * by the user.  The calendar only takes the month and year into account when determining
	 * the lowest date to display.  If the lowDisplayDate is null, then there is no highest
	 * date limit.
	 * 
	 * @return	The low display date for the calendar.
	 */
	public Date getLowDisplayDate() {
		return lowDisplayDate;
	}
	
	/**
	 * This method sets the lowest date that can be selected by the user.  The calendar
	 * only takes the month and year into account when determining the lowest date to display.
	 * If the lowDisplayDate is null, then there is no lower date limit.<br>
	 * <br>
	 * The lowDisplayDate must be lower than both the startDate and the highDisplayDate.<br>
	 * 
	 * @param lowDisplayDate	The lowest date that will be displayed by the calendar.
	 */
	public void setLowDisplayDate(Date lowDisplayDate) {
		// Check for nulls
		if(lowDisplayDate == null) {
			this.lowDisplayDate = null;
			return;
		}
		
		// Check for bounds
		if(highDisplayDate != null && lowDisplayDate.compareTo(highDisplayDate) >= 0)
			return;
		
		if(lowDisplayDate.compareTo(startDate) >= 0)
			return;
		
		// Assign the date
		this.lowDisplayDate = lowDisplayDate;
	}
	
	/**
	 * This method returns the highest date that can be selected by the user.  The calendar
	 * only takes the month and year into account when determining the highest date to display.
	 * If the highDisplayDate is null, then there is no highest date limit.
	 * 
	 * @return	The high display date for the calendar.
	 */
	public Date getHighDisplayDate() {
		return highDisplayDate;
	}
	
	/**
	 * This method sets the highest date that can be selected by the user.  The calendar only
	 * takes the month and year into account when determining the highest date to display.  If
	 * the highDisplayDate is null, then there is no highest date limit.<br>
	 * <br>
	 * The highDisplayDate must be higher than both the startDate and the lowDisplayDate.<br>
	 * 
	 * @param highDisplayDate	The highest date that will be displayed by the calendar.
	 */
	public void setHighDisplayDate(Date highDisplayDate) {
		// Check for nulls
		if(highDisplayDate == null) {
			this.highDisplayDate = null;
			return;
		}
		
		// Check for bounds
		if(lowDisplayDate != null && highDisplayDate.compareTo(lowDisplayDate) <= 0)
			return;
		
		if(highDisplayDate.compareTo(startDate) <= 0)
			return;
		
		// Assign the date
		this.highDisplayDate = highDisplayDate;
	}
	
	/**
	 * Returns any custom text that will be displayed along with the month and year at
	 * the top of the calendar.
	 * 
	 * @return	The custom text to be displayed next to the month and year.
	 */
	public String getCustomText() {
		return customText;
	}
	
	/**
	 * Sets any custom text that will be displayed along with the month and year at the
	 * top of the calendar.
	 * 
	 * @param customText	The custom text to display.
	 */
	public void setCustomText(String customText) {
		String oldText = this.customText;
		if(customText != null)
			this.customText = customText;
		else
			this.customText = "";
		
		firePropertyChange("customText", oldText, this.customText);
	}
	
	/**
	 * Returns the HashMap of Strings that represent dates in which the day item image
	 * should be displayed.  The Strings are in the form of year-month-day.  For example,
	 * February 3rd, 2004 would be displayed as "2004-2-3".
	 * 
	 * @return	The HashMap containing the String dates that should display day item images
	 */
	@SuppressWarnings("unchecked")
	public HashMap getDayItemDates() {
		return dayItemDates;
	}
	
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
	public void setDayItemDates(HashMap dayItemDates) {
		HashMap oldHM = this.dayItemDates;
		
		if(dayItemDates != null)
			this.dayItemDates = dayItemDates;
		else
			this.dayItemDates = new HashMap();
		
		firePropertyChange("dayItemDates", oldHM, this.dayItemDates);
	}
	
	/**
	 * This method adds the specified date to the HashMap of dates that should have the
	 * day item image displayed within them.  The date should be in the format of
	 * year-month-day.  For example, February 3rd, 2004 would be "2004-2-3".
	 * 
	 * @param date	The String date that should be added
	 */
	@SuppressWarnings("unchecked")
	public void addDayItemDate(String date) {
		dayItemDates.put(date, date);
		firePropertyChange("dayItemDates", dayItemDates, dayItemDates);
	}
	
	// Removes the specified date from the dayItemDates
	/**
	 * This method removes the specified date from the HashMap of dates that should have the
	 * day item image displayed within them.  The date should be in the format of year-month-day.
	 * For example, February 3rd, 2004 would be "2004-2-3".
	 * 
	 * @param date	The String date to be removed
	 */
	public void removeDayItemDate(String date) {
		dayItemDates.remove(date);
		firePropertyChange("dayItemDates", dayItemDates, dayItemDates);
	}
	
	/**
	 * This method adds the specified PropertyChangeListener to the list of PropertyChangeListeners.
	 * 
	 * @param listener	The PropertyChangeListener to add
	 */
	@SuppressWarnings("unchecked")
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if(!(listeners.contains(listener)))
			listeners.add(listener);
	}
	
	/**
	 * This method removes the specified PropertyChangeListener from the list of
	 * PropertyChangeListeners.
	 * 
	 * @param listener	The PropertyChangeListener to remove
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * This method calls the propertyChange method of each registeterd PropertyChangeListener.
	 * 
	 * @param propertyName		The name of the property that changed
	 * @param oldValue			The old value of the changed property
	 * @param newValue			The new value of the changed property
	 */
	@SuppressWarnings("unchecked")
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		Vector list;
		
		synchronized(this) {
			list = (Vector)listeners.clone();
		}
		
		if(list.size() == 0)
			return;
		
		PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
		
		for(int i = list.size()-1; i >= 0; i--)
			((PropertyChangeListener)list.elementAt(i)).propertyChange(event);
	}
}