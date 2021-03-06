package edu.kis.powp.jobs2d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.CustomLine;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.DrawAdapter;
import edu.kis.powp.jobs2d.events.Figure;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		application.addTest("Figure Joe 1", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.FIGURE_JOE_1));
		application.addTest("Figure Joe 2", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.FIGURE_JOE_2));
		application.addTest("Command test", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.COMMAND_TEST));
		application.addTest("Complex command test", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.COMPLEX_COMMAND));
		application.addTest("Rectangle CC", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.RECTANGLE_COMPEX_COMMAND));
		application.addTest("Rhombus CC", new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager(), Figure.RHOMBUS_COMPLEX_COMMAND));
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new LineDrawerAdapter();
		DriverFeature.addDriver("Regular line", testDriver);

		Job2dDriver dottedDriver = new LineDrawerAdapter(LineFactory.getDottedLine());
		DriverFeature.addDriver("Dotted line", dottedDriver);

		Job2dDriver specialDriver = new LineDrawerAdapter(LineFactory.getSpecialLine());
		DriverFeature.addDriver("Special line", specialDriver);

		Job2dDriver customDriver = new LineDrawerAdapter(new CustomLine(Color.DARK_GRAY, 1, false));
		DriverFeature.addDriver("Custom line", customDriver);


		DriverFeature.updateDriverInfo();
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Application app = new Application("2d jobs Visio");
			DrawerFeature.setupDrawerPlugin(app);

			DriverFeature.setupDriverPlugin(app);
			setupDrivers(app);
			setupPresetTests(app);
			setupLogger(app);

			app.setVisibility(true);
		});
	}

}
