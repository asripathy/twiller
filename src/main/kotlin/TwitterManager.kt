import com.github.redouane59.twitter.TwitterClient
import com.github.redouane59.twitter.dto.tweet.Tweet
import com.github.redouane59.twitter.signature.TwitterCredentials
import java.util.NoSuchElementException

class TwitterManager {
    private val twitterClient = TwitterClient(
        TwitterCredentials.builder()
            .accessToken(TWITTER_ACCESS_TOKEN)
            .accessTokenSecret(TWITTER_ACCESS_TOKEN_SECRET)
            .apiKey(TWITTER_API_KEY)
            .apiSecretKey(TWITTER_API_KEY_SECRET)
            .build()
    )

    private var lastTweet: Tweet? = null

    fun sendTweet(status: String): String {
        val tweet = try {
            twitterClient.postTweet(status)
        } catch (exception: Exception) {
            return "Error posting tweet."
        }
        lastTweet = tweet
        return "Tweeting status: $status. Tweet ID: ${tweet.id} for reference."

    }

    fun deleteTweet(tweetId: String): String {
        try {
            twitterClient.deleteTweet(tweetId)
        } catch(exception: NoSuchElementException) {
            return "Error finding tweet."
        }
        return "Successfully deleted tweet"
    }

    fun deleteLastTweet(): String {
        return if (lastTweet != null) {
            val tweetId = lastTweet!!.id
            lastTweet = null
            return deleteTweet(tweetId)
        } else {
            "No applicable tweet found."
        }
    }
}