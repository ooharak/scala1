package auction

import scala.actors.Actor
import java.util.Date


abstract class AuctionMessage
case class Offer(bid:Int, client: Actor) extends AuctionMessage

abstract class AuctionReply
case class Status(asked: Int, expire: Date) extends AuctionReply
case object BestOffer extends AuctionReply


class Auction {

}