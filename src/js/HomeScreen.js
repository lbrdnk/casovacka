import { FlatList, Text } from "react-native";
import HomeItem from "./HomeItem"

export default function HomeScreen(props) {

  console.log(Object.keys(props))

  const {
    navigation
  } = props

  return (
    <FlatList
      data={[
        {
          key: "xixi",
          onPressHandler: () => {console.log("press"); props.navigation.navigate("interval")}
        },
        {
          key: "lol",
          onPressHandler: () => {console.log("press"); props.navigation.navigate("interval")}
        }
      ]}
      renderItem={({ item }) => <HomeItem
        key={item.key}
        title={item.key}
        navigation={navigation}
        onPressHandler={item.onPressHandler}
      />}
    />
  )
}

// renderItem={({ item }) => <Text>{item.key}</Text>}
// renderItem={item => <HomeItem {...item}/>}