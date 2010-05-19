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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;

/**
 * This class displays a calendar for the month in the specified date.  Most aspects of the calendar
 * customizable, including the starting date, and boundary dates, icons used, fonts and colors.
 * The CalendarModel used will define all the data parameters, and the look and feel parameters
 * are controlled through this class.<br>
 * <br>
 * The showDialog() methods allow you to display a JDialog containing the calendar.  When closed
 * the dialog will return the date the user clicked on, or null if no date was clicked.
 */
public class ZCalendar extends JComponent implements MouseListener, MouseMotionListener, PropertyChangeListener {
	// Global variables
	/**
	 * The CalendarModel to use
	 */
	protected CalendarModel calendarModel;
	
	/**
	 * The currently displayed date
	 */
	protected Date currentDate;
	
	/**
	 * The locale to use for displaying names
	 */
	protected Locale locale;
	
	/**
	 * The formatter used to display the name of the month
	 */
	protected SimpleDateFormat monthFormat;
	
	/**
	 * The array of strings of the days of the week
	 */
	protected String[] dayOfWeek;
	
	/**
	 * The name of the month
	 */
	protected String monthName;
	
	/**
	 * The left arrow polygon
	 */
	protected Polygon leftArrow;
	
	/**
	 * The right arrow polygon
	 */
	protected Polygon rightArrow;
	
	/**
	 * The background color of the week name row
	 */
	protected Color weekNameBGColor;
	
	/**
	 * The background color of the weekend columns
	 */
	protected Color weekendBGColor;
	
	/**
	 * The highlight color for the current day
	 */
	protected Color currentDayHighlightColor;
	
	/**
	 * The highlight color for the current mouse over day
	 */
	protected Color highlightColor;
	
	/**
	 * The color of the month text
	 */
	protected Color monthFGColor;
	
	/**
	 * The color of the week name text
	 */
	protected Color weekNameFGColor;
	
	/**
	 * The color of the day text
	 */
	protected Color dayFGColor;
	
	/**
	 * The font used to draw the month text
	 */
	protected Font monthFont;
	
	/**
	 * The font used to draw the week text
	 */
	protected Font weekFont;
	
	/**
	 * The font used to draw the day text
	 */
	protected Font dayFont;
	
	/**
	 * The Image used mark days that have items associated with them
	 */
	protected Image dayItemImage;
	
	/**
	 * The height of a day box
	 */
	protected float dayHeight;
	
	/**
	 * The width of a day box
	 */
	protected float dayWidth;
	
	/**
	 * The day of the week for the first day of the month
	 */
	protected int firstDay;
	
	/**
	 * The number of days in the month
	 */
	protected int maxDays;
	
	/**
	 * The number of weeks in the month
	 */
	protected int weeksInMonth;
	
	/**
	 * The day of the month for the date
	 */
	protected int currentDay;
	
	/**
	 * The current x position for the mouse over square
	 */
	protected int currentXMouse;
	
	/**
	 * The current y position for the mouse over square
	 */
	protected int currentYMouse;
	
	/**
	 * The flag for which arrow is highlighted, if any
	 */
	protected int arrowHighlight;
	
	/**
	 * The alignment of the month name
	 */
	protected int monthAlignment;
	
	/**
	 * The alignment of the day text
	 */
	protected int dayAlignment;
	
	/**
	 * The alignment of the day item image
	 */
	protected int dayItemAlignment;
	
	/**
	 * The width of the day item image
	 */
	protected int dayItemWidth;
	
	/**
	 * The height of the day item image
	 */
	protected int dayItemHeight;
	
	
	// Constants
	private static int HEADER_HEIGHT = 20;		// The height of the calendar month header
	private static int DAY_HEIGHT = 15;			// The height of a day box
	private static int DAY_WIDTH = 25;			// The width of a day box
	
	private static final int NO_ARROW = 0;				// Neither arrow is highlighted
	private static final int LEFT_ARROW = 1;			// The left arrow is highlighted
	private static final int RIGHT_ARROW = 2;			// The right arrow is highlight
	
	/**
	 * Top left alignment for an item
	 */
	public static final int ALIGN_TOP_LEFT = 0;
	
	/**
	 * Top center alignment for an item
	 */
	public static final int ALIGN_TOP_CENTER = 1;
	
	/**
	 * Top right alignment for an item
	 */
	public static final int ALIGN_TOP_RIGHT = 2;
	
	/**
	 * Middle left alignment for an item
	 */
	public static final int ALIGN_MIDDLE_LEFT = 3;
	
	/**
	 * Middle center alignment for an item
	 */
	public static final int ALIGN_MIDDLE_CENTER = 4;
	
	/**
	 * Middle right alignment for an item
	 */
	public static final int ALIGN_MIDDLE_RIGHT = 5;
	
	/**
	 * Bottom left alignment for an item
	 */
	public static final int ALIGN_BOTTOM_LEFT = 6;
	
	/**
	 * Bottom middle alignment for an item
	 */
	public static final int ALIGN_BOTTOM_CENTER = 7;
	
	/**
	 * Bottom right alignment for an item
	 */
	public static final int ALIGN_BOTTOM_RIGHT = 8;
	
	
	/**
	 * This constructor creates a ZCalendar using the DefaultCalendarModel and the default Locale.
	 */
	public ZCalendar() {
		this(new DefaultCalendarModel(), Locale.getDefault());
	}
	
	/**
	 * This constructor create a ZCalendar using the DefaultCalendarModel and the specified Locale.
	 * 
	 * @param locale	The Locale to use
	 */
	public ZCalendar(final Locale locale) {
		this(new DefaultCalendarModel(), locale);
	}
	
