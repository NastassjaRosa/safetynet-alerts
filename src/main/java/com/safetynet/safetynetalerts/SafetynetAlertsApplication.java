package com.safetynet.safetynetalerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d’entrée de l’application SafetyNet Alerts.
 * Lance le contexte Spring Boot.
 */


@SpringBootApplication
public class SafetynetAlertsApplication {

    /**
     * Constructeur par défaut requis par Spring.
     * Ne fait rien de spécial.
     */
    public SafetynetAlertsApplication() {
        // no-op
    }

    /**
     * Démarre l’application Spring Boot.
     *
     * @param args arguments de ligne de commande (non utilisés)
     */

    public static void main(String[] args) {
        SpringApplication.run(SafetynetAlertsApplication.class, args);
    }

}
