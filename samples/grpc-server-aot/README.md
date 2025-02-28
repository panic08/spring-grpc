# Spring Boot gRPC Sample

This project is a copy one of the samples from the [gRPC Spring Boot Starter](https://github.com/yidongnan/grpc-spring-boot-starter/blob/master/examples/local-grpc-server/build.gradle). Build and run any way you like to run Spring Boot. E.g:

```
$ ./mvnw spring-boot:run
...
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.0)

2022-12-08T05:32:24.934-08:00  INFO 551632 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication using Java 17.0.5 with PID 551632 (/home/dsyer/dev/scratch/demo/target/classes started by dsyer in /home/dsyer/dev/scratch/demo)
2022-12-08T05:32:24.938-08:00  INFO 551632 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to 1 default profile: "default"
2022-12-08T05:32:25.377-08:00  WARN 551632 --- [           main] ocalVariableTableParameterNameDiscoverer : Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: net.devh.boot.grpc.server.autoconfigure.GrpcHealthServiceAutoConfiguration
2022-12-08T05:32:25.416-08:00  WARN 551632 --- [           main] ocalVariableTableParameterNameDiscoverer : Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration
2022-12-08T05:32:25.425-08:00  WARN 551632 --- [           main] ocalVariableTableParameterNameDiscoverer : Using deprecated '-debug' fallback for parameter name resolution. Compile the affected code with '-parameters' instead or avoid its introspection: net.devh.boot.grpc.server.autoconfigure.GrpcServerFactoryAutoConfiguration
2022-12-08T05:32:25.427-08:00  INFO 551632 --- [           main] g.s.a.GrpcServerFactoryAutoConfiguration : Detected grpc-netty: Creating NettyGrpcServerFactory
2022-12-08T05:32:25.712-08:00  INFO 551632 --- [           main] n.d.b.g.s.s.AbstractGrpcServerFactory    : Registered gRPC service: Simple, bean: grpcServerService, class: com.example.demo.GrpcServerService
2022-12-08T05:32:25.712-08:00  INFO 551632 --- [           main] n.d.b.g.s.s.AbstractGrpcServerFactory    : Registered gRPC service: grpc.health.v1.Health, bean: grpcHealthService, class: io.grpc.protobuf.services.HealthServiceImpl
2022-12-08T05:32:25.712-08:00  INFO 551632 --- [           main] n.d.b.g.s.s.AbstractGrpcServerFactory    : Registered gRPC service: grpc.reflection.v1alpha.ServerReflection, bean: protoReflectionService, class: io.grpc.protobuf.services.ProtoReflectionService
2022-12-08T05:32:25.820-08:00  INFO 551632 --- [           main] n.d.b.g.s.s.GrpcServerLifecycle          : gRPC Server started, listening on address: *, port: 9090
2022-12-08T05:32:25.831-08:00  INFO 551632 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.264 seconds (process running for 1.623)
```

The server starts by default on port 9090. Test with [gRPCurl](https://github.com/fullstorydev/grpcurl):

```
$ grpcurl -d '{"name":"Hi"}' -plaintext localhost:9090 Simple.SayHello
{
  "message": "Hello ==\u003e Hi"
}
```

## Native Image + AOT

The app compiles to a native image with Spring AOT unsuccessfully because some classes are still created when building the image:

```
$ ./mvnw -Paot spring-boot:build-image
    [creator]     Error: Classes that should be initialized at run time got initialized during image building:
    [creator]      ch.qos.logback.classic.Logger was unintentionally initialized at build time. To see why ch.qos.logback.classic.Logger got initialized use --trace-class-initialization=ch.qos.logback.classic.Logger
    [creator]     ch.qos.logback.core.util.StatusPrinter was unintentionally initialized at build time. To see why ch.qos.logback.core.util.StatusPrinter got initialized use --trace-class-initialization=ch.qos.logback.core.util.StatusPrinter
    [creator]     ch.qos.logback.core.util.StatusPrinter2 was unintentionally initialized at build time. To see why ch.qos.logback.core.util.StatusPrinter2 got initialized use --trace-class-initialization=ch.qos.logback.core.util.StatusPrinter2
    [creator]     org.slf4j.helpers.Reporter was unintentionally initialized at build time. To see why org.slf4j.helpers.Reporter got initialized use --trace-class-initialization=org.slf4j.helpers.Reporter
    [creator]     ch.qos.logback.core.status.InfoStatus was unintentionally initialized at build time. To see why ch.qos.logback.core.status.InfoStatus got initialized use --trace-class-initialization=ch.qos.logback.core.status.InfoStatus
    [creator]     ch.qos.logback.core.util.Loader was unintentionally initialized at build time. To see why ch.qos.logback.core.util.Loader got initialized use --trace-class-initialization=ch.qos.logback.core.util.Loader
    [creator]     ch.qos.logback.core.status.StatusBase was unintentionally initialized at build time. To see why ch.qos.logback.core.status.StatusBase got initialized use --trace-class-initialization=ch.qos.logback.core.status.StatusBase
    [creator]     org.slf4j.LoggerFactory was unintentionally initialized at build time. To see why org.slf4j.LoggerFactory got initialized use --trace-class-initialization=org.slf4j.LoggerFactory
    [creator]     ch.qos.logback.classic.Level was unintentionally initialized at build time. To see why ch.qos.logback.classic.Level got initialized use --trace-class-initialization=ch.qos.logback.classic.Level
    [creator]     To see how the classes got initialized, use --trace-class-initialization=ch.qos.logback.classic.Logger,ch.qos.logback.core.util.StatusPrinter,ch.qos.logback.core.util.StatusPrinter2,org.slf4j.helpers.Reporter,ch.qos.logback.core.status.InfoStatus,ch.qos.logback.core.util.Loader,ch.qos.logback.core.status.StatusBase,org.slf4j.LoggerFactory,ch.qos.logback.classic.Level
    [creator]     Error: Use -H:+ReportExceptionStackTraces to print stacktrace of underlying exception
    [creator]     --------------------------------------------------------------------------------
    [creator]         7.1s (14.6% of total time) in 87 GCs | Peak RSS: 1.96GB | CPU load: 3.65
    [creator]     ================================================================================
    [creator]     Finished generating 'org.springframework.grpc.sample.GrpcServerApplication' in 48.0s.
    [creator]     unable to invoke layer creator
    [creator]     unable to contribute native-image layer
    [creator]     error running build
    [creator]     exit status 1
    [creator]     ERROR: failed to build: exit status 1

> Task :bootBuildImage FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':bootBuildImage'.
> Builder lifecycle 'creator' failed with status code 51

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

BUILD FAILED in 1m 26s
13 actionable tasks: 13 executed       : Started DemoApplication in 0.046 seconds (process running for 0.052)
```
