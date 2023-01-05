# Utilisation du projet :
Pour réaliser le chiffrage d'un fichier, il faut lancer le programme avec la commande suivante :
```java -jar artifacts/CBCDechiffrage_jar/CBCDechiffrage.jar <clé> <vecteur> <fichier à déchiffrer>```

Le premier argument correspond à la clé permettant de savoir comment déchiffrer le fichier. Le second argument correspond au vecteur d'initialisation. Le troisième argument correspond au fichier à déchiffrer.

### Exemple :
```cmd
java -jar artifacts/CBCDechiffrage_jar/CBCDechiffrage.jar 4 1010 test.txt.encrypted
```

### Résultat:
Le programme va générer un fichier déchiffré là où se trouve le fichier à déchiffrer avec l'extension .decrypted.
Ce fichier contient le texte déchiffré.