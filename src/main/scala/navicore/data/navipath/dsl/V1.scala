package navicore.data.navipath.dsl

import navicore.data.navipath.FieldByPath

import scala.reflect.ClassTag

// query path [String] "$.name" in """{"name": "Ishmael"}"""

object NaviPathDslV1 {
  val query: Query.type = Query
}

object Query {
  def path[T: ClassTag](querySpec: String): In[T] = new In[T](querySpec)
}

class In[T: ClassTag](queryStr: String) {
  def in(jsonString: String): Option[T] = {
    FieldByPath(jsonString, queryStr)
  }
}
