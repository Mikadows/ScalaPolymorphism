# Exercice 5: File system existentiel

Dans cet exercice, nous voulons modéliser une librairie de gestion de fichiers.

Avec notre librairie, noous souhaitons pouvoir avoir:

- un système de fichier natif: à travers lequel on peut lire et écrire n'importe quel fichier présent sur la machine. La racine de ce système de fichier est la racine de la machine.

- un système de fichier virtuel: monté à partir d'un répertoire (qui représentera la racine). Pour des contraintes de sécurité, il restreint la visibilité à un seul dossier.

Voici la modélsation que nous aurons pour un fichier lu par un système de fichier:

```scala
import java.nio.file.Path

sealed trait File {
  val path: Path
  def content: String
}
```

`File` est une interface abstraite, chaque système de fichier de notre librairie en retournera son propre type de fichier. Pour un utilisateur ayant reçu un fichier, les seules opérations accessibles sur ce dernier sera d'obtenir son chemin et de lire son contenu (sous forme de `String`). 

Pour chaque système de fichier nous voulons que les méthodes suivantes soient disponibles:

- une méthode pour créer un fichier dans le `Path` passé en paramètre. Pour le système de fichier virtuel, le chemin sera crée

- une méthode prenant un fichier en paramètre et de la donnée (sous forme de `String`) et qui permettra d'écrire dans celui-ci

- une méthode prenant un fichier et qui permettra de fermer celui-ci

Nous ne voulons pas qu'il soit possible d'utiliser un fichier virtuel avec un système de fichier natif et vice-versa.

## Question 1:

Proposer une interface pour notre système de fichier que nous appellerons `FileSystem`. Le fichier retourné par ce système de fichier doit être un sous-type de `File`.
L'instance de `File` utilisé par le système de fichier doit être inconnu pour l'utilisateur de la lib.


## Question 2:

Définissez une implémentation de `FileSystem` pour le natif que nous appellerons `NativeFileSystem`. Pour l'implémentation des méthodes (écriture et fermeture) nous nous contenterons d'afficher dans la console.


## Question 3:

Définissez une implémentation de `FileSystem` virtuel que nous appellerons `VirtualFileSystem`. Idem, pour l'implémentation des méthodes (écriture et fermeture) nous nous contenterons d'afficher dans la console.


## Question 4:

Définissez dans le companion objet de `FileSystem` une méthode build qui prend en parmètre les valeurs `VIRTUAL` ou `NATIVE` et qui retourne l'un ou l'autre des système de fichier.
