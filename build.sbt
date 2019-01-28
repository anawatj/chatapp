name := "chatapp"

version := "0.1"

scalaVersion := "2.12.8"
enablePlugins(FlywayPlugin)
libraryDependencies := Seq(
  "com.typesafe.akka" %% "akka-http"   % "10.1.6",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "com.typesafe.slick" %% "slick" % "3.2.3",
  "mysql" % "mysql-connector-java" % "5.1.16",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.7",
  "org.hsqldb" % "hsqldb" % "2.3.1",
  "org.mariadb.jdbc" % "mariadb-java-client" % "1.1.7",
  "com.github.t3hnar" %% "scala-bcrypt" % "3.1"
)


flywayDriver := "org.mariadb.jdbc.Driver"
flywayUrl := "jdbc:mariadb://localhost/chatapp"
flywayUser := "root"
flywayPassword := ""
flywayLocations += "db/migration"
flywayUrl in Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser in Test := "SA"
flywayPassword in Test := ""