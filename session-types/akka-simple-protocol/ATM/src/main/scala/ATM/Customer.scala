package ATM

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Customer{
  sealed trait Message
  final case class Approved(newBalance: Int) extends Message
  final case class Error(err: String) extends Message

  def apply(): Behavior[Message] = Behaviors.setup { context =>
    val atm: ActorRef[ATM.Message] = context.spawn(ATM(), "atm")

    atm ! ATM.Deposit("Filippo", 100)

    Behaviors.receiveMessage[Message] {
      case Approved(newBalance) =>
        println(newBalance)
        Behaviors.same
      case Error(err) =>
        println(err)
        Behaviors.same
    }
  }
}
