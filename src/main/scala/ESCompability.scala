/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.actionml

import org.apache.http.{ Header, HttpEntity }
import org.elasticsearch.client.{ Request, Response, RestClient }

import scala.collection.JavaConversions._
import scala.language.implicitConversions

object ESCompability {
  implicit def ToOldClient(s: RestClient): BetterClient = new BetterClient(s)

  class BetterClient(val restClient: RestClient) {
    def performRequest(
      method: String,
      endpoint: String,
      params: Map[String, String],
      entity: HttpEntity): Response = {
      val request = new Request(method, endpoint)
      params.foreach(kv => request.addParameter(kv._1, kv._2))
      request.setEntity(entity)
      restClient.performRequest(request)
    }
    def performRequest(
      method: String,
      endpoint: String,
      params: java.util.Map[String, String],
      entity: HttpEntity): Response = {
      val request = new Request(method, endpoint)
      params.foreach(kv => request.addParameter(kv._1, kv._2))
      request.setEntity(entity)
      restClient.performRequest(request)
    }
    def performRequest(
      method: String,
      endpoint: String,
      params: java.util.Map[String, String],
      entity: HttpEntity,
      header: Header): Response = {
      val request = new Request(method, endpoint)
      params.foreach(kv => request.addParameter(kv._1, kv._2))
      request.setEntity(entity)
      restClient.performRequest(request)
    }

    def performRequest(
      method: String,
      endpoint: String,
      params: java.util.Map[String, String]): Response = {
      val request = new Request(method, endpoint)
      params.foreach(kv => request.addParameter(kv._1, kv._2))
      restClient.performRequest(request)
    }
  }

}
