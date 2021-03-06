
# react-native-crop-image

## Getting started

`$ yarn add github:Radweb/react-native-crop-image`

### Mostly automatic installation

`$ react-native link react-native-crop-image`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-crop-image` and add `RNCropImage.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNCropImage.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.radweb.cropimage.RNCropImagePackage;` to the imports at the top of the file
  - Add `new RNCropImagePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-crop-image'
  	project(':react-native-crop-image').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-crop-image/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-crop-image')
  	```


## Usage
```javascript
import crop from 'react-native-crop-image';

await crop(diskPathToImage, { 
  width: 100, // Desired width to crop to
  height: 100, // Desired height to crop to
  left: 50, // Offset from the left of the image
  top: 50, // Offset from the top of the image
})
```

### `crop(path: string, cropData: CropData): Promise<string>`
- `path` - The native path to the file (so no leading `file://`, no image store path, etc)
- `cropData` - Defines the crop area within the coordinate space of the image. `width` and `height` are required, `left` and `top` default to 0

- **Returns** - Path to cropped image, usable as the uri in `Image` component sources. Located in a temp directory on ios
