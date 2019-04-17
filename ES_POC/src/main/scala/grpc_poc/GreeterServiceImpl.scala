//package grpc_poc
//
//import akka.NotUsed
//import akka.stream.Materializer
//import akka.stream.scaladsl.Source
//import example.myapp.helloworld.grpc.{GreeterService, HelloReply, HelloRequest}
//
//import scala.concurrent.Future
//
//class GreeterServiceImpl(materializer: Materializer) extends GreeterService {
//
//  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply("This is hello message"))
//
//  override def itKeepsTalking(in: Source[HelloRequest, NotUsed]): Future[HelloReply] = ???
//
//  override def itKeepsReplying(in: HelloRequest): Source[HelloReply, NotUsed] = ???
//
//  override def streamHellos(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = ???
//}
