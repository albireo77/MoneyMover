package org.mdtech.moneymover.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	
	private static final Logger LOG = Logger.getLogger(ApplicationProperties.class);
	private static final Properties props = new Properties();
	
	static {
		try (InputStream is = ApplicationProperties.class.getClassLoader().getResourceAsStream("moneymover.properties")) {
			if (is != null) {
				props.load(is);
			} else {
				LOG.warn("Properties file not found. Will be using default values.");
			}
		} catch(IOException e) {
			LOG.error("Failed to load properties: " + e.getMessage());
		}
	}
	
	public static String getProperty(String name) {
		return props.getProperty(name);
	}

}
