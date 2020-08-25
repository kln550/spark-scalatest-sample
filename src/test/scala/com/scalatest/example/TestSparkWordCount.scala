package com.scalatest.example

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{BeforeAndAfter, FunSuite}

class TestSparkWordCount extends FunSuite with BeforeAndAfter {
  private val wordCountInputTxt = "src/test/resources/testdata/wordCountInputfile.txt";
  var sc: SparkContext = _

  before {
    val conf = new SparkConf()
      .setAppName("Test SparkWordCount")
      .setMaster("local")
      .set("spark.default.parallelism", "1")

    sc = new SparkContext(conf)
  }

  test("SparkWordCount Test should fail") {
    SparkWordCount.process(sc, Array(wordCountInputTxt));
    //assertResult(out)(SparkWordCount.process(in, 1).collect)
  }

  after {
    sc.stop()
  }
}