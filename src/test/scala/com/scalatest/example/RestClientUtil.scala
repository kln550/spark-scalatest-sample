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
import java.util.{ArrayList, List}

case class Token(access_token: String, instance_url: String,
                 id: String,
                 token_type: String,
                 issued_at: String,
                 signature: String)


class RestClientUtil {
  def getAccessToken(clientId: String,  clientSecret: String): String = {
    var access_token = ""
    try {
      val post = new HttpPost("http://localhost:8080/oauth2/token")
      val nvps = new util.ArrayList[BasicNameValuePair](2)
      nvps.add(new BasicNameValuePair("client_id", clientId))
      nvps.add(new BasicNameValuePair("client_secret", clientSecret))
      post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8))
      val httpClient = new DefaultHttpClient
      val response = httpClient.execute(post)
      val handler = new BasicResponseHandler();
      println("response:" + response)
      val body = handler.handleResponse(response);
      println(response)
      val gson = new Gson
      val jsonObject = gson.fromJson(body, classOf[Token])
      access_token = jsonObject.access_token
      println("access_token: " + access_token)
    } catch {
      case ioe: java.io.IOException =>
      case ste: java.net.SocketTimeoutException =>
    }
    return access_token
  }

}
