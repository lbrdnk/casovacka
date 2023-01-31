// Learn more https://docs.expo.io/guides/customizing-metro
const { getDefaultConfig } = require('expo/metro-config');

const defaultConfig = getDefaultConfig(__dirname);

defaultConfig.resolver.resolverMainFields = [
    "sbmodern",
    ...defaultConfig.resolver.resolverMainFields,
  ];
  
  defaultConfig.transformer.getTransformOptions = async () => ({
    transform: {
      experimentalImportSupport: false,
      inlineRequires: false,
    },
  });
  
  // do i need following?
//   defaultConfig.watchFolders = [...defaultConfig.watchFolders, "./.ondevice"];
  
  module.exports = defaultConfig;

//   module.exports = {
//     /* existing config */
//     resolver: {
//         resolverMainFields: ['sbmodern', 'react-native', 'browser', 'main'],
//     },
// };