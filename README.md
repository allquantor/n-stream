Prophet
=======
A micro service to predict resources would be needed for a particular spark job.

The project is based on  *Akka*, *Akka Stream*, *Akka Http*, *Scalaz*, *ScalaTest* and *ScalaMock* at their latest versions as dependencies.

If you want to extend it, please stick to the service based architecture.

* Create a new service (including your http route) and extend the BaseService component.
* Extend the akka-http server to listen to your new created route.
* Take care about supervision and non happy path.
* Document the purpose of your service.
* Write tests.


