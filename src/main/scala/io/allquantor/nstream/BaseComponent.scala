package io.allquantor.nstream

import akka.event.LoggingAdapter

import scala.concurrent.ExecutionContext
// Base component for dynamic invocation of akka related features when you extend it.
trait BaseComponent {
  protected implicit def log: LoggingAdapter
  protected implicit def executor: ExecutionContext
}
