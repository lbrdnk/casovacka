import { FlatList, Text } from "react-native";
import HomeItem from "./HomeItem";
import { jsx as _jsx } from "react/jsx-runtime";
export default function Home(props) {
  console.log(Object.keys(props));
  const {
    navigation
  } = props;
  return /*#__PURE__*/_jsx(FlatList, {
    data: [{
      key: "xixi",
      onPressHandler: () => {
        console.log("press");
        props.navigation.navigate("interval");
      }
    }, {
      key: "lol",
      onPressHandler: () => {
        console.log("press");
        props.navigation.navigate("interval");
      }
    }],
    renderItem: ({
      item
    }) => /*#__PURE__*/_jsx(HomeItem, {
      title: item.key,
      navigation: navigation,
      onPressHandler: item.onPressHandler
    }, item.key)
  });
}

// renderItem={({ item }) => <Text>{item.key}</Text>}
// renderItem={item => <HomeItem {...item}/>}