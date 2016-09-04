package de.kreditech.oktopus.prophet

import spray.json.DefaultJsonProtocol

trait Protocol extends DefaultJsonProtocol {

  import de.kreditech.oktopus.prophet.ProtocolTypes.{
    ResourceCalculationProposalRequest,
    ResourceResponse
  }

  // Implicit format to ensure code generation for json-class and vice-versa transformation of the protocol types.
  implicit val requestFormat = jsonFormat3(
    ResourceCalculationProposalRequest.apply)
  implicit val responseFormat = jsonFormat3(ResourceResponse.apply)
}

/**
  * Protocol Type definition.
  * Each type describe an entity related to the communication protocol of prophet.
  */
object ProtocolTypes {

  final case class ResourceCalculationProposalRequest(jobCount: Int,
                                                      rows: Long,
                                                      columns: Long)

  final case class ResourceResponse(cpuAmount: Int, memory: Long, time: Long)

}
