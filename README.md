## Instructions

### Create Twilio Phone number
Follow instructions here: https://www.twilio.com/docs/sms/quickstart/java#get-a-phone-number

### Create Env File for Twitter
Provide Twitter Access Token, Access Token Secret, API Key, API Key secret via an env file for use by TwitterManager.kt

### Run TwillerApp.kt
This will have the Java spark web application server start listening on port 4567.

### Set up WebHook
Run following from command line:
```aidl
twilio phone-numbers:update "+15017122661" --sms-url="http://localhost:4567/sms"
```

Replace phone number with your Twilio phone number.


### Send a Text
Text your Twilio phone number to get started!
