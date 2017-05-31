package io.allquantor.nstream

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import io.allquantor.nstream.protocol.Protocol

// Base Service Trait composing the component, protocol and json configuration.
trait BaseService extends BaseComponent with Protocol with SprayJsonSupport
