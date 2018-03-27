![Meniga](https://github.com/meniga/mobile-sdk-ios/raw/master/logo.png)

mobile-sdk-android
====
[![Download](https://api.bintray.com/packages/meniga/android/com.meniga.sdk/images/download.svg) ](https://bintray.com/meniga/android/com.meniga.sdk/_latestVersion)
[![Build Status](https://travis-ci.org/meniga/mobile-sdk-android.svg?branch=master)](https://travis-ci.org/meniga/mobile-sdk-android)

Meniga helps banks use data to accelerate innovation and meaningful engagement by providing RESTful APIs on top of Meniga software products to improve and personalize the online banking experience for both individuals and corporate customers.

## Getting started
Developers for institutions implementing the Meniga REST API can use this sdk to communicate easily with the SDK and perform API operations. Please follow the setup guide in the documentation for further detail. Individuals making personalized implementations can point the SDK to their bank or financial institution's API URL to use their own data. You must however obtain some authentication information and pass it along to the SDK. See the documentation for further detail.

## Basic usage

```
repositories {
  jcenter()
}

dependencies {
  compile 'com.meniga.sdk:sdk-android:x.y.z'
}
```

For the latest release version code, check out https://github.com/meniga/mobile-sdk-android/releases

## Snapshot usage

```
repositories {
  maven { url 'https://oss.jfrog.org/artifactory/oss-snapshot-local' }
}

dependencies {
  compile 'com.meniga.sdk:sdk-android:x.y.z-SNAPSHOT'
}
```

For more information please refer to [list of snapshots](https://oss.jfrog.org/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/com/meniga/sdk/sdk-android).

## Local development

In order to use an SDK that contains local changes you may start by checking the version as shown below.

```
$ ./gradlew currentVersion

> Task :currentVersion 

Project version: x.y.z-SNAPSHOT
```

The next step is to publish the artifact to the local Maven repository.

```
$ ./gradlew publishToMavenLocal
```

In the Android Application project add the missing local Maven repository and adjust the version of the artifact.

```
repositories {
  mavenLocal()
  
  ...
}

dependencies {
  compile 'com.meniga.sdk:sdk-android:x.y.z-SNAPSHOT'
}
```

## Changelog
The changelog can be found in the [release section](https://github.com/meniga/mobile-sdk-android/releases)

## License
The Meniga SDK for Android is published under the [MIT license](LICENSE.txt).
