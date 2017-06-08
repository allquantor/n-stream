package io.allquantor.nstream

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport


// Base Service Trait composing the component, protocol and json configuration.
trait BaseService extends BaseComponent with SprayJsonSupport
