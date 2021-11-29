package controllers

import play.api.mvc._
import models.Ticket
import play.api.libs.json.{JsObject, JsValue}

import javax.inject._
import scala.util.{Failure, Success}

@Singleton
class TicketRequestController @Inject()(cc: ControllerComponents, val configuration: play.api.Configuration)
  extends AbstractController(cc) {

  def displayAllTicketsPage: Action[AnyContent] = Action {
    Ok(views.html.allTickets())
  }

  def getAllTickets: Action[AnyContent] = Action {
    Ok(views.html.allTickets())
  }

  def displayTicketInfoByIdPage: Action[AnyContent] = Action {
    Ok(views.html.ticketById())
  }

  def getTicketInfoById(ticketId: Int): Action[AnyContent] = Action {
    Ticket.getTicketInfoById(ticketId) match {
      case Success(value) =>
        Ok(value)
      case Failure(exception) =>
        BadRequest(exception.getMessage)
    }
  }

}
