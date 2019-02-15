# P2 Inspector

[![Build Status](https://travis-ci.org/avojak/p2-inspector.svg?branch=master)](https://travis-ci.org/avojak/p2-inspector) [![Coverage Status](https://coveralls.io/repos/github/avojak/p2-inspector/badge.svg?branch=master)](https://coveralls.io/github/avojak/p2-inspector?branch=master) [![License](https://img.shields.io/badge/license-EPL%201.0-blue.svg)](https://opensource.org/licenses/EPL-1.0) ![Version](https://img.shields.io/badge/version-1.0.0--SNAPSHOT-yellow.svg)

P2 Inspector is a headless Eclipse plugin which exposes a REST interface for inspecting and retrieving the contents of a remote P2 repository.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Java 1.8+

### Installing

```
$ cd com.avojak.webapp.p2.inspector.releng
$ mvn clean install
```

Artifacts are build in:

```
com.avojak.webapp.p2.inspector.packaging/target/products/com.avojak.webapp.p2.inspector.product/
```

## Usage

Different executables are provided for various platforms (Windows, macOS, Linux).

### Linux Example

```
$ cd com.avojak.webapp.p2.inspector.packaging/target/products/
$ cd com.avojak.webapp.p2.inspector.product/linux/gtk/x86_64/
$ ./p2-inspector
```

### Docker

Docker is supported via the [Dockerfile Maven plugin](https://github.com/spotify/dockerfile-maven).

```
$ mvn package
$ docker run --rm -p 8081:8081 avojak/p2-inspector:1.0.0-SNAPSHOT
```

## Deployment

TODO

## Built With

* [Tycho](https://www.eclipse.org/tycho/) - Building the Eclipse plugin
* [Maven](https://maven.apache.org/) - Dependency management
* [Jetty](https://www.eclipse.org/jetty/) - Embedded web server
* [Dockerfile Maven](https://github.com/spotify/dockerfile-maven) - Building the Docker image

## Versioning

I use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/avojak/p2-inspector/tags).

## License

This project is licensed under the Eclipse Public License v1.0 - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

* The structure of this project, and the ability to create a cross-platform, headless Eclipse plugin, was directly inspired by [irbull/p2diff](https://github.com/irbull/p2diff)
