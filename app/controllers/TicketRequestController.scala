package controllers

import play.api.mvc._
import models.Ticket

import javax.inject._
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
        Ok(value)
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
        Ok(value)
      case Failure(exception) =>
        BadRequest(exception.getMessage)
    }
  }

}
