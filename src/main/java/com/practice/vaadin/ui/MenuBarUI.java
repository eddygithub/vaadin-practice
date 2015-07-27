package com.practice.vaadin.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

public class MenuBarUI extends MenuBar {
	private static final long serialVersionUID = 1L;

	public MenuBarUI() {
		init();
	}

	private void init() {
		MenuItem customerItems = addItem("Customer", null, null);
		customerItems.addItem("Entry", null, null);
		customerItems.addItem("Search", null, null);

		MenuItem reports = addItem("Reports", null, null);
		reports.addItem("Annual Summary report", null);
	}
}
