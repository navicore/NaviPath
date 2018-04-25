[![Build Status](https://travis-ci.org/navicore/NaviPath.svg?branch=master)](https://travis-ci.org/navicore/NaviPath)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/70e6c4da5022432ab78cc212ed55759e)](https://www.codacy.com/app/navicore/NaviPath?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=navicore/NaviPath&amp;utm_campaign=Badge_Grade)

NaviPath
-----

A lib for adding implicit [JsonPath](http://jsonpath.com/) functions to Strings
======

## UNDER CONSTRUCTION 

## UNDER CONSTRUCTION 

## UNDER CONSTRUCTION 

## INSTALL

* for scala 2.12+ only

```
// https://mvnrepository.com/artifact/tech.navicore/navipath
libraryDependencies += "tech.navicore" %% "navipath" % "0.1.3"
```

## DSL V1 USAGE

`query path [<RESULT TYPE>] "<JSON PATH>" in "<JSON STRING>"`

```scala
    val jsonString = """{"name": "Ishmael"}"""
    import onextent.data.navipath.dsl.NaviPathDslV1._
    val result = query path [String] "$.name" in jsonString
    result.fold()(assertResult("Ishmael"))
```

## DSL V2 USAGE

First match support:

```scala
    val jsonString = """{"name": "Ishmael"}"""
    import onextent.data.navipath.dsl.NaviPathDslV2._
    val result = jsonString.query[String]("$.name")
    result.fold()(assertResult("Ishmael"))
```

Multiple matches support:

```scala
    val jsonString = """[{"name": "Ishmael"}, {"name": "Mud"}]"""
    import onextent.data.navipath.dsl.NaviPathDslV2._
    val results = jsonString.query[List[String]]("$.widget.stuff[*].name")
    results.fold()(r => assert(r.head == "Ishmael"))
    results.fold()(r => assert(r(1) == "Mud"))
```

## OPS

### publish local

```console
sbt +publishLocalSigned
```

### publish to nexus staging

```console
sbt +publishSigned
sbt sonatypeReleaseAll
```

