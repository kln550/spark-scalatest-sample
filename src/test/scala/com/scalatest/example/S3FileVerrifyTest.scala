package com.scalatest.example

import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.scalatest.{BeforeAndAfterAllConfigMap, ConfigMap, FunSuite}
import java.io._
import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.impl.client.BasicResponseHandler
import org.apache.http.client.ResponseHandler
//import org.json.JSONObject
//import org.json.JSONTokener
import com.typesafe.config._
import com.google.gson.Gson
import org.apache.http.client.methods.HttpGet;

class S3FileVerrifyTest extends FunSuite with BeforeAndAfterAllConfigMap {
  var clientId = ""
  var clientSecret = ""
  var loginURL = ""
  var awsTokenUrl = ""
  var bucketName = ""
  var fileName = ""
  //val provider = new AWSStaticCredentialsProvider(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY))
  //val amazonS3Client = AmazonS3ClientBuilder.standard().withCredentials(provider).withRegion("us-east-1").build()

  override def beforeAll(configMap: ConfigMap) = {
    bucketName = configMap.get("bucketName").fold("")(_.toString)
    fileName = configMap.get("fileName").fold("")(_.toString)
    clientId = configMap.get("client_id").fold("")(_.toString)
    clientSecret = configMap.get("client_secret").fold("")(_.toString)
    loginURL = configMap.get("loginUrl").fold("")(_.toString)
    awsTokenUrl = configMap.get("awsTokenUrl").fold("")(_.toString)

    println("bucketName=" + bucketName)
    println("fileName=" + fileName)
  }

  test("Verify whether user can able to get the Oauth and AWS tokens") {
    val restClient = new RestClientUtil()
    // This will create a bucket for storage.
    var accessToken = restClient.getOAuthAccessToken(clientId, clientSecret, loginURL)
    println("acccessKeyId: " + accessToken)
    assert(accessToken !== "")

    // Gets AWS access key and secret access key.
    val tokenInfo = restClient.getAwsTokens(accessToken, awsTokenUrl)
    println("acccessKeyId: " + tokenInfo.acccessKeyId)
    println("SecreteAccessKey: " + tokenInfo.secreteAccessKey)
    println("sessionToken: " + tokenInfo.sessionToken)
    assert(tokenInfo.acccessKeyId !== null)
    assert(tokenInfo.secreteAccessKey !== null)
    assert(tokenInfo.sessionToken !== null)
  }

  /*test("Verify S3 bucket file count exist") {
    // This will create a bucket for storage
    val count = countObjects(bucketName)
     assert(count==1)
  }*/


//  def countObjects(bucketName: String): Int = {
//    var count = 0
//    var objectListing = amazonS3Client.listObjects(bucketName)
//    var currentBatchCount = objectListing.getObjectSummaries.size
//    while ( {
//      currentBatchCount != 0
//    }) {
//      count += currentBatchCount
//      objectListing = amazonS3Client.listNextBatchOfObjects(objectListing)
//      currentBatchCount = objectListing.getObjectSummaries.size
//    }
//    count
//  }

}
