package de.kreditech.oktopus.prophet

import akka.http.scaladsl.model.{ContentTypes, StatusCodes}
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

  "StatusService" when {
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



}
