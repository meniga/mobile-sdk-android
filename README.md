![Meniga](https://github.com/meniga/mobile-sdk-ios/raw/master/logo.png)

Meniga helps banks use data to accelerate innovation and meaningful engagement by providing RESTful APIs on top of Meniga software products to improve and personalize the online banking experience for both individuals and corporate customers.

## Getting started
Developers for institutions implementing the Meniga REST API can usi this sdk to communicate easily with the sdk and perform api operations. Please follow the setup guide in the documentation for further detail. Individuals making personalized implementations can point the sdk to their bank or financial institution's API URL to use their own data. You must however obtain some authentication information and pass it along to the sdk. See the documentation for further detail.

## Installation

# Dependency

```groovy
dependencies {

  compile 'com.meniga.sdk:1.0.0'

}
```

You also have to add the url to the snapshot repository:

```gradle
allprojects {
  repositories {
    ...

    maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
}
```

# Changelog
The changelog can be found in the [release section](https://github.com/meniga/mobile-sdk-android/releases)

## License
The Meniga SDK for Android is published under the MIT license.