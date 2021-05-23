import com.twilio.twiml.MessagingResponse
import com.twilio.twiml.messaging.Body
import com.twilio.twiml.messaging.Message
import spark.Spark.post

fun main(args: Array<String>) {
    val twitterManager = TwitterManager()

    post("/sms") { request, response ->
        val requestBody = getRequestBody(request)
        val responseBody = getResponseBody(requestBody)
        response.type("application/xml")
        val bodyText = when (responseBody) {
            is ResponseType.PlainText -> {
                responseBody.message
            }
            is ResponseType.Tweet -> {
                twitterManager.sendTweet(responseBody.tweet)
            }
            is ResponseType.DeleteTweet -> {
                if (responseBody.tweetId != null) {
                    twitterManager.deleteTweet(responseBody.tweetId)
                } else {
                    twitterManager.deleteLastTweet()
                }
            }
        }
        val body = Body.Builder(bodyText)
            .build()
        val sms = Message.Builder()
            .body(body)
            .build()
        val twiml = MessagingResponse.Builder()
            .message(sms)
            .build()
        return@post twiml.toXml()
    }
}