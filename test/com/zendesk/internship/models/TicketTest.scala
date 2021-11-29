package com.zendesk.internship.models

import com.typesafe.config.ConfigFactory
import models.Ticket
import org.scalatest.FunSuite

class TicketTest extends FunSuite {

  private val subDomain: String = ConfigFactory.load().getString("zendesk.subDomain")

  private val rightFullToken = ConfigFactory.load().getString("zendesk.fullToken")

  test("Should succeed when the token is correct"){
    val url = s"https://$subDomain/api/v2/tickets.json"
    assert(Ticket.getRequest(url,rightFullToken).isSuccess)
  }

  test("Should fail when the token is not correct"){
    val url = s"https://$subDomain/api/v2/tickets.json"
    val dummyToken = "dummyToken"
    assert(Ticket.getRequest(url,dummyToken).isFailure)
  }

  test("Should receive an response for the pageNumber = 2 and pageLimit = 10"){
    assert(Ticket.getTicketsWithPageLimit(2, 10).isSuccess)
  }

  test("Should fail for the pageNumber = -1 and pageLimit = 10"){
    assert(Ticket.getTicketsWithPageLimit(-1, 10).isFailure)
  }

  test("Should fail for the pageNumber = 2 and pageLimit = 0"){
    assert(Ticket.getTicketsWithPageLimit(2, 0).isFailure)
  }

  test("Should receive an response for the ticketId = 2"){
    assert(Ticket.getTicketInfoById(2).isSuccess)
  }

  test("Should fail for the ticketId = -1"){
    assert(Ticket.getTicketInfoById(-1).isFailure)
  }

}
