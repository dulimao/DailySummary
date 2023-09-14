package com.example.myandroiddemo

import android.util.Log
import java.net.URI
import java.net.URISyntaxException

/**
 * url转换：https -> http+443 上汽大众icas平台和一汽大众的要求
 */
object UrlConvertor {
    private val TAG = "UrlConvertor"

    @JvmStatic
    var isDebug: Boolean = false
    @JvmStatic
    var shouldConvert: Boolean = true

    @JvmStatic
    var shouldHttpsOnly: Boolean = false

//    @JvmStatic
//    fun convertUrl(httpUrl: HttpUrl): HttpUrl {
//        Log.d(TAG, "convertUrl()_httpurl oldUrl: ${httpUrl.toString()}")
//        if (isDebug) {
//            return httpUrl
//        }
//        //url格式转换
//        var newUrl = httpUrl
//        if (shouldConvert) {
//            if (httpUrl.isHttps()) {
//                val builder = HttpUrl.Builder()
//                for (pathSegment in httpUrl.pathSegments()) {
//                    builder.addPathSegment(pathSegment)
//                }
//                newUrl = builder.scheme("http")
//                        .host(httpUrl.host())
//                        .username(httpUrl.username())
//                        .port(443)
//                        .encodedQuery(httpUrl.encodedQuery())
//                        .build()
//                Log.d(TAG, "imgUrlConvertor() newUrl: " + newUrl.url().toString())
//            }
//        }
//        return newUrl
//    }

    @JvmStatic
    fun convertUrl(url: String): String {
        Log.d(TAG, "convertUrl()_url oldUrl: $url")
        if (isDebug) {
            return url
        }
        var newUrl = url
        if (shouldConvert) {
            if (newUrl.startsWith("https")) {
                newUrl = newUrl.replace("https", "http")
                val originUri = URI.create(newUrl)
                try {
                    val newUri = URI(originUri.scheme, originUri.userInfo, originUri.host, 443, originUri.path, originUri.query, null)
                    newUrl = newUri.toString()
                    Log.d(TAG, "》》newUrl: $newUrl")
                } catch (e: URISyntaxException) {
                    e.printStackTrace()
                    newUrl = url
                }
            }
        }
        return newUrl
    }


    @JvmStatic
    fun shouldConvert(url: String): Boolean {
        return (shouldConvert) && url.startsWith("https")
    }
}