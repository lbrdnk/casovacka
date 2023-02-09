import { View, Text } from "react-native";

export default function HomeItem(props) {

  const {
    title,
    onPressHandler,
    navigation
  } = props

  // console.log(Object.keys(props))

  return (
    <View
      style={{ margin: 2, padding: 2, borderWidth: 2 }}
    >
      <Text
        onPress={onPressHandler}
      >
        {title}
      </Text>
    </View>
  )
}