## Requirements

* Apache Buildr (available via rubygems)
* Ruby 1.8.7+
* JDK 1.6+
* Interweb access

## Getting started

You'll need to create two java property files (timeapp.ini & rmapp.ini) containing the following variables;

* username
* site

## Packaging and running

buildr package # will create a jar in the target folders, the jar merges all dependencies.
buildr run		 # will run the respective project (currently te & rm), unknown effect from the root folder.

