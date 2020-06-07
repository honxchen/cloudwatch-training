## cloudwantch-training

### prerequisite
* aws cli

### setup
```
$ aws s3api create-bucket --bucket hongxing-deployment-bucket --region ap-southeast-1 --create-bucket-configuration LocationConstraint=ap-southeast-1

```

### deploy
```
$ ./gradlew shadowJar
$ aws s3 cp build/libs/cloudwatch-training-1.0-SNAPSHOT.jar s3://hongxing-deployment-bucket/func/v1/
$ aws cloudformation create-stack --stack-name hongxing-stack --template-body file://cloudformation.yml
$ aws cloudformation update-stack --stack-name hongxing-stack --template-body file://cloudformation.yml
```