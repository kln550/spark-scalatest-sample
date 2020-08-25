package com.scalatest.example

import org.apache.spark.sql
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.scalatest.{BeforeAndAfterEach, FunSuite}

class ReadAndWriteTestSpec extends FunSuite with BeforeAndAfterEach {

  private val master = "local"
  private val appName = "ReadAndWrite-Test"
  private val usersParque = "src/test/resources/testdata/users.parquet";

  var spark: SparkSession = _

  override def beforeEach(): Unit = {
    spark = new sql.SparkSession.Builder().appName(appName).master(master).getOrCreate()
  }

  test("creating data frame from parquet file will pass count==2") {
    val sparkSession = spark
    import sparkSession.implicits._
    val usersDF = spark.read.parquet(usersParque)
    usersDF.show(10);
    assert(usersDF.count == 2)
  }

  test("creating data frame from parquet file will fail count ==3") {
    val sparkSession = spark
    import sparkSession.implicits._
    val usersDF = spark.read.parquet(usersParque)
    usersDF.show(10);
    assert(usersDF.count == 3)
  }

}
