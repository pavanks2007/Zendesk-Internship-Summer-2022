name := "Zendesk_Coding_Challenge"
 
version := "1.0" 
      
lazy val `zcc` = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.mavenLocal
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.13.6"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies ++=  Seq(
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "org.apache.commons" % "commons-lang3" % "3.9",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)

      