	/**
	 * This constructor creates a ZCalendar using the specified CalendarModel and the default Locale.
	 * 
	 * @param calendarModel		The CalendarModel to use
	 */
	public ZCalendar(final CalendarModel calendarModel) {
		this(calendarModel, Locale.getDefault());
	}
	
	/**
	 * This constructor creates a ZCalendar using the specified CalendarModel and Locale.
	 * 
	 * @param calendarModel		The CalendarModel to use
	 * @param locale			The Locale to use
	 */
	public ZCalendar(final CalendarModel calendarModel, final Locale locale) {
		// Set defaults
		dayOfWeek = new String[] {"S", "M", "T", "W", "T", "F", "S"};
		weeksInMonth = 4;
		monthName = "";
		currentXMouse = currentYMouse = -1;
		dayHeight = DAY_HEIGHT;
		dayWidth = DAY_WIDTH;
		monthFormat = new SimpleDateFormat("MMMM yyyy", locale);
		
		// Set alignments
		monthAlignment = ALIGN_MIDDLE_CENTER;
		dayAlignment = ALIGN_BOTTOM_RIGHT;
		dayItemAlignment = ALIGN_MIDDLE_CENTER;
		
		// Set the fonts
		monthFont = new Font("Arial", Font.BOLD, 12);
		weekFont = new Font("Arial", Font.PLAIN, 10);
		dayFont = new Font("Arial", Font.PLAIN, 10);
		
		// Set colors
		monthFGColor = Color.black;
		weekNameFGColor = Color.black;
		dayFGColor = Color.black;
		
		// Set images
		setDayItemImage((new ImageIcon(ZCalendar.class.getResource("icons/dayItem.gif"))).getImage());
		
		// Set model and locale
		this.locale = locale;
		setModel(calendarModel);
		
		// Set background
		setBackground(Color.white);
		
		// Set colors
		weekNameBGColor = new Color(255, 255, 204);
		weekendBGColor = new Color(220, 220, 220);
		currentDayHighlightColor = Color.red;
		highlightColor = Color.green;
		
		// Add the MouseListeners
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/**
	 * This method displays the default ZCalender in a JDialog, with the specified Dialog
	 * as the parent.
	 * 
	 * @param dialog	The parent Dialog.
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Dialog dialog) {
		return displayDialog(dialog, "Calendar", new DefaultCalendarModel(), Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified Locale in a JDialog.  The specified
	 * Dialog is used as the parent.
	 * 
	 * @param dialog	The parent Dialog
	 * @param locale	The Locale to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Dialog dialog, final Locale locale) {
		return displayDialog(dialog, "Calendar", new DefaultCalendarModel(), Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified CalendarModel in a JDialog.  The
	 * specified Dialog is used as the parent.
	 * 
	 * @param dialog			The parent Dialog
	 * @param calendarModel		The CalendarModel to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Dialog dialog, final CalendarModel calendarModel) {
		return displayDialog(dialog, "Calendar", calendarModel, Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified calendarModel and locale in a JDialog.
	 * The title of the dialog is specified by the String title.  The specified Dialog is used as
	 * the parent.
	 * 
	 * @param dialog			The parent Dialog
	 * @param title				The title for the dialog
	 * @param calendarModel		The CalendarModel to use
	 * @param locale			The Locale to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Dialog dialog, final String title, final CalendarModel calendarModel, final Locale locale) {
		return displayDialog(dialog, title, calendarModel, locale);
	}
	
	/**
	 * This method displays the default ZCalender in a JDialog, with the specified Frame
	 * as the parent.
	 * 
	 * @param frame		The parent Frame.
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Frame frame) {
		return displayDialog(frame, "Calendar", new DefaultCalendarModel(), Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified Locale in a JDialog.  The specified
	 * Frame is used as the parent.
	 * 
	 * @param frame		The parent Frame
	 * @param locale	The Locale to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Frame frame, final Locale locale) {
		return displayDialog(frame, "Calendar", new DefaultCalendarModel(), Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified CalendarModel in a JDialog.  The
	 * specified Frame is used as the parent.
	 * 
	 * @param frame				The parent Frame
	 * @param calendarModel		The CalendarModel to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Frame frame, final CalendarModel calendarModel) {
		return displayDialog(frame, "Calendar", calendarModel, Locale.getDefault());
	}
	
	/**
	 * This method displays a ZCalendar with the specified calendarModel and locale in a JDialog.
	 * The title of the dialog is specified by the String title.  The specified Frame is used as
	 * the parent.
	 * 
	 * @param frame				The parent Frame
	 * @param title				The title for the dialog
	 * @param calendarModel		The CalendarModel to use
	 * @param locale			The Locale to use
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	public static Date showDialog(final Frame frame, final String title, final CalendarModel calendarModel, final Locale locale) {
		return displayDialog(frame, title, calendarModel, locale);
	}
	
	/**
	 * This method displays the ZCalendar in a JDialog.  This method returns the selected Date
	 * if the user clicks on a date, or null if the user closes the JDialog without clicking on
	 * a date.
	 * 
	 * @param parent			The parent component for the JDialog
	 * @param title				The title to use for the JDialog
	 * @param calendarModel		The CalendarModel to use in the ZCalendar
	 * @param locale			The Locale to use in the ZCalender
	 * 
	 * @return	The date the user clicked on, or null if the JDialog is closed without a
	 * 			date being clicked on.
	 */
	protected static Date displayDialog(final Component parent, String title, final CalendarModel calendarModel, final Locale locale) {
		// Check for title
		if(title == null)
			title = "Calendar";
		
		// Create the dialog
		final JDialog dialog;
		if(parent instanceof Frame)
			dialog = new JDialog((Frame)parent, title, true);
		else
			dialog = new JDialog((Dialog)parent, title, true);
		dialog.getContentPane().setLayout(new BorderLayout());
		
		// Create the ZCalendar
		final ZCalendar calendar = new ZCalendar(calendarModel, locale);
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent event) {
				if(event.getPropertyName().equals("SelectedDay")) {
					dialog.setVisible(false);
					dialog.setTitle("selected");
					calendar.setCurrentDate((Date)event.getNewValue());
				}
			}
		});
		
		// Add the calendar to the dialog
		dialog.getContentPane().add(calendar);
		dialog.pack();
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
		
		// Return the selected date
		if(dialog.getTitle().equals("selected"))
			return calendar.getCurrentDate();
		else
			return null;
	}
	
	/**
	 * Sets the CalendarModel to be used by this ZCalendar.
	 * 
	 * @param calendarModel		The new CalendarModel to use
	 */
	public void setModel(final CalendarModel calendarModel) {
		// Remove any old listeners
		if(this.calendarModel != null)
			this.calendarModel.removePropertyChangeListener(this);
		
		// Assign new model
		this.calendarModel = calendarModel;
		setCurrentDate(calendarModel.getStartDate());
		this.calendarModel.addPropertyChangeListener(this);
	}
	
	/**
	 * This methods returns the CalendarModel currently being used by the ZCanlendar.
	 * 
	 * @return	The current CalendarModel
	 */
	public CalendarModel getModel() {
		return calendarModel;
	}
	
	/**
	 * This method sets the current date for the ZCalendar.  This is different from the start date.
	 * The start date remains constant, but the current date changes as the user moves or clicks the
	 * mouse, or changes to a new month.
	 * 
	 * @param currentDate	The current date.
	 */
	public void setCurrentDate(final Date currentDate) {
		this.currentDate = currentDate;
		updateDisplayInfo();
	}
	
	/**
	 * This method returns the current date.  This is the date that will be set when the user
	 * clicks on a date.  Therefore this method should be used to retrieve the chosen date.
	 * 
	 * @return	The current (chosen) date.
	 */
	public Date getCurrentDate() {
		return currentDate;
	}
	
	/**
	 * This method sets the Locale that the ZCalendar will use when determining the text to
	 * display the name of the month and weekdays.
	 * 
	 * @param locale	The Locale the ZCalendar will use.
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
		updateDisplayInfo();
	}
	
	/**
	 * This method returns the current Locale being used by the ZCalendar to display month
	 * and day text.
	 * 
	 * @return	The current Locale used by the ZCalendar
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * This method sets the alignment of the month text.  By default it is ALIGN_MIDDLE_CENTER.
	 * Only the values of ALIGN_MIDDLE_LEFT, ALIGN_MIDDLE_CENTER and ALIGN_MIDDLE_RIGHT are
	 * acceptable.  All other values will be ignored.
	 * 
	 * @param monthAlignment	The new monthAlignment value
	 */
	public void setMonthAlignment(final int monthAlignment) {
		if(monthAlignment == ALIGN_MIDDLE_LEFT || monthAlignment == ALIGN_MIDDLE_CENTER || monthAlignment == ALIGN_MIDDLE_RIGHT)
			this.monthAlignment = monthAlignment;
	}
	
	/**
	 * This method returns the current monthAlignment value
	 * 
	 * @return	The monthAlignment value
	 */
	public int getMonthAlignment() {
		return monthAlignment;
	}
	
	/**
	 * This method sets the dayAlignment value.  This determines where the number of the day will be
	 * drawn within the day's cell.  The value must be one of the ALIGN constants or it will be ignored.
	 * ALIGN_BOTTOM_RIGHT is the default.
	 * 
	 * @param dayAlignment		The new dayAlignment value
	 */
	public void setDayAlignment(final int dayAlignment) {
		if(dayAlignment == ALIGN_TOP_LEFT || dayAlignment == ALIGN_TOP_CENTER || dayAlignment == ALIGN_TOP_RIGHT ||
			dayAlignment == ALIGN_MIDDLE_LEFT || dayAlignment == ALIGN_MIDDLE_CENTER || dayAlignment == ALIGN_MIDDLE_RIGHT ||
			dayAlignment == ALIGN_BOTTOM_LEFT || dayAlignment == ALIGN_BOTTOM_CENTER || dayAlignment == ALIGN_BOTTOM_RIGHT) {
			
			this.dayAlignment = dayAlignment;
		}
	}
	
	/**
	 * This method returns the current dayAlignment value.
	 * 
	 * @return	The current dayAlignment value
	 */
	public int getDayAlignment() {
		return dayAlignment;
	}
	
	/**
	 * This method sets the value of the dayItemAlignment.  This determines the alignment of the
	 * day item image within the day cell.  This value must be one of the ALIGN constants or it
	 * will be ignored.  By default ALIGN_MIDDLE_CENTER is used.
	 * 
	 * @param dayItemAlignment		The new value for dayItemAlignment
	 */
	public void setDayItemAlignment(final int dayItemAlignment) {
		if(dayItemAlignment == ALIGN_TOP_LEFT || dayItemAlignment == ALIGN_TOP_CENTER || dayItemAlignment == ALIGN_TOP_RIGHT ||
			dayItemAlignment == ALIGN_MIDDLE_LEFT || dayItemAlignment == ALIGN_MIDDLE_CENTER || dayItemAlignment == ALIGN_MIDDLE_RIGHT ||
			dayItemAlignment == ALIGN_BOTTOM_LEFT || dayItemAlignment == ALIGN_BOTTOM_CENTER || dayItemAlignment == ALIGN_BOTTOM_RIGHT) {
			
			this.dayItemAlignment = dayItemAlignment;
		}
	}
	
	/**
	 * This method returns the current value for dayItemAlignment.
	 * 
	 * @return	The current dayItemAlignment value
	 */
	public int getDayItemAlignment() {
		return dayItemAlignment;
	}
	
	/**
	 * This method sets the color used to display the the background of the week name cells.
	 * 
	 * @param weekNameBGColor		The new weekNameBGColor
	 */
	public void setWeekNameBGColor(final Color weekNameBGColor) {
		this.weekNameBGColor = weekNameBGColor;
	}
	
	/**
	 * This method returns the color used to display the background of the week name cells.
	 * 
	 * @return	The current weekNameBGColor
	 */
	public Color getWeekNameBGColor() {
		return weekNameBGColor;
	}
	
	/**
	 * This method sets the color used to display the background of the weekend cells.
	 * 
	 * @param weekendBGColor	The new weekendBGColor
	 */
	public void setWeekendBGColor(final Color weekendBGColor) {
		this.weekendBGColor = weekendBGColor;
	}
	
	/**
	 * This method returns the color used to display the background of the weekend cells.
	 * 
	 * @return	The current weekendBGColor
	 */
	public Color getWeekendBGColor() {
		return weekendBGColor;
	}
	
	/**
	 * This method sets the color used to highlight the currently selected date.
	 * 
	 * @param currentDayHighlightColor		The new currentDayHighlightColor
	 */
	public void setCurrentDayHighlightColor(final Color currentDayHighlightColor) {
		this.currentDayHighlightColor = currentDayHighlightColor;
	}
	
	/**
	 * This method returns the color used to highlight the currently selected date.
	 * 
	 * @return		The current currentDayHighlightColor
	 */
	public Color getCurrentDayHighlightColor() {
		return currentDayHighlightColor;
	}
	
	/**
	 * This method sets the color used to highlight the day the mouse is currently over.
	 * 
	 * @param highlightColor		The new highlightColor
	 */
	public void setHighlightColor(final Color highlightColor) {
		this.highlightColor = highlightColor;
	}
	
	/**
	 * This method return the current color used to highlight the day the mouse is currently over.
	 * 
	 * @return		The current highlightColor
	 */
	public Color getHighlightColor() {
		return highlightColor;
	}
	
	/**
	 * This method sets the image displayed when a day is marked as a dayItemDay.
	 * 
	 * @param dayItemImage		The new dayItemImage
	 */
	public void setDayItemImage(final Image dayItemImage) {
		this.dayItemImage = dayItemImage;
		final ImageIcon icon = new ImageIcon(dayItemImage);
		dayItemWidth = icon.getIconWidth();
		dayItemHeight = icon.getIconHeight();
	}
	
	/**
	 * This method returns the image currently displayed when a day is marked as a dayItemDay.
	 * 
	 * @return	The current dayItemImage
	 */
	public Image getDayItemImage() {
		return dayItemImage;
	}
	
	/**
	 * This method sets the foreground color used to draw the text for the month and year.
	 * 
	 * @param monthFGColor		The new monthFGColor
	 */
	public void setMonthFGColor(final Color monthFGColor) {
		this.monthFGColor = monthFGColor;
	}
	
	/**
	 * This method returns the foreground color used to draw the text for the month and year.
	 * 
	 * @return		The current monthFGColor
	 */
	public Color getMonthFGColor() {
		return monthFGColor;
	}
	
	/**
	 * This method sets the foreground color used to draw the weekday text.
	 * 
	 * @param weekNameFGColor		The new weekNameFGColor
	 */
	public void setWeekNameFGColor(final Color weekNameFGColor) {
		this.weekNameFGColor = weekNameFGColor;
	}
	
	/**
	 * This method returns the foreground color used to draw the weekday text.
	 * 
	 * @return		The current weekNameFGColor
	 */
	public Color getWeekNameFGColor() {
		return weekNameFGColor;
	}
	
	/**
	 * This method sets the foreground color used to draw the day numbers.
	 * 
	 * @param dayFGColor		The new dayFGColor
	 */
	public void setDayFGColor(final Color dayFGColor) {
		this.dayFGColor = dayFGColor;
	}
	
	/**
	 * This method returns the foreground color used to draw the day numbers.
	 * 
	 * @return		The current dayFGColor
	 */
	public Color getDayFGColor() {
		return dayFGColor;
	}
	
	/**
	 * This method sets the font used to draw the month and year.
	 * 
	 * @param monthFont		The new monthFont
	 */
	public void setMonthFont(final Font monthFont) {
		this.monthFont = monthFont;
		
		// Recalculate HEADER_HEIGHT
		final FontRenderContext context = new FontRenderContext(new AffineTransform(), false,false);
		final GlyphVector glyphVector = monthFont.createGlyphVector(context, "Month");
		//int stringWidth = (int)glyphVector.getLogicalBounds().getWidth();
		HEADER_HEIGHT = (int)glyphVector.getLogicalBounds().getHeight() + 5;
	}
	
	/**
	 * This method return the font used to draw the month and year
	 * 
	 * @return		The current monthFont
	 */
	public Font getMonthFont() {
		return monthFont;
	}
	
	/**
	 * This method sets the font used to draw the days of the week.
	 * 
	 * @param weekFont	The new weekFont
	 */
	public void setWeekNameFont(final Font weekFont) {
		this.weekFont = weekFont;
	}
	
	/**
	 * This method returns the font used to draw the days of the week.
	 * 
	 * @return	The current weekFont
	 */
	public Font getWeekNameFont() {
		return weekFont;
	}
	
	/**
	 * This method sets the font used to draw the day numbers.
	 * 
	 * @param dayFont		The new dayFont
	 */
	public void setDayFont(final Font dayFont) {
		this.dayFont = dayFont;
	}
	
	/**
	 * This method returns the font used to draw the day numbers.
	 * 
	 * @return	The current dayFont
	 */
	public Font getDayFont() {
		return dayFont;
	}
	
	/**
	 * This method sets the SimpleDateFormat used to determine how the month and year will
	 * be displayed.  By default they are display as Full Month Name (space) Year.  For example,
	 * June 2004.  By specifying a new SimpleDateFormat you can alter how it is displayed.
	 * 
	 * @param monthFormat	The new monthFormat to use
	 */
	public void setMonthFormat(final SimpleDateFormat monthFormat) {
		this.monthFormat = monthFormat;
		updateDisplayInfo();
	}
	
	/**
	 * This method returns the current SimpleDateFormat used to generate the text for the
	 * month and the year.
	 * 
	 * @return	The current monthFormat
	 */
	public SimpleDateFormat getMonthFormat() {
		return monthFormat;
	}
	
		/**
	 * This method returns the preferred size of the ZCalendar based upon its current settings.
	 * 
	 * @return	The Dimension for the preferred size.
	 */
	public Dimension getPreferredSize() {
		return new Dimension(1 + (7 * DAY_WIDTH), 1 + ((weeksInMonth + 1) * DAY_HEIGHT) + HEADER_HEIGHT);
	}
	
	/**
	 * This method determines the text for displaying the month and year, the weekday abbreviations,
	 * the first day of the month, the number of days in the month and the number of weeks in the
	 * month.
	 */
	protected void updateDisplayInfo() {
		// Get the month
		monthName = monthFormat.format(currentDate);
		
		// Get the days of the week
		final Calendar calendar = Calendar.getInstance(locale);
		final SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", locale);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		dayOfWeek[0] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		dayOfWeek[1] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		dayOfWeek[2] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		dayOfWeek[3] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		dayOfWeek[4] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		dayOfWeek[5] = dayFormat.format(calendar.getTime()).substring(0,1);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		dayOfWeek[6] = dayFormat.format(calendar.getTime()).substring(0,1);
		
		// Determine the date's information
		final Calendar dayCalendar = Calendar.getInstance(locale);
		dayCalendar.setTime(currentDate);
		currentDay = dayCalendar.get(Calendar.DAY_OF_MONTH);
		
		// Set the calendar to the first of the month and get month information
		dayCalendar.set(Calendar.DAY_OF_MONTH, 1);
		firstDay = dayCalendar.get(Calendar.DAY_OF_WEEK);
		maxDays = dayCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		weeksInMonth = dayCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * This method paints the calendar onto the specified Graphics component.
	 * 
	 * @param g		The Graphics component upon which the calendar should be painted
	 */
	protected void paintComponent(final Graphics g) {
		final Graphics2D g2 = (Graphics2D)g;
		
		// Get the size and determine day width and height for that size
		final Dimension size = getSize();
		dayWidth = (size.width - 1) / 7.0f;
		dayHeight = (float)(size.height - 1 - HEADER_HEIGHT) / (float)(weeksInMonth + 1);
		
		// Set the font
		g.setFont(monthFont);
		FontMetrics fontMetrics = g.getFontMetrics();
		final int monthFontHeight = fontMetrics.getHeight();
		
		// Draw the background
		g.setColor(getBackground());
		g.fillRect(0,0,size.width,size.height);
		
		// Draw week name background color
		g.setColor(weekNameBGColor);
		g.fillRect(0, HEADER_HEIGHT, size.width, (int)dayHeight);
		
		// Draw weekend background colors
		g.setColor(weekendBGColor);
		g.fillRect(0, (int)(HEADER_HEIGHT + dayHeight), (int)dayWidth, (int)(size.height - HEADER_HEIGHT + dayHeight));
		g.fillRect((int)(dayWidth * 6), (int)(HEADER_HEIGHT + dayHeight), (int)(size.width - (dayWidth * 6)), (int)(size.height - HEADER_HEIGHT + dayHeight));
		
		// Draw the left arrow
		if(compareDates(calendarModel.getLowDisplayDate(), currentDate)) {
			g.setColor(Color.black);
			final int arrowOffset = (HEADER_HEIGHT - 8) / 2;
			final int[] leftArrowX = new int[] {5, 9, 9, 15, 15, 9, 9};
			final int[] leftArrowY = new int[] {arrowOffset + 4, arrowOffset + 0, arrowOffset + 3, arrowOffset + 3, arrowOffset + 5, arrowOffset + 5, arrowOffset + 8};
			leftArrow = new Polygon(leftArrowX, leftArrowY, leftArrowX.length);
			if(arrowHighlight == LEFT_ARROW) {
				g.setColor(Color.lightGray);
				g.fillPolygon(leftArrow);
				g.setColor(Color.black);
			}
			else {
				g.fillPolygon(leftArrow);
			}
			g.drawPolygon(leftArrow);
		}
		
		// Draw the right arrow
		if(compareDates(currentDate, calendarModel.getHighDisplayDate())) {
			g.setColor(Color.black);
			final int arrowOffset = (HEADER_HEIGHT - 8) / 2;
			final int[] rightArrowX = new int[] {size.width - 5, size.width - 9, size.width - 9, size.width - 15, size.width - 15, size.width - 9, size.width - 9};
			final int[] rightArrowY = new int[] {arrowOffset + 4, arrowOffset + 0, arrowOffset + 3, arrowOffset + 3, arrowOffset + 5, arrowOffset + 5, arrowOffset + 8};
			rightArrow = new Polygon(rightArrowX, rightArrowY, rightArrowX.length);
			if(arrowHighlight == RIGHT_ARROW) {
				g.setColor(Color.lightGray);
				g.fillPolygon(rightArrow);
				g.setColor(Color.black);
			}
			else {
				g.fillPolygon(rightArrow);
			}
			g.drawPolygon(rightArrow);
		}
		
		// Draw the name of the month and the custom text
		g.setColor(monthFGColor);
		int fontWidth = fontMetrics.stringWidth(monthName + calendarModel.getCustomText());
		switch(monthAlignment) {
			case ALIGN_MIDDLE_LEFT: // left alignment
				g.drawString(monthName + calendarModel.getCustomText(), leftArrow.getBounds().x + leftArrow.getBounds().width + 5, monthFontHeight);
				break;
			
			case ALIGN_MIDDLE_RIGHT: // Right alignment
				g.drawString(monthName + calendarModel.getCustomText(), size.width - fontWidth - (size.width - rightArrow.getBounds().x) - 4, monthFontHeight);
				break;
			
			default: // Center alignment
				g.drawString(monthName + calendarModel.getCustomText(), (size.width - fontWidth) / 2, monthFontHeight);
				break;
		}
		
		// Draw calendar bounding box
		g.setColor(Color.black);
		g.drawRect(0, HEADER_HEIGHT, size.width - 1, size.height - HEADER_HEIGHT - 1);
		
		// Reset the font and determine sizes
		g.setFont(weekFont);
		g.setColor(Color.black);
		fontMetrics = g.getFontMetrics();
		int fontHeight = fontMetrics.getHeight();
		float currentYPos = HEADER_HEIGHT;
		float currentXPos = dayWidth;
		
		// Draw the vertical lines
		for(int i = 0; i < 6; i++) {
			g2.draw(new Line2D.Float(currentXPos, currentYPos, currentXPos, size.height));
			currentXPos += dayWidth;
		}
		
		// Draw the days of the week
		currentXPos = 0;
		g.setColor(weekNameFGColor);
		for(int i = 0; i < 7; i++) {
			fontWidth = fontMetrics.stringWidth(dayOfWeek[i]);
			g2.drawString(dayOfWeek[i], currentXPos + ((dayWidth - fontWidth)/2), currentYPos + fontHeight - 1);
			currentXPos += dayWidth;
		}
		
		// Draw the horizontal lines
		g.setColor(Color.black);
		for(int i = 0; i < weeksInMonth; i++) {
			currentYPos += dayHeight;
			g2.draw(new Line2D.Double(0, currentYPos, size.width-1, currentYPos));
		}
		
		// Reset the font for the days
		g.setFont(dayFont);
		g.setColor(dayFGColor);
		fontMetrics = g.getFontMetrics();
		fontHeight = fontMetrics.getHeight();
		
		// Draw day numbers
		currentXPos = dayWidth * firstDay;
		currentYPos = HEADER_HEIGHT + (dayHeight * 2) - 1;
		int dayOfWeek = firstDay;
		for(int day = 1; day <= maxDays; day++) {
			// Draw the dayItemImage - if appropriate
			if(checkDayItem(day)) {
				switch(dayItemAlignment) {
					case ALIGN_TOP_LEFT: // Top left alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayWidth) + 1, (int)(currentYPos - dayHeight) + 2, this);
						break;
					
					case ALIGN_TOP_CENTER: // Top center alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - (dayWidth / 2) - (dayItemWidth / 2)), (int)(currentYPos - dayHeight) + 2, this);
						break;
					
					case ALIGN_TOP_RIGHT: // Top right alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayItemWidth), (int)(currentYPos - dayHeight) + 2, this);
						break;
					
					case ALIGN_MIDDLE_LEFT: // Middle left alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayWidth) + 1, (int)(currentYPos - (dayHeight / 2) - (dayItemHeight / 2)), this);
						break;
					
					case ALIGN_MIDDLE_CENTER: // Middle Center alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - (dayWidth / 2) - (dayItemWidth / 2)), (int)(currentYPos - (dayHeight / 2) - (dayItemHeight / 2)), this);
						break;
					
					case ALIGN_MIDDLE_RIGHT: // Middle right alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayItemWidth), (int)(currentYPos - (dayHeight / 2) - (dayItemHeight / 2)), this);
						break;
					
					case ALIGN_BOTTOM_LEFT: // Bottom left alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayWidth) + 1, (int)(currentYPos - dayItemHeight), this);
						break;
					
					case ALIGN_BOTTOM_CENTER: // Bottom center alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - (dayWidth / 2) - (dayItemWidth / 2)), (int)(currentYPos - dayItemHeight), this);
						break;
					
					case ALIGN_BOTTOM_RIGHT: // Bottom right alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - dayItemWidth), (int)(currentYPos - dayItemHeight), this);
						break;
					
					default: // Center alignment
						g2.drawImage(dayItemImage, (int)(currentXPos - (dayWidth / 2) - (dayItemWidth / 2)), (int)(currentYPos - (dayHeight / 2) - (dayItemHeight / 2)), this);
						break;
				}
			}
			
			if(day == currentDay) { 
				// Highlight this box as it is the current day of the month
				g2.setColor(currentDayHighlightColor);
				g2.draw(new Rectangle2D.Double(currentXPos - dayWidth + 1, currentYPos - dayHeight + 2, dayWidth - 2, dayHeight - 2));
				g2.draw(new Rectangle2D.Double(currentXPos - dayWidth + 2, currentYPos - dayHeight + 3, dayWidth - 4, dayHeight - 4));
				g2.setColor(dayFGColor);
			}
			
			// Draw the day
			switch(dayAlignment) {
				case ALIGN_TOP_LEFT: // Top left alignment
					g2.drawString(""+day, (float)(currentXPos - dayWidth + 2.0f), currentYPos - dayHeight + fontHeight);
					break;
				
				case ALIGN_TOP_CENTER: // Top center alignment
					g2.drawString(""+day, currentXPos - (dayWidth / 2) - (float)(fontMetrics.stringWidth(""+day) / 2), currentYPos - dayHeight + fontHeight);
					break;
				
				case ALIGN_TOP_RIGHT: // Top right alignment
					g2.drawString(""+day, currentXPos - (float)fontMetrics.stringWidth(""+day) - 1.0f, currentYPos - dayHeight + fontHeight);
					break;
				
				case ALIGN_MIDDLE_LEFT: // Middle left alignment
					g2.drawString(""+day, (float)(currentXPos - dayWidth + 2.0f), currentYPos - (dayHeight / 2) + (fontHeight / 2));
					break;
				
				case ALIGN_MIDDLE_CENTER: // Middle Center alignment
					g2.drawString(""+day, (float)(currentXPos - (dayWidth / 2) - (fontMetrics.stringWidth(""+day) / 2)), currentYPos - (dayHeight / 2) + (fontHeight / 2));
					break;
				
				case ALIGN_MIDDLE_RIGHT: // Middle right alignment
					g2.drawString(""+day, currentXPos - (float)fontMetrics.stringWidth(""+day) - 1.0f, currentYPos - (dayHeight / 2) + (fontHeight / 2));
					break;
				
				case ALIGN_BOTTOM_LEFT: // Bottom left alignment
					g2.drawString(""+day, (float)(currentXPos - dayWidth + 2.0f), currentYPos - 1.0f);
					break;
				
				case ALIGN_BOTTOM_CENTER: // Bottom center alignment
					g2.drawString(""+day, (float)(currentXPos - (dayWidth / 2) - (fontMetrics.stringWidth(""+day) / 2)), currentYPos - 1.0f);
					break;
				
				case ALIGN_BOTTOM_RIGHT: // Bottom right alignment
					g2.drawString(""+day, currentXPos - (float)fontMetrics.stringWidth(""+day) - 1.0f, currentYPos - 1.0f);
					break;
				
				default: // Center alignment
					g2.drawString(""+day, (float)(currentXPos - (dayWidth / 2) - (fontMetrics.stringWidth(""+day) / 2)), currentYPos - (dayHeight / 2) + (fontHeight / 2));
					break;
			}
				
			// Increment placement values
			currentXPos += dayWidth;
			dayOfWeek++;
			if(dayOfWeek > 7) {
				dayOfWeek = 1;
				currentXPos = dayWidth;
				currentYPos += dayHeight;
			}
		}
		
		// Draw the green square for the selected day
		if(currentXMouse != -1 || currentYMouse != -1) {
			final int hoverDay = (currentYMouse * 7) + currentXMouse + 1 - (firstDay - 1);
			if(hoverDay > 0 && hoverDay <= maxDays) {
				g.setColor(highlightColor);
				g2.draw(new Rectangle2D.Double((currentXMouse * dayWidth) + 1, (currentYMouse * dayHeight) + 1 + HEADER_HEIGHT + dayHeight, dayWidth - 2, dayHeight - 2));
				g2.draw(new Rectangle2D.Double((currentXMouse * dayWidth) + 2, (currentYMouse * dayHeight) + 2 + HEADER_HEIGHT + dayHeight, dayWidth - 4, dayHeight - 4));
			}
		}
	}
	
	/**
	 * This method checks to see if the dayItemImage should be drawn for the specified day.
	 * 
	 * @param day	The day, within the current month, to check
	 * 
	 * @return		True if the dayItemImage should be drawn, false otherwise
	 */
	private boolean checkDayItem(final int day) {
		// Create the calendar and date for the specified day
		final Calendar itemCal = Calendar.getInstance();
		itemCal.setTime(currentDate);
		final String testDate = itemCal.get(Calendar.YEAR) + "-" + itemCal.get(Calendar.MONTH) + "-" + day;
		
		// Return true if the date is contained within the dayItemDates HashMap
		return calendarModel.getDayItemDates().containsKey(testDate);
	}
	
	/**
	 * This method returns true if the first date comes before the second date.  This comparison
	 * is performed using the month and year parts of the dates only.
	 * 
	 * @param firstDate		The first date to compare
	 * @param secondDate	The second date to compare
	 * 
	 * @return		True if firstDate comes before secondDate, as per criteria stated above.
	 */
	private boolean compareDates(final Date firstDate, final Date secondDate) {
		// Check for nulls
		if(firstDate == null || secondDate == null)
			return true;
		
		// Create calendars
		final Calendar firstCal = Calendar.getInstance();
		firstCal.setTime(firstDate);
		
		final Calendar secondCal = Calendar.getInstance();
		secondCal.setTime(secondDate);
		
		// Get year and month
		final int firstYear = firstCal.get(Calendar.YEAR);
		final int firstMonth = firstCal.get(Calendar.MONTH);
		
		final int secondYear = secondCal.get(Calendar.YEAR);
		final int secondMonth = secondCal.get(Calendar.MONTH);
		
		// Compare year
		if(firstYear < secondYear)
			return true;
		
		if(firstMonth < secondMonth)
			return true;
		
		// Dates are equivalent
		return false;
	}
	
	/**
	 * This method determines if a day or arrow needs to be highlighted.
	 * 
	 * @param point		The current mouse point to examine.
	 */
	private void highlightDay(final Point point) {
		int x = point.x;
		int y = point.y;
		
		if(y > (HEADER_HEIGHT + dayHeight)) { // Over the calendar
			y -= (HEADER_HEIGHT + dayHeight);
			y /= dayHeight;
			x /= dayWidth;
			
			// If this is a new day box then repaint
			if(x != currentXMouse || y != currentYMouse) {
				currentXMouse = x;
				currentYMouse = y;
				repaint();
			}
			else if(arrowHighlight != NO_ARROW) {
				arrowHighlight = NO_ARROW;
				repaint();
			}
		}
		else { // Not over the calendar
			if(currentXMouse != -1 || currentYMouse != -1) {
				currentXMouse = currentYMouse = -1;
				repaint();
			}
			else if(arrowHighlight != NO_ARROW) {
				arrowHighlight = NO_ARROW;
				repaint();
			}
		}
	}
	
	/**
	 * This method is called when a user clicks on the table.  If the user clicked on a valid
	 * date, then that date is highlighted.
	 * 
	 * @param point		The current mouse location to examine.
	 */
	private void selectDay(final Point point) {
		//int x = point.x;
		final int y = point.y;
		
		if(y > (HEADER_HEIGHT + dayHeight)) { // Over the calendar	
			final int selectedDay = (currentYMouse * 7) + currentXMouse + 1 - (firstDay - 1);
			if(selectedDay > 0 && selectedDay <= maxDays) {
				// A valid day was clicked on, so notify listeners of the selection
				final Calendar newCalendar = Calendar.getInstance();
				newCalendar.setTime(currentDate);
				newCalendar.set(Calendar.DAY_OF_MONTH, selectedDay);
				firePropertyChange("SelectedDay", null, newCalendar.getTime());
			}
		}
	}
	
	/**
	 * This method updates the ZCalendar so that it can display a new month.
	 * 
	 * @param monthOffset		This is the number of months to add to the current month
	 * 							in order to create the new month.  This value is usually
	 * 							1 or -1.
	 */
	private void displayNewMonth(final int monthOffset) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MONTH, monthOffset);
		setCurrentDate(calendar.getTime());
		repaint();
	}
	
	/**
	 * This method is called when the user clicks the mouse.  It determines is the left or right
	 * arrows were clicked, or if the user clicked on a date.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseClicked(final MouseEvent event) {
		if(leftArrow.getBounds().contains(event.getPoint())) { // User clicked on left arrow
			if(compareDates(calendarModel.getLowDisplayDate(), currentDate))
				displayNewMonth(-1);
		}
		else if(rightArrow.getBounds().contains(event.getPoint())) { // User clicked on right arrow
			if(compareDates(currentDate, calendarModel.getHighDisplayDate()))
				displayNewMonth(1);
		}
		else { // User clicked on a date
			selectDay(event.getPoint());
		}
	}
	
	/**
	 * This method is called when the user presses the mouse button.  It does nothing.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mousePressed(final MouseEvent event) {}
	
	/**
	 * This method is called when the user releases the mouse button.  It does nothing.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseReleased(final MouseEvent event) {}
	
	/**
	 * This method is called when the user enters the ZCalendar with their mouse.
	 * It does nothing.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseEntered(final MouseEvent event) {}
	
	/**
	 * This method is called when the user exits the ZCalendar with their mouse.
	 * It does nothing.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseExited(final MouseEvent event) {}
	
	// MouseMotionListeners
	/**
	 * This method is called when the user moves their mouse over the ZCalendar.  It determines
	 * if the left or right arrows, or a day needs to be highlighted or unhighlighted.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseMoved(final MouseEvent event) {
		if(leftArrow != null && leftArrow.getBounds().contains(event.getPoint())) { // Mouse over left arrow
			if(arrowHighlight != LEFT_ARROW) {
				arrowHighlight = LEFT_ARROW;
				repaint();
			}
		}
		else if(rightArrow != null && rightArrow.getBounds().contains(event.getPoint())) { // Mouse over right arrow
			if(arrowHighlight != RIGHT_ARROW) {
				arrowHighlight = RIGHT_ARROW;
				repaint();
			}
		}
		else { // Mouse over day
			highlightDay(event.getPoint());
		}
	}
	
	/**
	 * This method is called when the user clicks and drags their mouse over the ZCalendar.
	 * It does nothing.
	 * 
	 * @param event		The MouseEvent
	 */
	public void mouseDragged(final MouseEvent event) {}
	
	// PropertyChangeListener method
	/**
	 * This method is called when a property changes in various objects.  If a property changes
	 * in the CalendarModel then the ZCalendar is repainted to ensure that property is correctly
	 * displayed.
	 * 
	 * @param event		The PropertyChangeEvent
	 */
	public void propertyChange(final PropertyChangeEvent event) {
		if(event.getSource() == calendarModel) { // CalendarModel property changed
			repaint();
		}
	}
}
