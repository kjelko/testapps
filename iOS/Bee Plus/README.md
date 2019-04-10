# Bee Plus

Bee Plus is a low-quality quiz app that shows a picture and allows you to guess what it is. Answers are stored in Firebase Real-time Database and checked weekly.

### Prerequisites

Building this app requires a Firebase account and cocoapod installed.

You can install cocoapods via:

```
sudo gem install cocoapods
```

Then, you'll need to install the required cocoapods:

```
pod install
```

### Building

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

### Distributing

Locate the generated .ipa and upload via App Distribution.

### Firebase CLI

#### Setup
Install the latest version of the App Distro Firebase CLI. This script pulls down our FAD-version of the Firebase CLI and packages it into a binary.
```
# At the top level of the iOS Bee Plus directory
./firepit-macos
```

To get an updated version of the FAD Firebase CLI, you'll need to clear the Firebase cache and the npm cache, and then re-run `./firepit-macos`. This is a temporary solution only. The Firebase CLI team is working on adding a command to do this automatically.
```
rm -r ~/.cache/firebase
npm cache clean --force
```

After that, the Firebase CLI should be installed. If you run `firebase` and don't see anything, you may need to update your PATH.

#### Uploading a distribution
Log in to the Firebase CLI.
```
firebase login
```

Run the app distro command. The `--distribution-path` and `google-services-path` options are required. Run `firebase appdistro:distribute --help` for more param options.
```
# Example: 
# firebase appdistro:distribute --distribution-path adhoc.ipa --google-services-path GoogleService-Info.plist --release-notes-path path/to/release-notes.txt --testers "mallardcrash@gmail.com,rebeccahe@google.com"

firebase appdistro:distribute
```
