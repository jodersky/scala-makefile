# A Makefile for Scala Applications

A makefile that demonstrates how building Scala applications can be
very simple.

It supports *dependency resolution of maven artefacts*, enables
building *executables* (not only fat jars, actual executable files)
and all **builds are reproducible** (anyone building the same source
gets bit-wise identical binaries).

## Dependencies

As this is a plain makefile, it relies on a number of tools that must
be present in the caller's environment. Those are:

- [scalac](https://www.scala-lang.org/download/) to compile Scala
- [coursier](https://coursier.github.io/coursier/1.1.0-SNAPSHOT/docs/quick-start-cli.html#installation) for dependency resolution
- openjdk to run binaries and for packaging jar files
- zip and unzip for creating standalone fat jars
- (optional) [scalafmt](https://scalameta.org/scalafmt/docs/installation.html#cli)

Tested on Linux, should work on Darwin and BSDs, does not support
Windows.

## How-To

Source files are in `src/` and maven dependencies are declared in
`Maven.list`.

The main targets for a development workflow are typically

- `make` to build a library jar in target/library.jar
- `make run` to run the application 

The application may be deployed as a standalone binary, only requiring
a Java runtime environemnt, by running `make target/application`.

## Example

This project contains an example application that prints the identicon
of a given string. Build it by running `make target/application`.

- Generate an identicon: `target/application username`.
- View an identicon: `inkview <(target/application admin)`.

## Known Issues / FAQ

- **It's slow**. Yes, this Makefile invokes a cold compiler on every
  source change. Using [bloop](https://scalacenter.github.io/bloop/)
  could help here.
  
- **Tools not included**. To run this makefile, all its constituent
  tools (scalac, scalafmt, coursier, etc) must be configured in the
  developer's environment. It's not as convenient for newcomers as the
  out-of-the-box experience that sbt or cbt offer, however adhering to
  the unix philosophy of "do one thing and do it well" is the whole
  purpose of this project.

- **Doesn't work with multiple Akka libraries**. The jar combination
  script is quite dumb: it simply extracts contents of jar files in a
  directory tree and recombines the final directory into a jar. In the
  process, any identically named files get overwritten. Since Akka
  relies on `reference.conf` files within jars, if multiple Akka
  libraries are used, the latest config in the classpath overwrites
  all others. A smarter merging script would be required to fix tis
  issue.

- **The resulting binary is large**. The fat jar which is contained in
  the application contains *all* dependent jars, which may add up for
  projects with complex transitive dependencies. Using a tool like
  proguard could drastically decrease the size of the final binary.

- **The resulting binary requires a Java runtime**. See
  [scala-native](http://www.scala-native.org/en/v0.3.8/), in the
  future hopefully we can add support for it in the form of an
  independent makefile.
  
- **Should I use this?** Maybe. This project was intended as a
  proof-of-concept; it was motivated by my feeling that build tools in
  the Scala world tend to be very complex machines, and that using
  them to their full extent requires almost as much effort as learning
  a new language.
  
  My suggestion is that you should give this Makefile a shot if
  
  1. You are new to Scala but familiar with programming and the unix
     environment, and want a quick but thorough overview of the steps
     involved in going from a source file to an executable.
 
  2. You are building an **application**. This makefile is too
     bare-bones for libraries (for example it does not support
     multiple scala version or uploading to Maven Central).

  3. You agree with the following sentiment, as
     [cbt](https://github.com/cvogt/cbt/)'s author, Chris Vogt, puts
     it:

	> Do you own your Build Tool or does your Build Tool own you?
