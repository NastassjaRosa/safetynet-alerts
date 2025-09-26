package com.safetynet.safetynetalerts.util;


import com.safetynet.safetynetalerts.repository.DataRepository;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public final class SaveUtil {

private SaveUtil() {

}
    /**
     * Sauvegarde les données via le DataRepository avec gestion des erreurs.
     *
     * @param repo  le repository qui gère le fichier JSON
     * @param action description de l’action ("ajout", "mise à jour", "suppression")
     */
    public static void save(DataRepository repo, String action) {
        try {
            repo.save();
            log.debug("Sauvegarde réussie après {}", action);
        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde après {}", action, e);
            throw new RuntimeException("Impossible de sauvegarder les données après " + action, e);
        }
    }

}
