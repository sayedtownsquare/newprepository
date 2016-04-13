package com.tsm.ds.mongo

import java.io.File

import com.mongodb.casbah.Imports._

object AnomimizeApp {
  
  def main(args:Array[String]) {
    val mongoClient = MongoClient("localhost", 27017)     
    val db = mongoClient("bigdata")
    
  }
  
  
}