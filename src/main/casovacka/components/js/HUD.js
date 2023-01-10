import React, { useEffect, useRef, useState } from "react";
// import { Canvas, Fill, Paint, Group, Rect, Text, useFont, useSpring } from "@shopify/react-native-skia";
import { useValueEffect, useValue, useClockValue, useCanvas, Canvas, Group, Text, Rect, TextPath, Skia, useFont, vec, useSpring, Paint, Fill } from "@shopify/react-native-skia";
import { Button, Dimensions, Dimenstions, StyleSheet, View } from "react-native";
import * as RN from "react-native"

const interval = {
  duration: 600,
  title: "Rope jumping",
  intervals: [
    {
      title: "Prepare",
      duration: 20
    },
    {
      repeat: 3,
      intervals: [
        {
          title: "Jump",
          duration: 150,
        },
        {
          title: "Rest",
          duration: 30,
        }
      ]
    },
    {
      title: "Cooldown"
      // compute what left to 600 -- 40
    }
  ]
}

// const hudElementDimensions = (offset, font, text) => {
//   const w = Dimensions.get("window").width
//   const h = Dimensions.get("window").height
//   const mx = w / 2
//   const my = h / 2
//   // font
//   const fontSize = 32
//   const tw = font.getTextWidth(text)
//   // rect
//   const rh = fontSize + 4
//   const rw = h
//   return null

// }

/*
  This should contain only presentation logic

  meaning timervalue and data to view
*/

// const hudElementPropsMock = {
//   text: "Timer 1",

// }

/*
  Animation logic

  first I need to build the scene

  for that i need to know:
  - Whats in the fov? Exact dimensions
    - Based on that:
      - ! draw HudElements

    this should be computed in cljs? -- some list of intervals
    or computed here in js? -- from interval structure a current time

*/

function HUDElement({ x = 0 }) {

  const c = useCanvas();

  const w = c.size.current.width
  const h = c.size.current.height
  const mx = w / 2 // actually + 1 missing, NOT
  const my = h / 2

  const fontSize = 32;
  // TODO avoid relative require
  const font = useFont(require("../../../../../assets/Roboto-Black.ttf"), fontSize);
  if (font === null) {
    return null;
  }
  const rw = 30
  const rh = h

  const tw = font.getTextWidth("Hello World")

  return (
    <>
      <Rect x={0} y={0} width={rw} height={rh} color="lightblue" />
      <Text
        transform={[{ rotate: Math.PI / 2 * 3 }]}
        origin={{ x: 0, y: 128 }}
        // x={c.size.current.width / 2 - font.getTextWidth("Hello World") / 2}
        x={0 - tw / 2}
        y={128 + fontSize / 2 + fontSize} // instead of - 5, I should be finding mid correctly
        text="Hello World"
        font={font}
      />
    </>
  )
}

/*
  what the hud input?
  - fading colors

  Scene is constructed from lanes

  Lane has some minimal width

  Lane contains IntervalRects

  Lanes have priorities
  - innermost named interval is used always
  - then with zoom or scroll containing intervals are added inside out

*/

export default function HUD() {
  const [toggled, setToggled] = useState(false);
  const position = useSpring(toggled ? 100 : 0);
  const w = Dimensions.get("window").width
  const h = Dimensions.get("window").height

  const [redraw, setRedraw] = useState(0)

  const cv = useClockValue()
  useEffect(() => {
    console.log(cv.current)
    console.log(Object.keys(cv))
  })

  // const kokocit = useValue(10);
  // console.log(Object.keys(kokocit))

  // const tr = useRef("")
  // console.log("rere")

  // const rr = useRef()
  // const v = useValue(0.1);

  // useEffect(() => {
  //   const kokocit = () => {
  //     rr.current = requestAnimationFrame(kokocit)
  //     v.current = (tr.current.length % 10.0) / 10
  //   }
  //   rr.current = requestAnimationFrame(kokocit)
  //   return () => cancelAnimationFrame(rr.current)
  // }, [])

  // useValueEffect(v, () => {

  // })

  return (
    <View style={{ flex: 1, flexDirection: "row", alignItems: "center", backgroundColor: "lightcyan" }}>
      <Canvas style={{ alignSelf: "stretch", backgroundColor: "lightyellow", width: 100, minWidth: 100 }}>
        <HUDElement x={0} />
        <HUDElement x={1} />
      </Canvas>
      <View style={{flex: 1}}>
        <Button style={{}} title="redraw" onPress={() => setRedraw(redraw + 1)} />
        <Button style={{}} title="start" onPress={() => cv.start()} />
        <Button style={{}} title="stop" onPress={() => cv.stop()} />
        <Button style={{}} title="reset" onPress={() => cv.current = 0} />
        <RN.TextInput
          style={{
            backgroundColor: "lightgreen",
            // flex: 1
          }}
          // value={tr.current}
          // onChangeText={(text) => {tr.current = text; console.log(tr.current); console.log(v.current)}}
          
        />
      </View>
    </View>
  );
};