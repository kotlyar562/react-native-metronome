# react-native-metronome

# NOTE! For now supported only Android!

## Getting started

Add to package.json dependencies line
`"react-native-metronome": "git://github.com/kotlyar562/react-native-metronome.git"`

### Mostly automatic installation

`$ react-native link react-native-metronome`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-metronome` and add `RNMetronome.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNMetronome.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.metronome.RNMetronomePackage;` to the imports at the top of the file
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
4. Important! Copy [sounds](https://github.com/kotlyar562/react-native-metronome/tree/master/example/android/app/src/main/res/raw) to folder `android/app/src/main/java/res/raw`

## Usage

```javascript
import Metronome from "react-native-metronome";

...

Metronome.play(80); //play with 80 bpm

Metronome.nextSound(); //play next sound

Metronome.stop(); // stop playing

Metronome.isPlay(status => {
  console.log(status);
})

...
```

_If you change bpm, call play() again._

**Show [example](https://github.com/kotlyar562/react-native-metronome/tree/master/example) for more info**
