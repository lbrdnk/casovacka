import { Button, View, Text } from "react-native";

export function IntervalScreen(props) {

  // console.log(Object.keys(props))
  // console.log(props.title)

  const {
    title = "interval title",
    timeStr = "00:00:01.12", // 00:00:01.12
    running = false, // also should not be here?
    startEpoch, // should not be here !!!
    startPressedHandler = () => console.log("start"),
    stopPressedHandler = () => console.log("stop"),
    resetPressedHandler = () => console.log("reset")
  } = props;

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center"
      }}>
      <Text>{title}</Text>
      <Text style={{fontVariant: ['tabular-nums']}}>{timeStr}</Text>
      {
        running ? (
          <Button title="stop" onPress={stopPressedHandler} />
        ) : (
          <Button title="start" onPress={startPressedHandler} />
        )
      }

      <Button title="reset" onPress={resetPressedHandler} />
    </View>
  )
}