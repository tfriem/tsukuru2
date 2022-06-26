package de.tfriem.tsukuru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import de.tfriem.tsukuru.games.TwitchCredentials;

@SpringBootApplication
@EnableConfigurationProperties(TwitchCredentials.class)
public class TsukuruApplication {

	public static void main(String[] args) {
		SpringApplication.run(TsukuruApplication.class, args);
	}

}
