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

### Building the app

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
fastlane build_adhoc
```

#### Generate an Enterprise .ipa

```
fastlane build_enterprise
```

When prompted for the certificate password, use the password for MallardCrashApple from valentine.

When prompted for the fad-fastlane@google.com password, get the password from valentine.

### Distributing

Locate the generated .ipa and upload via App Distribution.

### Firebase CLI

#### Installing

Install the latest version of the App Distro Firebase CLI.
```
./firepit-macos
```

This script pulls down our FAD-version of the Firebase CLI and packages it into a binary. It's located at the top level of the iOS Bee Plus directory. If you don't see it, you might need to re-pull this repo.

#### Distributing

Log in to the Firebase CLI.
```
./firepit-macos login
```

##### Using fastlane

Install the App Distribution fastlane plugin.
```
fastlane install_plugins
```

Run the fastlane command from the project directory.
```
# To build and distribute an Adhoc app
fastlane fad_distribute
```

Or

```
# To build and distribute an Enterprise app
fastlane fad_distribute type:"enterprise"
```

If you encounter an error like:
```
Could not find action, lane or variable 'firebase_app_distribution'.
```
Make sure that the path to the Pluginfile from the Gemfile is correct, and that the Pluginfile contains `gem 'fastlane-plugin-firebase_app_distribution'`.


##### Using Firebase CLI

Run the app distro command. See below for command line options.
```
./firepit-macos appdistro:distribute
```

Option | Required | Description
------ | --------- | -----------
distribution-path | Yes | Path to the IPA or APK to distribute
google-services-path | Yes | Path to the GoogleService-Info.plist or google-services.json file for your Firebase app
release-notes | No | Release notes to include with this distribution
release-notes-path | No | Path to file with release notes to include with this distribution
testers | No | A comma separated list of tester emails to distribute to
testers-path | No | Path to file with a comma separated list of tester emails to distribute to
groups | No | A comma separated list of group aliases to distribute to
groups-path | No | Path to file with a comma separated list of group aliases to distribute

Example:
```
./firepit-macos appdistro:distribute --distribution-path beeplus.ipa --google-services-path GoogleService-Info.plist --release-notes "Uploading from the Firebase CLI" --groups "firebase-app-distro"
```

#### (Optional) Updating the CLI
When you run `./firepit-macos`, the npm and firebase packages get cached. If a new version of the App Distro Firebase CLI gets published, you need to run the following commands to get the updated verison.
```
rm -r ~/.cache/firebase     # clears Firebase cache
npm cache clean --force     # clears npm cache
./firepit-macos             # Re-installs the Firebase CLI binary
```

This is a temporary solution only. The Firebase CLI team is working on adding a command to do this automatically.
