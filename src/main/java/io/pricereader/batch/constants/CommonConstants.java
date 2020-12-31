package io.pricereader.batch.constants;

import org.slf4j.Logger;

import io.pricereader.batch.logger.ApplicationLogger;

public class CommonConstants {
	private CommonConstants() {}
	public static final Logger LOGGER = ApplicationLogger.getInstance().getLogger();
}
