package org.mdtech.moneymover.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ApplicationProperties {
	
	private static final Logger LOG = Logger.getLogger(ApplicationProperties.class);
	private static final Properties props = new Properties();
	
	static {
		InputStream is = ApplicationProperties.class.getClassLoader().getResourceAsStream("moneymover.properties");
		try {
			if (is != null) {
				props.load(new InputStreamReader(is));
			} else {
				LOG.warn("Properties file not found. Will be using default values.");
			}
		} catch(IOException e) {
			LOG.error("Failed to load properties: " + e.getMessage());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getProperty(String name) {
		return props.getProperty(name);
	}

}
