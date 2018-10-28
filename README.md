# Download and Rename File App

## About

This is a simple Spring Boot web app that will download and rename a file.  I wrote this to be used as part of a Jenkins pipeline to rename artifacts from Nexus to drop the version number from the artifact name.  A link similar to the one below was sent via email allowing easy access to build artifacts.

## Building and Running Tests

```mvn package```

## Running as a Docker Container

```docker-compose up -d```

## Usage

For example, if running on local and using the default port in the compose file:

In a browser, open http://localhost:8888/artifact?artifactPath=https://repo1.maven.org/maven2/org/springframework/spring-test/5.1.1.RELEASE/spring-test-5.1.1.RELEASE.jar&filename=spring-test.jar

Replace `artifactPath` and `filename` to suit your scenario.