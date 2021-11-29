package controllers

import play.api.mvc._

import javax.inject._

@Singleton
class TicketRequestController @Inject()(cc: ControllerComponents, val configuration: play.api.Configuration)
  extends AbstractController(cc) {

  def getAllTickets: Action[AnyContent] = Action {
    Ok(views.html.allTickets())
  }

  def postAllTickets: Action[AnyContent] = Action {
    Ok(views.html.allTickets())
  }

  def getTicketInfoById: Action[AnyContent] = Action {
    Ok(views.html.ticketById())
  }

  def postTicketInfoById: Action[AnyContent] = Action {
    Ok(views.html.ticketById())
  }

}
