import org.apache.http.client.utils.URLEncodedUtils
import spark.Request
import java.net.URI
import java.nio.charset.Charset

sealed class ResponseType {
    data class PlainText(val message: String): ResponseType()
    data class Tweet(val tweet: String): ResponseType()
    data class DeleteTweet(val tweetId: String? = null): ResponseType()
}

private enum class SupportedCommands(val commandText: String, val commandDescription: String, val commandParam: String? = null) {
    help(":help", "Lists supported commands"),
    tweet("pt ", "Posts tweet with provided {{status}}", "{{status}}"),
    deleteTweet("dt ", "Deletes tweet with provided {{tweet id}} or most recent if 'last' specified.", "{{tweet id}} OR 'last'")
}

fun getRequestBody(request: Request): String {
    val params = URLEncodedUtils.parse(URI("https://www.example.com?" + request.body()), Charset.forName("UTF-8"))
    return params.find {
        it.name.equals("Body")
    }?.value ?: "Empty"
}

fun getResponseBody(requestBody: String): ResponseType {
    val requestWithoutCase = requestBody.toLowerCase()
    return when {
        requestWithoutCase == SupportedCommands.help.commandText -> {
            ResponseType.PlainText(createCommandList())
        }
        requestWithoutCase.startsWith(SupportedCommands.tweet.commandText) -> {
            ResponseType.Tweet(requestBody.substring(SupportedCommands.tweet.commandText.length))
        }
        requestWithoutCase.startsWith(SupportedCommands.deleteTweet.commandText) -> {
            val tweetId = requestBody.substring(SupportedCommands.deleteTweet.commandText.length)
            if (tweetId == "last") {
                ResponseType.DeleteTweet()
            } else {
                ResponseType.DeleteTweet(tweetId)
            }
        }
        else -> {
            ResponseType.PlainText("Unrecognized command. Type `:help` to view list of supported commands.")
        }
    }
}

private fun createCommandList(): String {
    val commandList = StringBuilder("Supported Commands:\n")
    for (supportedCommand in SupportedCommands.values()) {
        commandList.append(supportedCommand.commandText)
        supportedCommand.commandParam?.let {
            commandList.append(it)
        }
        commandList.append(": ${supportedCommand.commandDescription}")
        commandList.append("\n")
    }
    return commandList.toString()
}