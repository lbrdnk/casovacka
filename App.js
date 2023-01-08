
// const App = import("./app/index");

// export default App;


// import { Canvas, Circle, Group, Text } from "@shopify/react-native-skia";

// export default App = () => {
//   const fontSize = 32;
//   const size = 256;
//   const r = size * 0.33;
//   return (
//     <Canvas style={{ flex: 1 }}>
//       <Group blendMode="multiply">
//         <Circle cx={r} cy={r} r={r} color="cyan" />
//         <Circle cx={size - r} cy={r} r={r} color="magenta" />
//         <Circle
//           cx={size / 2}
//           cy={size - r}
//           r={r}
//           color="yellow"
//         />
//       </Group>
//       {/* <Fill color="white" /> */}
//       <Text
//         x={0}
//         y={fontSize}
//         text="Hello World"
//         // font={font}
//       />
//     </Canvas>
//   );
// };

// import {Canvas, Text, useFont, Fill} from "@shopify/react-native-skia";
 
// export const App = () => {
//   const fontSize = 32;
//   const font = useFont(require("./Roboto-Black.ttf"), fontSize);
//   if (font === null) {
//     return null;
//   }
//   return (
//     <Canvas style={{ flex: 1 }}>
//       <Fill color="white" />
//       <Text
//         x={0}
//         y={fontSize}
//         text="Hello World"
//         font={font}
//       />
//     </Canvas>
//   );
// };

// export default App

import React, { useState } from "react";
import { Canvas, Rect, useSpring } from "@shopify/react-native-skia";
import { Button, StyleSheet } from "react-native";
 
export default function AnimationExample() {
  const [toggled, setToggled] = useState(false);
  const position = useSpring(toggled ? 100 : 0);
  return (
    <>
      <Canvas style={{ flex: 1 }}>
        <Rect x={position} y={100} width={10} height={10} color={"red"} />
      </Canvas>
      <Button title="Toggle" onPress={() => setToggled((p) => !p)} />
    </>
  );
};