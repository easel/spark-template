lazy val `spark-template` = project.in(file("."))
  .configs(IntegrationTest)
  .settings(
    organization := "io.github",
    name := "spark-template",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.11.12",
    Defaults.itSettings,
    libraryDependencies ++= Seq (
      "org.apache.spark" %% "spark-sql" % "2.4.4" % Provided
    ),
    fork in run := true,
    fork in runMain := true,
    parallelExecution in test := false,
    parallelExecution in IntegrationTest := false,

    // Assembly settings
    assemblyMergeStrategy in assembly := {
      case PathList("org", "aopalliance", xs@_*) => MergeStrategy.last
      case PathList("javax", "inject", xs@_*) => MergeStrategy.last
      case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
      case PathList("javax", "activation", xs@_*) => MergeStrategy.last
      case PathList("org", "apache", xs@_*) => MergeStrategy.last
      case PathList("com", "google", xs@_*) => MergeStrategy.last
      case PathList("com", "esotericsoftware", xs@_*) => MergeStrategy.last
      case PathList("com", "codahale", xs@_*) => MergeStrategy.last
      case PathList("com", "yammer", xs@_*) => MergeStrategy.last
      case "about.html" => MergeStrategy.rename
      case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
      case "META-INF/mailcap" => MergeStrategy.last
      case "META-INF/mimetypes.default" => MergeStrategy.last
      case "plugin.properties" => MergeStrategy.last
      case "log4j.properties" => MergeStrategy.last
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    },

    // Force run to include classpath entries excluded from the assembly
    run in Compile := Defaults
      .runTask(
        fullClasspath in Compile,
        mainClass in(Compile, run),
        runner in(Compile, run)
      )
      .evaluated,
    runMain in Compile := Defaults
      .runMainTask(fullClasspath in Compile, runner in(Compile, run))
      .evaluated
  )
