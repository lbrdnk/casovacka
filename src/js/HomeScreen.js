import { useState } from "react";
import { Button, FlatList, View, Text, TextInput } from "react-native";
import HomeItem from "./HomeItem"

export default function HomeScreen(props) {

  // console.log(Object.keys(props))
  // console.log(props.intervalListItems)

  const {
    intervalListItems,
    newPressedHandler, //= () => console.log("new"),
    testText,
    onChangeTestText,
  } = props

  const [editMode, setEditMode] = useState(false)

  return (
    <>
      <FlatList
        // style={{ padding: 2, margin: 2, borderWidth: 2 }}
        style={{ flex: 1, borderWidth: 2, padding: 2, margin: 2, borderColor: editMode ? "magenta" : "black"}}
        // style={{flex: 1}}
        data={intervalListItems}
        renderItem={({ item }) => <HomeItem
          key={item.id}
          title={item.title}
          onPressHandler={editMode ? item.onEditHandler : item.onPressHandler}
        />}
      />
      <TextInput
        value={testText}
        onChangeText={onChangeTestText}
      />
      <Button title="New Interval" onPress={newPressedHandler} />
      <Button
        title={editMode ? "run mode" : "edit mode"}
        onPress={() => setEditMode(!editMode)}
      />
    </>
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