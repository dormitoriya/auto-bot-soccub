package com.dormitory.autobotsoccub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;
import org.telegram.telegrambots.logging.BotsFileHandler;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;

/**
 * @author nikolaev
 * @brief Main class to create bots
 * @date 23.09.2017
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude =
		LiquibaseAutoConfiguration.class
)
public class Main {

	private static final String LOGTAG = "MAIN";

	public static void main(String[] args) {

		BotLogger.setLevel(Level.ALL);
		BotLogger.registerLogger(new ConsoleHandler());
		try {
			BotLogger.registerLogger(new BotsFileHandler());
		} catch (IOException e) {
			BotLogger.severe(LOGTAG, e);
		}

		try {
			ApiContextInitializer.init();
            ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(Main.class, args);
			try {
				new TelegramBotsApi().registerBot(configurableApplicationContext.getBean(Bot.class));
			} catch (TelegramApiException e) {
				BotLogger.error(LOGTAG, e);
			}
		} catch (Exception e) {
			BotLogger.error(LOGTAG, e);
		}
	}
}
