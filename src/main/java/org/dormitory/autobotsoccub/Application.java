package org.dormitory.autobotsoccub;

import org.dormitory.autobotsoccub.bot.SoccubBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws TelegramApiRequestException {
		ApiContextInitializer.init();
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		SoccubBot bot = ctx.getBean(SoccubBot.class);
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		telegramBotsApi.registerBot(bot);
	}
}
