package com.scalatest.example

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.scalatest.{BeforeAndAfterAllConfigMap, ConfigMap, FunSuite}

class S3FileVerrifyTest extends FunSuite with BeforeAndAfterAllConfigMap {
  /* These Keys would be available to you in  "Security Credentials" of
    your Amazon S3 account */
  val AWS_ACCESS_KEY = ""
  val AWS_SECRET_KEY = ""
  var bucketName = ""
  var fileName = ""
  val provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY))
  val amazonS3Client = AmazonS3ClientBuilder.standard().withCredentials(provider).withRegion("us-east-1").build()

  override def beforeAll(configMap: ConfigMap) = {
    bucketName = configMap.get("bucketName").fold("")(_.toString)
    fileName = configMap.get("fileName").fold("")(_.toString)
    println("bucketName=" + bucketName)
    println("fileName=" + fileName)
  }

/*
  test("Verify S3 file exist") {

      // This will create a bucket for storage
      val isExist = amazonS3Client.doesObjectExist(bucketName, fileName)
      if (isExist) {
        assert(true)
      } else {
        assert(false)
      }
    }
*/


  test("Verify S3 bucket file count exist") {
    // This will create a bucket for storage
    val count = countObjects(bucketName)
     assert(count==1)
  }


  def countObjects(bucketName: String): Int = {
    var count = 0
    var objectListing = amazonS3Client.listObjects(bucketName)
    var currentBatchCount = objectListing.getObjectSummaries.size
    while ( {
      currentBatchCount != 0
    }) {
      count += currentBatchCount
      objectListing = amazonS3Client.listNextBatchOfObjects(objectListing)
      currentBatchCount = objectListing.getObjectSummaries.size
    }
    count
  }


  }
