# Project's Title
EmailAPI - This is a Spring-boot Restful API developed for handling email related functionalities.

# Project Description
This API facilitate email related functionalities like sending email, retrieving emails from inbox,  retrieving single email, drafting an email & updating existing fields in drafted email.Its a RestFul API which exposes five endpoints to consume, they are :-

/readinbox - It's a HTTP GET endpoint which enables you to fetch all emails from the user's inbox.

/readoneemail - It's a HTTP GET endpoint which enables you to fetch a single email from the user's mailbox.

/sendermail - It's a POST endpoint which enables you to send an email to the user's inbox.

/draftemail - It's a POST endpoint which enables you to draft an email and save it in the user's mailbox.

/draftemailupdate - It's a PUT endpoint which enables you to update an already drafted email and save it in the user's mailbox.


Security - API to be registered in API Gateway, which will enforce to have oAuth done by API Gateway before sending any traffic to API. Only successfully authenticated clients would be allowed to invoke the endpoints.

Deployment Strategy - Blue green deployment to be done, which will ensure zero downtime and no impact for customer experience.

Logging - Applications logs would be forwarded to Splunk, the logs can be viewed from the Splunk console which can be further used to create alerts, dashboards. Infrastructure logs like CPU utilization, memory, disk space to be captured by integrating the API with Datadog

Deployment Environment - API to be deployed in AWS Cloud EC2 instances

Scalability - API is scalable with autoscaling configuration of min -1 max -4

# Project Software Specifications

Pre-requisite Softwares/Applications :-

IDE - Spring Tool Suite 4 

Java - Openjdk 17

Build Automation Tool - Apache Maven

Gitbash

Postman
