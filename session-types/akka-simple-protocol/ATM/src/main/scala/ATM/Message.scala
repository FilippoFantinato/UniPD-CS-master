package ATM

import akka.actor.typed.ActorRef

final case class Customer(firstName: String)

final case class SubscribedMessage(subscriberId: Long, from: ActorRef[ATMOp])

sealed trait ATMOp
case object Quit extends ATMOp
case class Deposit(money: Int) extends ATMOp {
  def valid: Boolean = money > 0
}
case class Transfer(bookAddress: String, money: Int) extends ATMOp {
  def valid: Boolean = money > 0
}
