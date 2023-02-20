import { useEffect, useRef, useState } from "react";
import { Button, View, Text, Animated } from "react-native";

const intervalMs = 100;

export function Timer(props) {

  const {
    running,
    startEpoch,
    totalMs,

    timerStoppedHandler,

    timeStrFormatFn,
    startPressedHandler,
    stopPressedHandler,
    resetPressedHandler
  } = props

  const [deltaMs, setDeltaMs] = useState(0);

  const intervalId = useRef(-1);

  useEffect(() => {
    function runTimer() {
      setDeltaMs(Date.now() - startEpoch)
    }
    if (running && intervalId.current === -1) {
      intervalId.current = setInterval(runTimer, intervalMs);
    } else if (!running) {
      clearInterval(intervalId.current);
      intervalId.current = -1;
      setDeltaMs(0);
    }
    return () => {
      clearInterval(intervalId.current);
      intervalId.current = -1;
      setDeltaMs(0);
    }
  }, [running, startEpoch])

  const totalCurMs = totalMs + deltaMs;

  const timeStr = timeStrFormatFn(totalCurMs);

  return (
    <View>
      <Text style={{ fontVariant: ['tabular-nums'] }}>{timeStr}</Text>
      {
        running ? (
          <Button title="stop" onPress={() => stopPressedHandler(deltaMs)} />
        ) : (
          <Button title="start" onPress={startPressedHandler} />
        )
      }

      <Button title="reset" onPress={resetPressedHandler} />
    </View>
  )
}

export function IntervalScreen(props) {

  // console.log(Object.keys(props))
  // console.log(props.title)

  const {
    title, //  = "interval title",
    timeStr = "00:00:01.12", // 00:00:01.12
    running, // = false, // also should not be here?
    startEpoch, // should not be here !!!
    startPressedHandler, // = () => console.log("start"),
    stopPressedHandler, // = () => console.log("stop"),
    resetPressedHandler, // = () => console.log("reset")
  } = props;

  const timerProps = {
    timeStr,
    running,
  }

  useEffect(() => {
    // console.log(running)
    // console.log("cic")
  }, [running])

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center"
      }}>
      <Text>{title}</Text>
      <Timer
        {...props}
      />
    </View>
  )
}