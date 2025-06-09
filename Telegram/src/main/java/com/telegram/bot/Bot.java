package com.telegram.bot;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class Bot {
/*
	public static void main(String[] args) throws TelegramApiException {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(ctx.getBean("BotService", TelegramLongPollingBot.class));
		SpringApplication.run(Bot.class, args);
	}
  */      
        public static void main(String[] args) {
            ConfigurableApplicationContext ctx = SpringApplication.run(Bot.class, args);
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(ctx.getBean("botService", AbilityBot.class));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        
        @Bean
        public RestTemplate RestTemplate() {
            return new RestTemplate();
        }

}
