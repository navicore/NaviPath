[![Build Status](https://travis-ci.org/navicore/NaviPath.svg?branch=master)](https://travis-ci.org/navicore/NaviPath)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/70e6c4da5022432ab78cc212ed55759e)](https://www.codacy.com/app/navicore/NaviPath?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=navicore/NaviPath&amp;utm_campaign=Badge_Grade)

NaviPath
-----

A lib for adding implicit [JsonPath](http://jsonpath.com/) functions to Strings
======

Based on https://github.com/gatling/jsonpath.

See http://goessner.net/articles/JsonPath/ for jsonpath documentation.

## INSTALL

* ongoing dev is for for scala 2.12+
* if you use scala 2.11 - use version 2.1.0

```
// https://mvnrepository.com/artifact/tech.navicore/navipath
libraryDependencies += "tech.navicore" %% "navipath" % "3.0.1"
```

## USAGE

See http://goessner.net/articles/JsonPath/ for JsonPath documentation.

### DSL USAGE

Examples where "\<json\>" is a valid json string or parsed output from `.asJson`:
```scala
    import navicore.data.navipath.dsl.NaviPathSyntax._
    "<json>".query[String]("$.name")
    "<json>".query[Long]("$.widget.window.height")
    "<json>".query[List[String]]("$.stuff[*].name")
    "<json>".query[List[Int]]("$.stuff[*].value")
```

First match support:
```scala
    val jsonString = """{"name": "Ishmael"}"""
    import navicore.data.navipath.dsl.NaviPathSyntax._
    val result = jsonString.query[String]("$.name")
    result should be ('defined)
    result.fold()(assertResult("Ishmael"))
```

Multiple matches support:
```scala
    val jsonString = """{"stuff": [{"name": "Ishmael"}, {"name": "Mud"}]}"""
    import navicore.data.navipath.dsl.NaviPathSyntax._
    val results = jsonString.query[List[String]]("$.stuff[*].name")
    results.fold()(r => assert(r.head == "Ishmael"))
    results.fold()(r => assert(r(1) == "Mud"))
```

Parse once, query many times support:
```scala
    val jsonString = """{"stuff": [{"name": "Ishmael", "id": 1}, {"name": "Mud", "id": 2}]}"""
    import navicore.data.navipath.dsl.NaviPathSyntax._
    val parsedJson = jsonString.asJson
    val names = parsedJson.query[List[String]]("$.stuff[*].name")
    val ids = parsedJson.query[List[Int]]("$.stuff[*].value")
    ...
    ...
```

