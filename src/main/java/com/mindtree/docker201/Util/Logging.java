package com.mindtree.docker201.Util;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Logging {

	Logger logger = LoggerFactory.getLogger("Activitylog");

	public void log(String time, String action) {
		LinkedHashMap<String, Object> l1 = new LinkedHashMap<>();
		l1.put("Timestamp", time);
		l1.put("Action", action);
		logger.info(l1.toString());
	}

	public void genLog(String type, String message) {
		if (type != null && message != null && !type.equalsIgnoreCase("") && !message.equalsIgnoreCase("")) {
			try {
				logger.getClass().getMethod(type.toLowerCase()).invoke(message);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
				this.genLog("info", "Logging default message");
			}
		} else {
			this.genLog("info", "Logging default message");
		}
	}
}
