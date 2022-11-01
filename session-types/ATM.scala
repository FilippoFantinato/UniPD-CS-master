package lchannels.examples.ATM

import scala.concurrent.duration.{Duration, DurationInt}
import lchannels._

case class Welcome(name: String) (val cont: In[ATMOp])

sealed trait ATMOp
case class Deposit(money: Integer)(val cont: Out[Answer]) extends ATMOp
case class Transfer(bankAddress: String, money: Integer)(val cont: Out[Answer]) extends ATMOp
case object  Quit extends ATMOp

sealed trait Answer
case class Approved(newBalance: Integer) (val cont: Out[ATMOp]) extends Answer
case class Error(err: String) (val cont: Out[ATMOp]) extends Answer


object ATM {
  def apply(c: In[Welcome], bal: Integer = 400)
           (implicit timeout: Duration) : Unit = {
    c ? { customer =>
      val name = customer.name

      println("Hi " + name)

      def loop(op: In[ATMOp], bal: Int): Unit = {
        op ? {
          case m@Deposit(money) =>
            val (answer, newBalance) =
              if (money > 0) {
                val newBalance = bal + money
                println("Depositing: " + money)
                (Approved(newBalance)_, newBalance)
              } else {
                (Error("The amount of money is negative or equal to 0")_, bal)
              }
            val c2in = m.cont !! answer
            loop(c2in, newBalance)

          case m@Transfer(bankAddress, money) =>
            val (answer, newBalance) =
              if (bal >= money && money > 0) {
                val newBalance = bal - money
                println("Transferring from " + name + " to " + bankAddress + " " + money)
                (Approved(newBalance) _, newBalance)
              } else {
                (Error("You have too little money") _, bal)
              }
            val c2in = m.cont !! answer
            loop(c2in, newBalance)

          case Quit => println("Bye bye " + name)
        }
      }

      loop(customer.cont, bal)
    }
  }
}

object ClientDeposit {
  def apply(c: Out[Welcome])
           (implicit timeout: Duration): Any = {
    val dep = c !! Welcome("Filippo")_ !! Deposit(1000)_
    (dep ? {
      case m @ Approved(newBalance) => println("My new balance is " + newBalance); m.cont 
      case m @ Error(e) => println("Error: " + e); m.cont
    }) ! Quit
  }
}

object ClientTransfer {
  def apply(c: Out[Welcome])
           (implicit timeout: Duration): Any = {
    val trans = c !! Welcome("Filippo")_ !! Transfer("Alice", 1000)_
    (trans ? {
      case m @ Approved(newBalance) => println("My new balance is " + newBalance); m.cont 
      case m @ Error(e) => println("Error: " + e); m.cont
    }) ! Quit
  }
}

object LocalDeposit extends App {
  // Helper method to ease external invocation
  def run(): Unit = main(Array())

  import lchannels.LocalChannel.parallel
  import scala.concurrent.Await
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  implicit val timeout: FiniteDuration = 10.seconds

  println("[*] Spawning local ATM and client 1...")
  val (c1, s1) = parallel[Welcome, Unit, Unit](
    ATM(_), ClientDeposit(_)
  )

  Await.result(s1, 10.seconds) // Wait for ATM termination
}


object LocalTransfer extends App {
  // Helper method to ease external invocation
  def run(): Unit = main(Array())

  import lchannels.LocalChannel.parallel
  import scala.concurrent.Await
  import scala.concurrent.ExecutionContext.Implicits.global
  import scala.concurrent.duration._

  implicit val timeout: FiniteDuration = 10.seconds

  println("[*] Spawning local ATM and client 1...")
  val (c1, s1) = parallel[Welcome, Unit, Unit](
    ATM(_), ClientTransfer(_)
  )

  Await.result(s1, 10.seconds) // Wait for ATM termination
}

