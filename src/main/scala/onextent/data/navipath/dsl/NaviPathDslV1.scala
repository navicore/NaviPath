package onextent.data.navipath.dsl

import onextent.data.navipath.FieldByPath

// query path [String] "$.name" in """{"name": "Ishmael"}"""

object NaviPathDslV1 {
  val query: Query.type = Query
}

object Query {
  def path[T](querySpec: String): In[T] = new In[T](querySpec)
}

class In[T](queryStr: String) {
  def in(jsonString: String): Option[T] = {
    FieldByPath(jsonString, queryStr)
  }
}
