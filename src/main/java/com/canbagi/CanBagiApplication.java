package com.canbagi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class CanBagiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanBagiApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(CanBagiApplication.class);
		for (String s : Arrays.stream(context.getBeanDefinitionNames())
				.toList()) {
			System.out.println(s);
		}

	}

	/*
		Gelişmiş Modüler Monolith Yapısı (Clean Code + DDD + Common/Config/Core)
	 */

	/*
						| Modül        | Sorumluluk                                                    |
				| ------------ | ------------------------------------------------------------- |
				| auth         | JWT + Refresh Token, Firebase Auth, Rol bazlı erişim          |
				| donor        | Donör profili CRUD, lokasyon, kan grubu, bağış geçmişi        |
				| hospital     | Hastane CRUD, kan talebi oluşturma, onay workflow             |
				| bloodrequest | Kan talebi CRUD, donör eşleştirme, event-driven status        |
				| donation     | Kan bağışlarını takip etme, onaylama, audit logging           |
				| notification | Asenkron bildirimler (Email / SMS / FCM), retry, event-driven |

	 */

}
