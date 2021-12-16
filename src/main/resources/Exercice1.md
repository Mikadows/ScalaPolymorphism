# Exercice 1: Egalité

Dans cet exercice, nous voulons comparer deux valeurs d'un type.
Nous allons commencer par tester l'égalité entre deux valeurs.
Contrairement à la méthode `equals` de Java, nous voulons:
  - ne pas nous reposer sur l'héritage
  - avoir une erreur de compilation lorsqu'on compare 2 valeurs de types différents

Pour cela, reprenons la définition de la typeclass `Eq` définie dans le cours: 

```scala
trait Eq[A] {
  def eq(x: A, y: A): Boolean
}
```

NB: Les réponses seront à implémenter dans le fichier `../scala/Exercice1.scala`.

## Question 1: 

Implémentons les instances de la typeclass `Eq` pour les types `String`,
`Ìnt` et `(A, B)` (pour tout `A` et `B`).

```scala
// ... val eqString
// ... val eqInt
// ... val eqPair
```

## Question 2:

A partir de l'implémentation de la fonction `member` ci-dessous, définissons
la fonction `diff` qui permettra qui prend 2 listes en paramètres et retourne
la liste des éléments de la 2ème liste qui ne sont pas contenus dans la 1ère.

```scala
def member[A](ls: List[A], y: A)(implicit eqA: Eq[A]): Boolean = ls match {
  case Nil => false
  case head :: tail => eqA.eq(head, y) || member(tail, y)
}

// def diff[A](list1: List[A], list2: List[A]) ... : List[A] = ???
```

## Question 3:

Quel est le résultat des expressions suivantes (testez dans le `Main.scala`):

```scala
member(List(1, 2, 3, 4, 5), 1)
member(List(1, 2, 3, 4, 5), 7)
member(List("a", "b", "c", "d", "e"), "a")
member(List("a", "b", "c", "d", "e"), "A")
diff(List(1, 2, 3, 4, 5), List(1, 2, 3, 4, 5))
diff(List("a", "b", "c", "d", "e"), List("a", "b", "c", "d", "e"))
diff(List(1, 2, 3, 4, 5), List(3, 4, 5, 6, 7))
diff(List("a", "b", "c", "d", "e"), List("c", "d", "e", "f", "g"))
```

## Question 4:

Ecrire une fonction qui va compter le nombre total de voyelles dans une `String`.
Vous pouvez utiliser la fonction `toList` dispnible sur `String`.

```scala
// def compteVoyelles(...): Int = ???
```

## Question 5:

Implémenter une instance de la typeclass `Eq` pour les types `java.time.LocalDate``
et `java.time.LocalDateTime`.
Voir la javadoc pour [LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html) et la javadoc pour [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html).

```scala
  // ... val eqLocalDate
  // ... val eqLocalDateTime
```

## Question 6:

Nous souhaitons maintenant pouvoir effectuer la comparaison (inférieur à, supérieur à, ...)
entre deux valeurs d'un même type. Reprennons pour la typeclass `Ord` définie précédemment.

```scala
trait Order[A] extends Eq[A] {
  // lower than: x plus petit que y
  def lt(x: A, y: A): Boolean

  // lower or equal than: x plus petit ou égal à y
  def ltEq(x: A, y: A): Boolean

  // greater than: x plus grand que y
  def gt(x: A, y: A): Boolean

  // greater than or equal: x plus grand ou égal à y
  def gtEq(x: A, y: A): Boolean
}
```

Proposer une implémentation par défaut poour les méthodes `gt` et `gtEq`.

## Question 7:

Implémentons les instances de la typeclass `Ord` pour les types `String`,
`Ìnt` et `(A, B)` (pour tout `A` et `B`).

```scala
// ... val ordString
// ... val ordInt
// ... val ordPair
```

## Question 8:

Compte-tenu de la fonction `search` définie comme suit:

```scala
def search[A](x: A, list: List[A])(implicit ordA: Order[A]): Boolean = 
  list match {
    case Nil => false
    case head :: tail => (ordA.eq(x, head) || 
      (ordA.lt(x, tail) && search(x, tail)))
  }
```

Quelle est le résultat des expressions suivantes  (testez dans le `Main.scala`) ?

```scala
search(5, List(1, 2, 3, 4, 5))
search(6, List(1, 2, 3, 4, 5))
search("d", List("a", "b", "c", "d", "e"))
search("f", List("a", "b", "c", "d", "e"))
```

## Question 9:

Implémenter une instance de la typeclass `Ord` pour les types `java.time.LocalDate`
et `java.time.LocalDateTime`.
Voir la javadoc pour [LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
et pour [LocalDateTime](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html).

```scala
// ... val ordLocalDate
// ... val ordLocalDateTime
```

## Question 10:

Implémenter la fonction `isInRange` qui permet de vérifier qu'une valeur prise en paramètre est
comprise entre les deux valeurs de la paire prise en paramètre.

```scala
// def isInRange(value: A, range: (A, A)) ... : Boolean = ???
```

Et vérifier la valeur des expressions suivantes:

```scala
// val localDate = LocalDate.now()
// val localDateTime = LocalDateTime.now()
// isInRange(localDate, (localDate.minusDays(1), localDate.plusDays(1)))
// isInRange(localDate, (localDate.plusDays(1), localDate.plusDays(2)))
// isInRange(localDateTime, (localDateTime.minusDays(1), localDateTime.plusDays(1)))
// isInRange(localDateTime, (localDateTime.plusDays(1), localDateTime.plusDays(2)))
```
