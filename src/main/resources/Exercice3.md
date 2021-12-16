# Exercice 3: Combinons

Dans cet exercice, nous voulons refactor la solution que nous avons trouvé à un problème spécifique.
Nous souhaitons modéliser un système de panier d'achat pour une plateforme e-commerce. Dans notre 
programme, nous souohaitons être en mesure de rajouter des articles dans le panier d'un acheteur.
Il est possible pour un utilisateur d'avoir plusieurs paniers et il est possible pour celui-ci de
les fusionner afin d'acheter les articles en une seule commande.

Considérons le type `ShoppingCart` ci-après:

```scala
type ProductId = String
type Quantity = Int

case class ShoppingCart(items: Map[ProductId, Quantity])
```

Supposons que nous devions implémenter une fonction qui fusionne une liste de `ShoppingCart` en une
seule instance qui contient tous les articles de tous les paniers avec leurs quantités additionnées.

Une façon de faire est de mettre en oeuvre un repli sur la liste des paniers d'achat:

```scala
def merge(list: List[ShoppingCart]): ShoppingCart = {
    val emptyCart = ShoppingCart(Map())
    list.fold(emptyCart)(combineTwoShoppingCarts)
}
```

Il nous manque maintenant la fonction `combineTwoShoppingCarts` pour combiner deux paniers:

```scala
def combineTwoShoppingCarts(sc1: ShoppingCart, sc2: ShoppingCart): ShoppingCart = {
    ShoppingCart(combineItems(sc1.items, sc2.items))
}
```

Nous déléguons le travail à la fonction `combineItems` qui prennent deux listes d'id de produit
avec leur quantité et qui les fusionne en une seule:

```scala
def combineItems(
    m1: Map[ProductId, Quantity], m2: Map[ProductId, Quantity]
): Map[ProductId, Quantity] =
  (m1.keys ++ m2.keys)
    .toList
    .distinct
    .map(id => (id, m1.getOrElse(id, 0) + m2.getOrElse(id, 0)))
    .toMap
```

A noter que:
- La méthode `++` sur `Map` permet de concaténer deux `Map`
- `toList` transforme `Map[ProductId, Quantity]` en `List[(ProductId, Quantity)]`
- à l'inverse `toMap` transforme `List[(ProductId, Quantity)]` en `Map[ProductId, Quantity]`
- la fonction `getOrElse` sur `Map` permet d'obtenir la valeur de la clé passé en  1er argument ou le 2nd argument si cette clé n'a pas d'entrée dans la `Map`
- La méthode `distinct` permet de ne garder que les éléments uniques

Cela n'est pas la solution la plus efficiente mais elle a le mérite de marcher:

```scala
scala> val carts = List(
    ShoppingCart(Map("p0001" -> 1, "p0002" -> 3)),
    ShoppingCart(Map("p0001" -> 4, "p0004" -> 6)))

scala> merge(carts)
// ShoppingCart(Map(p0001 -> 5, p0002 -> 3, p0004 -> 6))
```

Pour définir la solution, nous pouvons d'analyser les traitements récurrents que nous 
pouvons abstraire. En l'occurrence, nous en avons deux qui apparaissent plusieurs fois:

1. Une valeur qui représente une valeur neutre ou vide: `m1.getOrElse(id, 0)`, `val emptyCart = ShoppingCart(Map())`
2. Une fonction qui combine deux éléments: `combineTwoShoppingCarts`, `combineItems`, `m1.getOrElse(id, 0) + m2.getOrElse(id, 0)`

La valeur vide et la fonction combine sont utilisés avec trois types différents:

- `Quantity` (qui est un alias de `Int`)
  - Valeur vide: `0`
  - Fonction qui combine: `+`
- `Map[ProductId, Quantity]`
  - Valeur vide: `Map()`
  - Fonction qui combine: `combineItems`
- `ShoppingCart`
  - Valeur vide: `ShoppingCart(Map())`
  - Fonction qui combine: `combineTwoShoppingCarts`

Maintenant essayons de refactor le code précédent.

## Question 1:

Définissez un typeclass `Combinable` qui va comporter les deux concepts de valeur vide (appelé `empty`) et de fonction qui combine (appelé `combine`).


## Question 2:

Définissez des instances de `Combinable` pour les types `Int` et `Map[A, B]`.


## Question 3:

Rajoutons la fonction suivante (qui nous permet d'avoir du sucre syntaxique) dans l'objet companion de `Combinable`:

```scala
object Combinable {
  // ...
  def apply[A](implicit comb: Combinable[A]): Combinable[A] = comb
}
```

Quel est le résultat des expressions suivantes:

```scala
Combinable[Int].empty
Combinable[Map[String, Int]].empty
Combinable[Map[Int, Map[Int, Map[String, Int]]]].empty
```


## Question 4:

Définissez une instance de `Combinable` pour le type `ShoppingCart` (dans l'objet companion de `ShoppingCart`).


## Question 5:

Implémenter la fonction `combineAll` qui prend une liste et combine les éléments de la liste.
Voici sa signature:

```scala
trait Combinable[A] {
  // ...
  def combineAll(list: List[A]) = ???
}
```

Indice: Vous pouvez utiliser la méthode `fold` sur `List`.


## Question 6:

Quel est le résultat de l'expression suivante:

```scala
scala> Combinable[ShoppingCart].combineAll(carts)
```
