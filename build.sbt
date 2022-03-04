name := "LibraryManage"

version := "0.1"

libraryDependencies ++= List(
    "com.typesafe.slick" %% "slick" % "3.3.3",
    "org.slf4j" % "slf4j-nop" % "1.7.36",
    "mysql" % "mysql-connector-java" % "8.0.25",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
    "org.scalatest" %% "scalatest" % "3.2.9" % "test"
)
