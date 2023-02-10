import { useEffect, useRef, useState } from "react";
import { Button, View, Text, Animated } from "react-native";

export function Timer(props) {

  const {
    running,
    // startEpoch,

    totalMs,

    timeStrFormatFn,
    startPressedHandler,
    stopPressedHandler,
    resetPressedHandler
  } = props

  

  // tmp
  const startEpoch = 0;

  const rafId = useRef(0);

  // const nowEpochRef = useRef(0);

  const [delta, setDelta] = useState(0);

  // const [nowEpoch, setNowEpoch] = useState(0)

  function tick() {
    setDelta(Date.now() - startEpoch)
    rafId.current = requestAnimationFrame(tick)
  }

  useEffect(() => {
    if (running) {
      tick()
    } else {
      cancelAnimationFrame(rafId.current);
    }
    // console.log(running)
    return () => {
      cancelAnimationFrame(rafId.current);
    }
  }, [running])

  const timeStr = delta;

  return (
    <View>
      <Text style={{ fontVariant: ['tabular-nums'] }}>{timeStr}</Text>
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
    console.log(running)
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