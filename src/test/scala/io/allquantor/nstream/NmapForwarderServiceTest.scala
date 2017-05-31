package io.allquantor.nstream

import io.allquantor.nstream.protocol.Protocol

class NmapForwarderServiceTest
  extends ServiceTestBase
    with Protocol {


  //  val request = ScanRequest("79.200.204.235","foo@bar.com")
  //  val requestJson = request.toJson.toString(CompactPrinter)
  //
  //
  //  "Forwarder Server Service " when {
  //    "POST /scan" should {
  //      "return a proposal" in {
  //        Post("/scan").withEntity(ContentTypes.`application/json`,
  //          requestJson) ~> standardRoute ~> check {
  //         // status should be(StatusCodes.OK)
  //          //responseAs[ScanResponse].cpuAmount should be(1)
  //        }
  //      }
  //    }
  //  }


  //
  //  "Calculation Service " when {
  //    "POST /scan" should {
  //      "reject wrong content type" in {
  //        Post("/scan").withEntity(ContentTypes.`text/csv(UTF-8)`,
  //          requestJson) ~> standardRoute ~> check {
  //          rejection shouldEqual UnsupportedRequestContentTypeRejection(Set(ContentTypes.`application/json`))
  //        }
  //      }
  //    }
  //  }
  //
  //  "Calculation Service " when {
  //    "POST /scan" should {
  //      "reject a request when json not valid" in {
  //        Post("/scan").withEntity(ContentTypes.`application/json`,
  //          requestJson + "BREAK THE JSON!") ~> standardRoute ~> check {
  //          rejection shouldBe a[MalformedRequestContentRejection]
  //        }
  //      }
  //    }
  //  }
}
