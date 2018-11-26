import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 *
 * This is a demo test for SMS publishing by AWS SNS Service
 *
 * @author: jiasfeng
 * @Date: 11/26/2018
 */
public class SMSPublish {
    public static void main(String[] args) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials("xxx", "xxx");
        AmazonSNS snsClient = AmazonSNSClient.builder()
                                    .withRegion("us-west-2")
                                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                                    .build();
        // Setting Preference
        Map<String, MessageAttributeValue> attributes = setDefaultSmsAttributes(snsClient);
        // Send SMS Message
        sendSMSMessage(snsClient, message, phoneNumber, attributes);
    }

    /**
     * Setting Preferences
     * @param snsClient
     */
    public static Map<String, MessageAttributeValue> setDefaultSmsAttributes(AmazonSNS snsClient) {
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                .withStringValue("mySenderID") //The sender ID shown on the device.
                .withDataType("String"));
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.50") //Sets the max price to 0.50 USD.
                .withDataType("Number"));
        smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                .withStringValue("Promotional") //Sets the type to promotional.
                .withDataType("String"));
        return smsAttributes;
    }

    /**
     * Send SMS Message
     * @param snsClient
     * @param message
     * @param phoneNumber
     * @param smsAttributes
     */
    public static void sendSMSMessage(AmazonSNS snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result.getMessageId()); // Prints the message ID.
    }
}
