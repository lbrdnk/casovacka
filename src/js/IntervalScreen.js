import { View, Text } from "react-native";

export default function IntervalScreen(props) {
  
  // console.log(Object.keys(props))
  // console.log(props.title)

  const {
    title
  } = props;

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center"
      }}>
      <Text>{title}</Text>
    </View>
  )
}