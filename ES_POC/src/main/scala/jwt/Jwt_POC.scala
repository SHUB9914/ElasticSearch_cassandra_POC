package jwt

import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim}


object Jwt_POC extends App {

  val secretKey = "qwwwwwaxsdsd123"
  val claim = """{"user" : "shubham Agrawal" , "id" : 12345 , "address" : "Noida"}"""

  val token = Jwt.encode(JwtClaim(claim).issuedNow.expiresIn(10) , secretKey , JwtAlgorithm.HS256)

  println(">>>token>"+token)

  val res = Jwt.decode(token , secretKey , Seq(JwtAlgorithm.HS256))

  println(">>>>>res>>>>" +res)

}
