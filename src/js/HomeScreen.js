import { useState } from "react";
import { Button, FlatList, View, Text, TextInput } from "react-native";

export function HomeItem(props) {

  const {
    title,
    onPressHandler,
  } = props

  // console.log(Object.keys(props))


  return (
    <View
      style={{ margin: 2, padding: 2, borderWidth: 2, }}
    >
      <Text
        onPress={onPressHandler}
      >
        {title}
      </Text>
    </View>
  )
}

export function HomeScreen(props) {

  // console.log(Object.keys(props))
  // console.log(props.intervalListItems)

  const {
    intervals,
    newPressedHandler
  } = props

  const [editMode, setEditMode] = useState(false)

  return (
    <>
      <FlatList
        // style={{ padding: 2, margin: 2, borderWidth: 2 }}
        style={{ flex: 1, borderWidth: 2, padding: 2, margin: 2, borderColor: editMode ? "magenta" : "black"}}
        data={intervals}
        renderItem={({ item }) => <HomeItem
          key={item.id}
          title={item.title}
          onPressHandler={editMode ? item.editPressedHandler : item.selectPressedHandler}
        />}
      />
      <Button title="New Interval" onPress={newPressedHandler} />
      <Button
        title={editMode ? "run mode" : "edit mode"}
        onPress={() => setEditMode(!editMode)}
      />
    </>
  )
}
