package com.xing;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class MessageHandler implements RequestHandler<ScheduledEvent, String> {
    @Override
    public String handleRequest(ScheduledEvent input, Context context) {
        LambdaLogger logger = context.getLogger();
        AmazonCloudWatch cloudWatch = AmazonCloudWatchClientBuilder.standard().withRegion("ap-southeast-1").build();
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("ap-southeast-1").build();

        double count = s3Client.listObjects("hongxing-deployment-bucket").getObjectSummaries().size();
        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace("/aws/lambda/hongxing")
                .withMetricData(new MetricDatum()
                        .withMetricName("honxing-custom-memory-metric")
                        .withUnit("Count")
                        .withValue(count));
        cloudWatch.putMetricData(request);

        logger.log("hongxing-deployment-bucket has " + count + " object");
        return "hongxing-deployment-bucket has " + count + " object";
    }

}
