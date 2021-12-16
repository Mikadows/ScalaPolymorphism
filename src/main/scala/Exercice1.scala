object Exercice1 {

  trait Eq[A] {
    def eq(x: A, y: A): Boolean
  }

  object Eq {
    // ... val eqString
    // ... val eqInt
    // ... val eqPair

    // ... val eqLocalDate
    // ... val eqLocalDateTime
  }

  def member[A](ls: List[A], y: A)(implicit eqA: Eq[A]): Boolean = ls match {
    case Nil => false
    case head :: tail => eqA.eq(head, y) || member(tail, y)
  }

  // def diff[A](list1: List[A], list2: List[A]) ... : List[A] = ???

  // def compteVoyelles(...): Int = ???

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

  object Order {
    // ... val ordString
    // ... val ordInt
    // ... val ordPair
  
    // ... val ordLocalDate
    // ... val ordLocalDateTime
  }

  def search[A](x: A, list: List[A])(implicit ordA: Order[A]): Boolean = 
    list match {
      case Nil => false
      case head :: tail => (ordA.eq(x, head) || 
        (ordA.lt(x, head) && search(x, tail)))
    }
 
  // def isInRange(value: A, range: (A, A)) ... : Boolean = ???

}
