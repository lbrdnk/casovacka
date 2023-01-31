import { FlatList, Text } from "react-native";
import HomeItem from "./HomeItem"

export default function HomeScreen(props) {

  // console.log(Object.keys(props))
  // console.log(props.intervalListItems)

  const {
    navigation,
    intervalListItems
  } = props

  return (
    <FlatList
      data={intervalListItems}
      renderItem={({ item }) => <HomeItem 
        key={item.id}
        title={item.title}
        onPressHandler={item.onPressHandler}
      />}
    />
  )
}

// renderItem={({ item }) => <HomeItem
// key={item.id}
// title={item.title}
// onPressHandler={item.onPressHandler}


// [
//   {
//     key: "xixi",
//     onPressHandler: () => {console.log("press"); props.navigation.navigate("interval")}
//   },
//   {
//     key: "lol",
//     onPressHandler: () => {console.log("press"); props.navigation.navigate("interval")}
//   }
// ]

// renderItem={({ item }) => <Text>{item.key}</Text>}
// renderItem={item => <HomeItem {...item}/>}