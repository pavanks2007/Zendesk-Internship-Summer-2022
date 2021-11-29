package controllers

import play.api.mvc._
import models.Ticket
import play.api.libs.json._

import javax.inject._
import scala.collection.immutable.ListMap
import scala.util.{Failure, Success}


@Singleton
class TicketRequestController @Inject()(cc: ControllerComponents, val configuration: play.api.Configuration)
  extends AbstractController(cc) {

  def displayAllTicketsPage: Action[AnyContent] = Action {
    Ok(views.html.allTickets())
  }

  def getAllTickets(pageNumber: Int, pageLimit: Int = 25): Action[AnyContent] = Action {
    Ticket.getTicketsWithPageLimit(pageNumber, pageLimit) match {
      case Success(value) =>
        Ok(Json.toJson(value("tickets").as[Seq[JsValue]].map(extractRequiredInfoFromTicket)))
      case Failure(exception) =>
        BadRequest(exception.getMessage)
    }
  }

  def displayTicketInfoByIdPage: Action[AnyContent] = Action {
    Ok(views.html.ticketById())
  }

  def getTicketInfoById(ticketId: Int): Action[AnyContent] = Action {
    Ticket.getTicketInfoById(ticketId) match {
      case Success(value) =>
        Ok(extractRequiredInfoFromTicket(value("ticket")))
      case Failure(exception) =>
        BadRequest(exception.getMessage)
    }
  }

  def extractRequiredInfoFromTicket(ticket: JsValue): JsValue = {
    Json.toJson(
      ListMap(
        "id" -> (ticket \ "id").get.toString(),
        "type" -> removeUnnecessaryCharacters((ticket \ "type").get.toString()),
        "subject" -> removeUnnecessaryCharacters((ticket \ "subject").get.toString()),
        "description" -> removeUnnecessaryCharacters((ticket \ "description").get.toString()),
        "requester_id" -> (ticket \ "requester_id").get.toString(),
        "submitter_id" -> (ticket \ "submitter_id").get.toString(),
        "assignee_id" -> (ticket \ "assignee_id").get.toString(),
        "group_id" -> (ticket \ "group_id").get.toString()
      )
    )
  }

  def removeUnnecessaryCharacters(str: String): String = str.replaceAll("^.|.$", "")

}
