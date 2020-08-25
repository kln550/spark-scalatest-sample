package com.scalatest.example

import org.apache.spark.rdd.RDD.{rddToOrderedRDDFunctions, rddToPairRDDFunctions}

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object SparkWordCount {
  def main(args: Array[String]) {
    // create Spark context with Spark configuration
    val sc = new SparkContext(new SparkConf().setAppName("Spark Count"))
    process(sc, args);
  }

  def process(sc: SparkContext, args: Array[String]) {
    // read in text file and split each document into words
    val tokenized = sc.textFile(args(0)).flatMap(_.split(" "))

    // count the occurrence of each word
    val wordCounts = tokenized.map((_, 1)).reduceByKey(_ + _)

    System.out.println(wordCounts.collect().mkString(", "))
  }

}