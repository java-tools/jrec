package net.sf.RecordEditor.utils.swing2.helpers;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

import javax.swing.event.ChangeEvent;

public class EventDetails {

	public final FocusEvent focusEvent;
	public final ActionEvent actionEvent;
	public final ChangeEvent changeEvent;
	public final Object source;
	
	public EventDetails(FocusEvent focusEvent) {
		this(focusEvent, null, null, focusEvent==null? null : focusEvent.getSource());
	}

	public EventDetails(ActionEvent actionEvent) {
		this(null, actionEvent, null, actionEvent==null? null : actionEvent.getSource());
	}

	public EventDetails(ChangeEvent changeEvent) {
		this(null, null, changeEvent, changeEvent== null? null : changeEvent.getSource());
	}

	protected EventDetails(FocusEvent focusEvent, ActionEvent actionEvent,
			ChangeEvent changeEvent, Object source) {
		super();
		this.focusEvent = focusEvent;
		this.actionEvent = actionEvent;
		this.changeEvent = changeEvent;
		this.source = source;
	}
	
	
	
}
