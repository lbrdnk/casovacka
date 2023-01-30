import { Text } from "react-native";

export default function HomeItem(props) {

  const {
    title,
    onPressHandler,
    navigation
  } = props

  // console.log(Object.keys(props))

  return (
    <Text
      onPress={onPressHandler}
    >
      {title}
    </Text>
  )
}