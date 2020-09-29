package com.scalatest.example

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
import org.apache.http.entity.StringEntity
//import org.json.JSONObject
//import org.json.JSONTokener
import com.typesafe.config._
import com.google.gson.Gson
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair
import org.apache.http.protocol.HTTP
import java.util
import java.util.{ArrayList, List, Map}

case class OauthToken(access_token: String, refresh_token: String, id: String, token_type: String, issued_at: String, signature: String)
case class AwsTokenInfo(acccessKeyId: String, secreteAccessKey: String, sessionToken: String)
case class AwsTokenDetailsTest(credentials: AwsTokenInfo)
case class AwsTokenDetails(credentials: String)

case class AwsCredentials(private val credentials: java.util.Map[String, String]) {
  def getCredentials = credentials
}

class RestClientUtil {

  /* Gets the Oauth token from auth service */
  def getAccessToken(clientId: String, clientSecret: String, loginUrl: String): String = {
    var access_token = ""
    try {
      val post = new HttpPost(loginUrl)
      val nvps = new util.ArrayList[BasicNameValuePair](2)
      nvps.add(new BasicNameValuePair("client_id", clientId))
      nvps.add(new BasicNameValuePair("client_secret", clientSecret))
      post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8))
      val httpClient = new DefaultHttpClient
      println("Before getAccessToken REST call");
      val response = httpClient.execute(post)
      val handler = new BasicResponseHandler();
      println("getAccessToken: response:" + response)
      val body = handler.handleResponse(response);
      println(response)
      val gson = new Gson
      val jsonObject = gson.fromJson(body, classOf[OauthToken])
      access_token = jsonObject.access_token
      println("access_token: " + access_token)
    } catch {
      case ioe: java.io.IOException =>
      case ste: java.net.SocketTimeoutException =>
    }
    return access_token
  }


  /* Gets the Oauth token from auth service */
  def getAwsAccessTokens(accessToken: String, awsTokenUrl: String): AwsTokenInfo = {
    val awsTokenInfo  =null
    var jsonBody = "{'acccessType':'Consume'}";
    try {
      // HTTP post request.
      val post = new HttpPost(awsTokenUrl)

      // set the Content-type
      post.addHeader("Authorization", "Bearer " + accessToken)
      post.setHeader("Content-type", "application/json;v=1")
      post.setHeader("Accept", "application/json;v=1")
      post.setEntity(new StringEntity(jsonBody))

      // Making Rest call.
      val httpClient = new DefaultHttpClient
      val response = httpClient.execute(post)
      val handler = new BasicResponseHandler();
      println("response:" + response)
      val responseBody = handler.handleResponse(response);
      println(responseBody)
      val gson = new Gson
      val awsTokenInfoFromResponse = gson.fromJson(responseBody, classOf[AwsTokenDetailsTest])
      println(awsTokenInfoFromResponse.credentials)
      return  awsTokenInfoFromResponse.credentials
    } catch {
      case ioe: java.io.IOException =>
      case ste: java.net.SocketTimeoutException =>
    }
    return awsTokenInfo
  }

}


