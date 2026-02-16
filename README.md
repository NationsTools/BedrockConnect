# BedrockConnect - Version Améliorée 🎮

DNS personnalisé pour Minecraft Bedrock permettant aux consoles de se connecter à des serveurs personnalisés.

## 🎯 Nouveautés de cette Version

### ✨ Système de Gestion d'Images Centralisé
- **Fichier `images.json`** : Toutes les URLs d'images dans un seul endroit
- **Classe `ImageAssets`** : Gestion élégante des ressources d'images
- **Plus besoin de modifier le code Java** pour changer une image !

### 🔄 Maintenance Facilitée
- **`minecraft-versions.json`** : Configuration centralisée des versions supportées
- **Scripts d'aide** : Outils automatisés pour les mises à jour
- **Guide MAINTENANCE.md** : Instructions détaillées pas à pas

## 📁 Structure du Projet

```
bedrockconnect/
├── serverlist-server/
│   ├── src/main/
│   │   ├── com/pyratron/pugmatt/bedrockconnect/
│   │   │   ├── config/
│   │   │   │   ├── ImageAssets.java      ← 🆕 Gestion des images
│   │   │   │   ├── Language.java
│   │   │   │   └── ...
│   │   │   └── ...
│   │   └── resources/
│   │       ├── images.json               ← 🆕 URLs d'images centralisées
│   │       ├── language.json
│   │       └── minecraft-versions.json   ← 🆕 Configuration des versions MC
│   └── pom.xml
├── scripts/
│   ├── check-minecraft-updates.sh        ← 🆕 Vérifier les mises à jour
│   └── add-minecraft-version.sh          ← 🆕 Ajouter une version facilement
├── MAINTENANCE.md                        ← 🆕 Guide de maintenance complet
└── README.md                             ← Ce fichier
```

## 🚀 Démarrage Rapide

### Prérequis
- Java 8 ou supérieur
- Maven 3.6+

### Installation

```bash
# Cloner le projet
git clone <votre-repo>
cd bedrockconnect

# Compiler
cd serverlist-server
mvn clean package

# Lancer
java -jar target/BedrockConnect-1.0-SNAPSHOT.jar
```

## 🖼️ Gestion des Images

### Modifier une Image Existante

Éditez simplement `serverlist-server/src/main/resources/images.json` :

```json
{
  "nationsglory": {
    "hub": "https://nouvelle-url.com/Hub.png"
  }
}
```

**Pas besoin de recompiler !** Redémarrez juste le serveur.

### Ajouter une Nouvelle Image

```json
{
  "nationsglory": {
    "nouveau_serveur": "https://url.com/image.png"
  }
}
```

Puis utilisez dans le code :
```java
String imageUrl = BedrockConnect.getConfig().getImageAssets()
    .getNationsGloryIcon("nouveau_serveur");
```

## 🔄 Maintenance des Versions Minecraft

### Méthode Automatique (Recommandée)

```bash
# Lancer l'assistant interactif
./scripts/add-minecraft-version.sh

# Suivez les instructions à l'écran
```

### Vérifier les Mises à Jour

```bash
# Vérifier s'il y a de nouvelles versions disponibles
./scripts/check-minecraft-updates.sh
```

### Méthode Manuelle

Consultez le guide détaillé : **[MAINTENANCE.md](MAINTENANCE.md)**

## 📝 Configuration

### Fichiers de Configuration

| Fichier | Description | Fréquence de modification |
|---------|-------------|---------------------------|
| `images.json` | URLs des images de serveurs | Occasionnelle |
| `language.json` | Traductions de l'interface | Rare |
| `minecraft-versions.json` | Versions MC supportées | Toutes les 2-4 semaines |
| `servers.json` | Liste des serveurs featured | Occasionnelle |

### Variables d'Environnement

```bash
# Port du serveur (défaut: 19132)
export PORT=19132

# Activer le mode debug
export DEBUG=true

# Autres options disponibles dans Config.java
```

## 🛠️ Développement

### Ajouter un Nouveau Type de Serveur

1. **Ajoutez les images dans `images.json`** :
```json
{
  "mon_reseau": {
    "lobby": "https://url.com/lobby.png",
    "survival": "https://url.com/survival.png"
  }
}
```

2. **Ajoutez une méthode dans `ImageAssets.java`** :
```java
public String getMonReseauIcon(String serverName) {
    if (monReseau != null && monReseau.has(serverName)) {
        return monReseau.get(serverName).getAsString();
    }
    return getDefaultServerIcon();
}
```

3. **Utilisez dans votre code** :
```java
String icon = BedrockConnect.getConfig().getImageAssets()
    .getMonReseauIcon("lobby");
```

### Compilation et Tests

```bash
# Compilation
mvn clean package

# Tests (si configurés)
mvn test

# Exécution avec logs de debug
java -jar target/BedrockConnect-1.0-SNAPSHOT.jar debug=true
```

## 📊 Versions Supportées

Version actuelle : **Minecraft Bedrock 1.21.80** (Protocol 924)

Voir toutes les versions dans `minecraft-versions.json` ou :
```bash
grep -A 4 "is_latest.*true" serverlist-server/src/main/resources/minecraft-versions.json
```

## 🔧 Dépannage

### Images ne se chargent pas
1. Vérifiez que `images.json` est dans `src/main/resources/`
2. Rebuild : `mvn clean package`
3. Vérifiez les logs au démarrage

### Nouvelle version Minecraft non reconnue
1. Consultez **MAINTENANCE.md**
2. Utilisez le script : `./scripts/add-minecraft-version.sh`
3. Vérifiez que la dépendance Maven est à jour

### Erreurs de compilation
```bash
# Nettoyer et forcer la mise à jour des dépendances
mvn clean
mvn install -U
```

## 📚 Documentation

- **[MAINTENANCE.md](MAINTENANCE.md)** - Guide complet de maintenance
- **[Guide Original](https://github.com/Pugmatt/BedrockConnect)** - Documentation BedrockConnect

## 🤝 Contribution

Les améliorations sont les bienvenues ! Pour contribuer :

1. Fork le projet
2. Créez une branche (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

## 📜 Changelog

### Version 2.0 (Refactoring - 2025)
- ✨ Ajout du système de gestion d'images centralisé (`ImageAssets`)
- ✨ Configuration des versions Minecraft externalisée
- 🛠️ Scripts d'aide à la maintenance
- 📝 Documentation complète de maintenance
- ♻️ Refactoring du code pour meilleure maintenabilité

### Version 1.0
- Version originale BedrockConnect

## 📄 Licence

[Votre licence ici]

## 🙏 Remerciements

- [BedrockConnect Original](https://github.com/Pugmatt/BedrockConnect) par Pugmatt
- [CloudburstMC Protocol](https://github.com/CloudburstMC/Protocol)
- Communauté Minecraft Bedrock

---

**💡 Astuce :** Ajoutez ce projet à vos favoris et exécutez `./scripts/check-minecraft-updates.sh` toutes les 2 semaines !

**📧 Support :** Ouvrez une issue sur GitHub pour toute question.
