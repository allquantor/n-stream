package de.kreditech.oktopus.prophet

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
import akka.http.scaladsl.server.{MalformedRequestContentRejection, UnsupportedRequestContentTypeRejection}
import de.kreditech.oktopus.prophet.ProtocolTypes.ResourceResponse

class CalculationServiceTest
  extends ServiceTestBase
    with CalculationService
    with Protocol {

  val requestJson =
    """{
    "jobCount" : 10,
    "rows" : 5,
    "columns" : 100}""".stripMargin

  "Calculation Service " when {
    "POST /proposal" should {
      "return a proposal" in {
        Post("/proposal").withEntity(ContentTypes.`application/json`,
          requestJson) ~> standardRoute ~> check {
          status should be(StatusCodes.OK)
          responseAs[ResourceResponse].cpuAmount should be(1)
        }
      }
    }
  }

  "Calculation Service " when {
    "POST /proposal" should {
      "rejectf wrong content type" in {
        Post("/proposal").withEntity(ContentTypes.`text/csv(UTF-8)`,
          requestJson) ~> standardRoute ~> check {
          rejection shouldEqual  UnsupportedRequestContentTypeRejection(Set(ContentTypes.`application/json`))
        }
      }
    }
  }

  "Calculation Service " when {
    "POST /proposal" should {
      "reject a request when json not valid" in {
        Post("/proposal").withEntity(ContentTypes.`application/json`,
          requestJson + "BREAK THE JSON!") ~> standardRoute ~> check {
          rejection shouldBe a [MalformedRequestContentRejection]
        }
      }
    }
  }




}
