import React, { useEffect, useRef, useState } from "react";
// import { Canvas, Fill, Paint, Group, Rect, Text, useFont, useSpring } from "@shopify/react-native-skia";
import { useValueEffect, useValue, useClockValue, useCanvas, Canvas, Group, Text, Rect, TextPath, Skia, useFont, vec, useSpring, Paint, Fill } from "@shopify/react-native-skia";
import { Button, Dimensions, Dimenstions, StyleSheet, View } from "react-native";
// following line is not parsable by shadow-cjls, hence using babel
import * as RN from "react-native";
import { Fragment as _Fragment } from "react/jsx-runtime";
import { jsx as _jsx } from "react/jsx-runtime";
import { jsxs as _jsxs } from "react/jsx-runtime";
const myFont = require("../../../assets/Roboto-Regular.ttf");

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

function HUDElement({
  x = 0
}) {
  const c = useCanvas();
  const w = c.size.current.width;
  const h = c.size.current.height;
  const mx = w / 2; // actually + 1 missing, NOT
  const my = h / 2;
  const fontSize = 32;
  // TODO avoid relative require
  const font = useFont(myFont, fontSize);
  if (font === null) {
    return null;
  }
  const rw = 30;
  const rh = h;
  const tw = font.getTextWidth("Hello World NAZDAR cauko");
  return /*#__PURE__*/_jsxs(_Fragment, {
    children: [/*#__PURE__*/_jsx(Rect, {
      x: 0,
      y: 0,
      width: rw,
      height: rh,
      color: "lightblue"
    }), /*#__PURE__*/_jsx(Text, {
      transform: [{
        rotate: Math.PI / 2 * 3
      }],
      origin: {
        x: 0,
        y: 128
      }
      // x={c.size.current.width / 2 - font.getTextWidth("Hello World") / 2}
      ,
      x: 0 - tw / 2,
      y: 128 + fontSize / 2 + fontSize // instead of - 5, I should be finding mid correctly
      ,
      text: "Hello World xix",
      font: font
    })]
  });
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

export default function HUD({
  running,
  startTime
}) {
  console.log(running, startTime);
  const [toggled, setToggled] = useState(false);
  const position = useSpring(toggled ? 100 : 0);
  const w = Dimensions.get("window").width;
  const h = Dimensions.get("window").height;
  const [redraw, setRedraw] = useState(0);
  const cv = useClockValue();
  useEffect(() => {
    console.log(cv.current);
    console.log(Object.keys(cv));
  });

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

  return /*#__PURE__*/_jsxs(View, {
    style: {
      flex: 1,
      flexDirection: "row",
      alignItems: "center",
      backgroundColor: "lightcyan"
    },
    children: [/*#__PURE__*/_jsx(Canvas, {
      style: {
        alignSelf: "stretch",
        backgroundColor: "lightyellow",
        width: 100,
        minWidth: 100
      },
      children: /*#__PURE__*/_jsx(HUDElement, {
        x: 0
      })
    }), /*#__PURE__*/_jsxs(View, {
      style: {
        flex: 1
      },
      children: [/*#__PURE__*/_jsx(Button, {
        style: {},
        title: "redraw",
        onPress: () => setRedraw(redraw + 1)
      }), /*#__PURE__*/_jsx(Button, {
        style: {},
        title: "start",
        onPress: () => cv.start()
      }), /*#__PURE__*/_jsx(Button, {
        style: {},
        title: "stop",
        onPress: () => cv.stop()
      }), /*#__PURE__*/_jsx(Button, {
        style: {},
        title: "reset",
        onPress: () => cv.current = 0
      }), /*#__PURE__*/_jsx(RN.TextInput, {
        style: {
          backgroundColor: "lightred"
          // flex: 1
        }
        // value={tr.current}
        // onChangeText={(text) => {tr.current = text; console.log(tr.current); console.log(v.current)}}
      })]
    })]
  });
}

;