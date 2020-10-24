![Alt text](images/android_samples_logo.png?raw=true "AWS Amplify - Android samples")

AWS Amplify is an end-to-end solution that enables mobile and front-end web developers to build and deploy secure, scalable full stack applications, powered by AWS. With Amplify, you can configure app backends in minutes, connect them to your app in just a few lines of code, and deploy static web apps in three steps. Get to market faster with AWS Amplify.

This repository focus on show how you can integrate AWS Amplify in your Android application and offers you a list of samples which correspond to the user-centric libraries that the framework offers. 

NOTE: The goal of the samples are to show in a very simple manner how to use the AWS Amplify Android API's. Maybe some samples does not follow the best design app architectures recommended which is intentional, so the readers can focus in the AWS Amplify Android API's

# AWS Amplify Installation

- Create an AWS account if you do not have already.

- Install npm:

```
npm install -g @aws-amplify/cli
```

- Configure your AWS profile:

```
amplify configure
```

- Configure your Android project. For more details about how to configure just follow the steps describe here: https://docs.amplify.aws/lib/project-setup/create-application/q/platform/android

# Samples

## 🔐 Authentication

This sample application covers the following features:

- Sign in
- Sign up
- Forgot password (reset your password)

![Alt text](images/1_authentication.png?raw=true "AWS Amplify - Android samples")

### Installation

- Clone the project app
- Follow the previous AWS Amplify installation steps
- Add the auth to your project. Behind the scenes, you are going to tell the CLI to prepare the resources are need to deploy to AWS to manage the authentication features.

```
amplify add auth

Note: use the default configuration and email for the sign in
```

- Push your changes to AWS

```
amplify push
```