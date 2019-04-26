# aws-s3-java-sample
This app integrated the AWS SDK of version 2.x will help you to connect to s3 to upload and download an object.

- first step
  go to aws > iam > your user > security credentials > add access key

- second step
  set the security credentials in the application.properties

  aws.access_key_id=your access key
  aws.secret_access_key=your secret access key

- third step
  set the region and bucket name in the application.properties
  
  aws.s3.bucket=your bucket name
  aws.s3.region=cn-northwest-1(Nixia China for example)
  
- last step
  build and run your server at last.
  
  