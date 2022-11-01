package ATM

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object Main extends App {
  val actorSystem: ActorSystem[Customer.Message] = ActorSystem(
    Customer(),
    "ATMSystem"
  )
}
