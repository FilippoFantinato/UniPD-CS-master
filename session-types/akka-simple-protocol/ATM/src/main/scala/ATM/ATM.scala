package ATM

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors


object ATM {
  sealed trait Message
  final case class Deposit(name: String, money: Int) extends Message
  final case class Transfer(name: String, bookAddress: String, money: Int) extends Message
  final case object Quit extends Message

  def apply(): Behaviors.Receive[Message] = {
    Behaviors.receiveMessage[Message] {
      case Deposit(name, money) =>
        println(money)
        Behaviors.same
      case Transfer(name, bookAddress, money) =>
        println(bookAddress, money)
        Behaviors.same
      case Quit =>
        println("Bye bye")
        Behaviors.same
    }
  }
}
