# Bee Plus

Bee Plus is a low-quality quiz app that shows a picture and allows you to guess what it is. Answers are stored in Firebase Real-time Database and checked weekly.

### Prerequisites

Building this app requires a Firebase account and cocoapod installed.

You can install cocoapods via:

```
sudo gem install cocoapods
```

### Installing

First, you'll need to install the required cocoapods:

```
pod install
```

### Distributing

#### First time setup

Install Xcode command line tools:

```
xcode-select --install
```

Install fastlane:

```
sudo gem install fastlane -NV
```

#### Generate an Adhoc .ipa

```
fastlane fad_adhoc
```

#### Generate an Enterprise .ipa

```
fastlane fad_enterprise
```

When prompted for the certificate password, use the password for MallardCrashApple from valentine.

When prompted for the fad-fastlane@google.com password, get the password from valentine.

#### Distribute

Locate the generated .ipa and upload via App Distribution.




