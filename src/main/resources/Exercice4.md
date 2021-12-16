# Exercice4: Read/Write my JSON

Dans cet exercice, nous voulons réaliser une mini-lib de sérialisation/désérialisation en JSON.
Voici à quoi ressemble du JSON:

```
{
  "field1": 1,
  "field2": "hello",
  "field3": true,
  "field4": [1, 2, 3, 4],
  "field5": {
    "subField1": 2,
    "subField2": false
  }
}
```

Et voici comment nous allons modéliser du JSON:

```scala
sealed trait JsValue

case object JsNull extends JsValue

sealed abstract class JsBoolean(val value: Boolean) extends JsValue
case object JsTrue extends JsBoolean(true)
case object JsFalse extends JsBoolean(false)

case class JsNumber(value: BigDecimal) extends JsValue

case class JsString(value: String) extends JsValue

case class JsArray(value: IndexedSeq[JsValue] = Array[JsValue]()) extends JsValue
// IndexedSeq est une super classe de List et Array (pour représenter une séquence indexée)

case class JsObject(private val underlying: Map[String, JsValue]) extends JsValue
```

Pour la suite nous pouvons importer `play.api.libs.json._` dans lequel toutes ces classes sont définies.
Cela nous vient de la librairie `play-json` dont la documentation est disponible [ici](https://www.playframework.com/documentation/2.8.x/ScalaJson) et l'api [ici](https://www.playframework.com/documentation/2.8.x/api/scala/play/api/libs/json/index.html).


## Question 1:

Nous voulons maintenant gérer la conversion d'une valeur en `JsValue`.
Définissez une typeclass `Writes`, proposant la méthode `writes` qui prendrait une valeur et la transformerait en `JsValue` (Json).

## Question 2:

Définissez des instances de `Writes` pour les types `Int`, `String`, `Boolean`.

## Question 3:

Définissez (sans faire appel explicitement à `toString`) une implémentation de `Writes` pour la classe suivante:

```scala
import java.time.LocalDate

case class PersonalInfo(birthDate: LocalDate, weigth: Int, height: Int)

case class Person(id: Int, name: String, isFemale: Boolean, info: PersonalInfo)
```

## Question 4:

Quel est le résultat de: 

```scala
Writes[Person].writes(Person(1, "John Doe", false, PersonalInfo(LocalDate.now(), 89, 186)))
```

## Question 5:

Définissez une instance de `Writes` pour le type suivant:

```scala
sealed trait Employee

case class Manager(id: Int, person: Person) extends Employee

case class Worker(id: Int, managerId: Int, person: Person) extends Employee
```

Indice: Ajouter un Json un champ discriminant qu'on nommera `__type`.


## Question 6:

Quel est le résultat de:

```scala
Write[Employee].writes(Manager(1, Person(1, "John Doe", false, PersonalInfo(LocalDate.now(), 89, 186))))
```

## Question 7:

La librairie `play-json` que nous avons utilisé qui nous fournissait le type `JsValue` définit aussi la typeclass `Writes` comme à la question 1.
Cependant il est fastidieux de construire les instances pour chaque nouveau type en fonction des instances prédéfinies auparavant. Pour cela,
`play-json` définit une façon de le faire automatiquement pour nous comme suit:

```scala
import play.api.libs.json.{Json, Writes}

case class Foo(a: Int, b: String, c: Boolean)

object Foo {
  implicit val fooWrites: Writes[Foo] = Json.writes[Foo]
}
```

L'expression `Json.writes[Foo]` va à partir des instances de `Writes` définies pour `Int`, `String` et `Boolean` pour générer automatiquement
l'instance de `Writes` pour la classe `Foo`. La composition de `typeclass` permet d'automatiser le process de génération d'instances. On parle
souvent de `dérivation automatique d'instance`. Cette dérivation peut aussi être faite pour un `sealed trait`:

```scala
sealed trait Alt

case class Sub1(a: String) extends Alt
object Sub1 {
  implicit val writes = Json.writes[Sub1]
}

case class Sub2(b: Int) extends Alt 
object Sub2 {
  implicit val writes = Json.writes[Sub2]
}

object Alt {
  implicit val roleWrites: Writes[Alt] = Json.writes[Alt]
}
```

La documentation de `play-json` sur la génération automatique peut être consultée [ici](https://www.playframework.com/documentation/2.8.x/ScalaJsonAutomated).


Générez les instances de `Writes` (de `play-json`) pour les `Person` et `Employee` (dans les objets companion respectifs) en utilisant la génération automatique de `play-json`.


## Question 8:

Il se trouve que `play-json` propose aussi la typeclass `Reads` pour décoder du Json en une valeur de type `A` (opération inverse de `Writes`):

```scala
trait Reads[A] {

  /**
   * Convert the JsValue into a A
   */
  def reads(json: JsValue): JsResult[A]
}

sealed trait JsResult[+A]
case class JsSuccess[T](value: T, path: JsPath) extends JsResult[T]
case class JsError(errors: Seq[(JsPath, Seq[JsonValidationError])]) extends JsResult[Nothing]

// JsPath: chemin du champ dans le json (ex: p.info.weight)
```

De même il est possible de générer automatiquement les instances de Reads comme suit:

```scala
import play.api.libs.json.{Json, Reads}

case class Foo(a: Int, b: String, c: Boolean)

object Foo {
  implicit val fooWrites: Reads[Foo] = Json.reads[Foo]
}
```

Générez les instances de `Reads` (de `play-json`) pour les `Person` et `Employee` (dans les objets companion respectifs) en utilisant la génération automatique de `play-json`.


## Question 9:

Scala propose le mot-clé `implicitly` qui permet de récupérer une valeur implicite définir pour un type:

```scala
val w: Writes[Person] = implicitly[Writes[Person]]
val r: Reads[Person] = implicitly[Reads[Person]]
```

Quel est le résultat de l'expression suivante ?

```scala
val person = Person(1, "John Doe", false, PersonalInfo(LocalDate.now(), 89, 186))

r.reads(w.writes(person))
```
