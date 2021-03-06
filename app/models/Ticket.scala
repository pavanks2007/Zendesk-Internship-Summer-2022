package models

import com.typesafe.config.ConfigFactory
import play.api.libs.json.{JsError, JsObject, JsSuccess, JsValue, Json}
import scalaj.http.Http

import scala.collection.immutable.ListMap
import scala.util.{Failure, Success, Try}

object Ticket {

  private val subDomain: String = ConfigFactory.load().getString("zendesk.subDomain")

  private val fullToken = ConfigFactory.load().getString("zendesk.fullToken")

  def getTicketsWithPageLimit(pageNumber: Int, pageLimit: Int): Try[JsValue] = {
    getRequest(
      url = s"https://$subDomain/api/v2/tickets.json?per_page=$pageLimit&page=$pageNumber",
      fullToken
    )
  }

  def getTicketInfoById(ticketId: Int): Try[JsValue] = {
    getRequest(
      url = s"https://$subDomain/api/v2/tickets/$ticketId.json",
      fullToken
    )
  }

  def getRequest(url: String, token: String): Try[JsValue] = {
    val request = Http(url).header("Authorization", s"Bearer $token")
    val response = request.asString
    val json = Json.parse(response.body)
    handleResponseError(json)
  }

  def handleResponseError(response: JsValue): Try[JsValue] = {
    (response \ "error").validate[JsObject] match {
      case JsSuccess(value, _) =>
        val errorMsg: String = s"Error: ${value.toString}"
        Failure(new Exception(errorMsg))

      case JsError(_) =>
        (response \ "error").validate[String] match {
          case JsSuccess(value, _) =>
            val errorMsg: String = s"Error: $value."
            Failure(new Exception(errorMsg))
          case JsError(_) =>
            Success(response)
        }
    }
  }

  def extractRequiredInfo(ticket: JsValue): JsValue = {
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
