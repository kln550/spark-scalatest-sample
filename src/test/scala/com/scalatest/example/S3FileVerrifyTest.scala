package com.scalatest.example

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.scalatest.{BeforeAndAfter, FunSuite}

class S3FileVerrifyTest extends FunSuite with BeforeAndAfter {
  // Access Key ID: AKIAIKM2JQLJTJHLEQEA
  // Secret Access Key: 8LeP/ZIoQtgMUWn+mbsk9yfIy701eyF1J3Aq4wfw

  /* These Keys would be available to you in  "Security Credentials" of
    your Amazon S3 account */
  val AWS_ACCESS_KEY = "**********"
  val AWS_SECRET_KEY = "8LeP/ZIoQtgMUWn+mbsk9yfIy701eyF1J3Aq4wfw"
  val bucketName = "prasadvatest"
  val fileName = "spark_scala_doc2.pdf"
  val provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY))
  val amazonS3Client = AmazonS3ClientBuilder.standard().withCredentials(provider).withRegion("us-east-1").build()

  test("Verify S3 file exist") {
      // This will create a bucket for storage
      val isExist = amazonS3Client.doesObjectExist(bucketName, fileName)
      if (isExist) {
        assert(true)
      } else {
        assert(false)
      }
    }
  }
