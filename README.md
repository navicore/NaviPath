[![Build Status](https://travis-ci.org/navicore/NaviPath.svg?branch=master)](https://travis-ci.org/navicore/NaviPath)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/70e6c4da5022432ab78cc212ed55759e)](https://www.codacy.com/app/navicore/NaviPath?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=navicore/NaviPath&amp;utm_campaign=Badge_Grade)

NaviPath
-----

A lib for adding implicit [JsonPath](http://jsonpath.com/) functions to Strings
======

## UNDER CONSTRUCTION 

## UNDER CONSTRUCTION 

## UNDER CONSTRUCTION 

# INSTALL

# DSL V1 USAGE

`query path [<RESULT TYPE>] "<JSON PATH>" in "<JSON STRING>"`

```scala
    // query path [String] "$.name" in """{"name": "Ishmael"}"""
    val result = query path [String] "$.name" in jsonString
    result.fold()(assertResult("Ishmael"))
```

# OPS

### publish local

```console
sbt +publishLocalSigned
```

### publish to nexus staging

```console
sbt +publishSigned
sbt sonatypeReleaseAll
```


