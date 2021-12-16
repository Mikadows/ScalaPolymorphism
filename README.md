# TD 3: Types et polymorphisme

## Pré-requis

Il est indispensable d'avoir installé en local:

- la version 2.13 du compilateur Scala, [ici](https://scala-lang.org/download/)

- le gestionnaire de build `sbt`, [voir ici](https://www.scala-sbt.org/download.html). En installant `sbt`, le compilateur sera installé aussi.

## Enoncé et déroulement

Pour chaque exercice, vous disposez d'un énoncé dans le répertoire `src/main/resources` et d'un fichier Scala dans lequel il vous sera possible d'écrire le code résolvant les exercices (dans le répertoire `src/main/scala`).

Veuiller vous référer à la liste des exercices:

- Exercice 1: [Voir l'énoncé ici](./src/main/resources/Exercice1)

- Exercice 2: [Voir l'énoncé ici](./src/main/resources/Exercice2)

- Exercice 3: [Voir l'énoncé ici](./src/main/resources/Exercice3)

- Exercice 4: [Voir l'énoncé ici](./src/main/resources/Exercice4)

- Exercice 5: [Voir l'énoncé ici](./src/main/resources/Exercice5)

- Exercice 6: [Voir l'énoncé ici](./src/main/resources/Exercice6)

Le fichier `src/main/scala/Main.scala` est la classe principale du projet. Il est possible de le lancer avec la commande `sbt run`.

## Guide de survie

Ce projet est un projet Scala. Il est géré par `sbt`, le build tool Scala. Sa documentation est disponible [ici](https://www.scala-sbt.org/1.x/docs/).

Nous allons lister ici une liste de commandes utiles avec `sbt`:

- `sbt`: cette commande lance un invite de commande interactif

- `run` (ou `sbt run` hors de l'invite de commande): lance la classe `Main` du projet `sbt`

- `compile` (ou `sbt compile` hors de l'invite de commande): lance la compilation de l'ensemble du projet `sbt` (compile toutes les classes)

- `console` (`sbt console` hors de l'invite de commande): lance un REPL interactif Scala. Les dépendances du projet `sbt` seront disponibles et pourront être importés.
