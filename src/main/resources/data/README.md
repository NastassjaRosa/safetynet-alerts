# 📑 Comprendre `data.json` et la création des modèles Java

Ce guide explique comment fonctionne le fichier `data.json` et comment ses données deviennent utilisables en Java à
l’aide de Jackson et Lombok.

---

## 1. Structure générale du fichier `data.json`

Le fichier `data.json` contient plusieurs **collections** (listes) d’objets.  
Chaque collection porte un nom au pluriel, par exemple : `persons`, `firestations`, `medicalrecords`.

**Exemple d’extrait de `data.json` :**

```json
{
  "persons": [
    {
      "firstName": "John",
      "lastName": "Boyd",
      "address": "1509 Culver St",
      "city": "Culver",
      "zip": "97451",
      "phone": "841-874-6512",
      "email": "john.boyd@email.com"
    }
    
  ],
  "firestations": [
    {
      "address": "1509 Culver St",
      "station": "3"
    }
    
  ],
  "medicalrecords": [
    {
      "firstName": "John",
      "lastName": "Boyd",
      "birthdate": "03/06/1984",
      "medications": ["aznol:350mg", "hydrapermazol:100mg"],
      "allergies": ["nillacilan"]
    }
    
  ]
}
```

---

## 2. Règle de transformation JSON → modèles Java

- **Chaque collection** du JSON (ex : `persons`) devient une **liste** d’objets Java (`List<Person>`).
- **Chaque objet** du JSON (ex : une personne) devient une **instance d’une classe Java** (ex : `Person`).
- **Chaque propriété** de l’objet JSON devient un **champ privé** de la classe Java.

**Exemple :**

```json
{
  "firstName": "John",
  "lastName": "Boyd"
}
```

devient :

```java
public class Person {
    private String firstName;
    private String lastName;
}
```

---

## 3. Comment est constituée une classe modèle Java ?

- Le **nom de la classe** correspond au singulier de la collection, en **PascalCase**.
    - Exemple : `persons` → `Person`, `firestations` → `FireStation`
- **Chaque propriété** du JSON devient une **variable privée** dans la classe.
- Les **types** sont adaptés (`String`, `List<String>`, etc.).

**Exemple pour la classe `Person` :**

```java
import lombok.Data;

@Data // Lombok génère les getters et setters automatiquement
public class Person {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
```

---

## 4. Pourquoi un wrapper `Data` ?

Pour lire toutes les collections d’un seul coup, on crée une classe racine appelée par convention `Data`.  
Cette classe contient une liste pour chaque collection du JSON.

**Exemple :**

```java
import lombok.Data;

import java.util.List;

@Data
public class Data {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
}
```

**But du wrapper :**  
Cela permet à Jackson de lire tout le fichier JSON d’un coup et de reconstituer l’arborescence complète (plusieurs
listes d’objets).

---

## 5. Chargement automatique avec Jackson

Jackson est une bibliothèque Java qui permet de convertir facilement du JSON en objets Java (**désérialisation**).

- **Jackson lit le fichier `data.json`**
- Il crée un objet `Data` et remplit les listes de `Person`, `FireStation` et `MedicalRecord` à partir du JSON.

**Exemple de code :**

```java
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

ObjectMapper objectMapper = new ObjectMapper();
Data data = objectMapper.readValue(new File("src/main/resources/data/data.json"), Data.class);
// data.getPersons() contient la liste des personnes
```

**En résumé :**  
Jackson « désérialise » le JSON, c’est-à-dire qu’il transforme le texte JSON en objets Java exploitables.

---

## 6. Rôle de Lombok

**Lombok** est une bibliothèque qui simplifie le code Java.

- L’annotation `@Data` placée sur la classe génère automatiquement :
    - les **getters** (méthodes pour lire les variables)
    - les **setters** (méthodes pour modifier les variables)
    - les méthodes utiles comme `toString()`, `equals()`, etc.

**Avantage :**  
Pas besoin d’écrire manuellement tout le code « boilerplate » (répétitif).

---

## 7. Schéma global

```text
data.json
   │
   ▼
Jackson ObjectMapper (désérialise)
   │
   ▼
Data                        ← objet racine (wrapper)
 ├─ List<Person> persons    ← chaque entrée JSON = 1 Person
 ├─ List<FireStation> firestations
 └─ List<MedicalRecord> medicalrecords
```

---

## 8. Lexique simplifié

- **Désérialiser** : convertir un texte JSON en objets Java.
- **Objet racine (wrapper)** : une classe qui regroupe toutes les collections du JSON.
- **Collection** : une liste d’objets (ex : la liste des personnes).

---

## 9. Pour résumer

- Chaque collection du JSON devient une **liste** d’objets Java.
- Chaque propriété devient une **variable privée** dans la classe correspondante.
- Le **wrapper `Data`** permet de lire toutes les données d’un coup.
- **Jackson** fait la transformation JSON → Java automatiquement.
- **Lombok** simplifie l’écriture des classes modèles.

