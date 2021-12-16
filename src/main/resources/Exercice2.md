# Exercice 2: Show classes


Nous voulons définir une `typeclass` avec une méthode `show` pour avoir la 
représentation en `String` d'un type donné. Cela équivaudrait à la méthode 
`.toString` de `Java`/`Scala`.


## Question 1:

Définissez la typeclass `Show`:

```scala
// ... Show ...
```


## Question 2:

Définissez des instances de `Show` pour les types `Int`, `String` et `(A, B)`:

```scala
// ... val stringShow ...
// ... val intShow ...
// ... val pairShow ...
```

## Question 3:

Rajoutez dans l'object companion de `Show` la méthode suivante:

```scala
def show[A](a: A)(implicit sh: Show[A]) = sh.show(a)
```

Quel est le résultat expressions suivantes ?

```scala
Show.show(2)

Show.show("Hello world!")

Show.show((2, "Wazaa"))

Show.show((true, 'c'))
```

## Question 4:

Définissons le classe suivante:

```scala
case class Person(id: Int, name: String, isManager: Boolean)
```

Définissez une instance de Show pour `Person`.
