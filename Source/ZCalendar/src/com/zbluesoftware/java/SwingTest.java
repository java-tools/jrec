package com.zbluesoftware.java;

import java.awt.Color;
import java.awt.Rectangle;

import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JFrame;

import com.zbluesoftware.java.swing.DefaultCalendarModel;
import com.zbluesoftware.java.swing.ZCalendar;
//import com.zbluesoftware.java.swing.ZFontChooser;
//import com.zbluesoftware.java.swing.ZFontChooserRTF;

public class SwingTest {

	// Main method
	public static void main(String[] args) {
//		if(args.length == 0) {
//			displayUsage();
//			return;
//		}

//		String appName = args[0];
//		if(appName.equals("ZFontChooser")) {
//			displayFontChooser();
//		}
//		else if(appName.equals("ZCalendar")) {
			displayCalendar();
//		}
	}

	// Displays how the class should be used
	public static void displayUsage() {
		System.out.println("Usage:");
		System.out.println("SwingTest (ZFontChooser|ZCalendar)");
	}

	// Displays the font chooser
	public static void displayFontChooser() {
		Color color = new Color(51,255,51,50);
		System.out.println("color: " + color.getRGB());


		JFrame frame = new JFrame("Swing Component Test");

		// Create zFontChooser
		//ZFontChooserRTF chooser = new ZFontChooserRTF();
		//chooser.setBackground(Color.white);
		//frame.getContentPane().add(chooser);

		// Display the application
		//frame.setBounds(new Rectangle(100,100,400,200));
		//frame.setVisible(true);

		// Display the font chooser
		//System.out.println("Font: " + ZFontChooserRTF.showDialog(frame));

		//chooser.getFontChooserModel().setSampleText("New Sample");
		//chooser.getFontChooserRTFModel().removeOption("Underline");
	}

	// Displays the calendar
	@SuppressWarnings("unchecked")
	public static void displayCalendar() {
		JFrame frame = new JFrame("Calendar");

		// Create zCalendar
		DefaultCalendarModel model = new DefaultCalendarModel();
		Calendar cal = Calendar.getInstance();
		cal.set(2005,3,5);
		model.setHighDisplayDate(cal.getTime());
		cal.set(2004,0,1);
		model.setLowDisplayDate(cal.getTime());

		HashMap hashMap = new HashMap();
		hashMap.put("2004-6-6", "2004-6-6");
		hashMap.put("2004-6-8", "2004-6-8");
		hashMap.put("2004-5-4", "2004-5-4");
		model.setDayItemDates(hashMap);

		ZCalendar calendar = new ZCalendar(model);
		frame.getContentPane().add(calendar);

		// Display the application
		frame.setBounds(new Rectangle(100,100,200,200));
		frame.setVisible(true);

		model.addDayItemDate("2004-6-9");

		// Display the calendar
		System.out.println("Date: " + ZCalendar.showDialog(frame));

	}
}