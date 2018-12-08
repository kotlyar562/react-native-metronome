# react-native-metronome

## Getting started

`$ npm install react-native-metronome --save`

### Mostly automatic installation

`$ react-native link react-native-metronome`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-metronome` and add `RNMetronome.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMetronome.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.kotlyar562.RNMetronome.RNMetronomePackage;` to the imports at the top of the file
- Add `new RNMetronomePackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':react-native-metronome'
   project(':react-native-metronome').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-metronome/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
   ```
     compile project(':react-native-metronome')
   ```

## Usage

```javascript
import Metronome from "react-native-metronome";

...
```
