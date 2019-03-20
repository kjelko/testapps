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

After this, you'l need to open the .xcworkspace in Xcode and lastly, copy in your GoogleService-Info.plist file from Firebase

### Distributing

#### First time setup

Install fastlane:

```
sudo gem install fastlane -NV
```

Copy the team certificates & provisioning profiles to your keychain. From the project directory run:

```
fastlane match adhoc
```

and

```
fastlane match enterprise
```

#### Generate an Adhoc .ipa

```
fastlane fad_adhoc
```

#### Generate an Enterprise .ipa

```
fastlane fad_enterprise
```

#### Distribute

Locate the generated .ipa and upload via App Distribution.




