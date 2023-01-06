
// const App = import("./app/index");

// export default App;


// import React from 'react';
// import { View } from 'react-native';
// import { GLView } from 'expo-gl';

// export default function App() {
//   return (
//     <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
//       <GLView style={{ width: 300, height: 300 }} onContextCreate={onContextCreate} />
//     </View>
//   );
// }

// function onContextCreate(gl) {
//   gl.viewport(0, 0, gl.drawingBufferWidth, gl.drawingBufferHeight);
//   gl.clearColor(0, 1, 1, 1);

//   // Create vertex shader (shape & position)
//   const vert = gl.createShader(gl.VERTEX_SHADER);
//   gl.shaderSource(
//     vert,
//     `
//     void main(void) {
//       gl_Position = vec4(0.0, 0.0, 0.0, 1.0);
//       gl_PointSize = 150.0;
//     }
//   `
//   );
//   gl.compileShader(vert);

//   // Create fragment shader (color)
//   const frag = gl.createShader(gl.FRAGMENT_SHADER);
//   gl.shaderSource(
//     frag,
//     `
//     void main(void) {
//       gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0);
//     }
//   `
//   );
//   gl.compileShader(frag);

//   // Link together into a program
//   const program = gl.createProgram();
//   gl.attachShader(program, vert);
//   gl.attachShader(program, frag);
//   gl.linkProgram(program);
//   gl.useProgram(program);

//   gl.clear(gl.COLOR_BUFFER_BIT);
//   gl.drawArrays(gl.POINTS, 0, 1);

//   gl.flush();
//   gl.endFrameEXP();
// }

import { GLView } from 'expo-gl';
import Expo2DContext from 'expo-2d-context';

const onGLContextCreate = (gl) => {
  var ctx = new Expo2DContext(gl);
  ctx.translate(50, 200)
  ctx.scale(4, 4)
  ctx.fillStyle = "grey";
  ctx.fillRect(20, 40, 100, 100);
  ctx.fillStyle = "white";
  ctx.fillRect(30, 100, 20, 30);
  ctx.fillRect(60, 100, 20, 30);
  ctx.fillRect(90, 100, 20, 30);
  ctx.beginPath();
  ctx.arc(50, 70, 18, 0, 2 * Math.PI);
  ctx.arc(90, 70, 18, 0, 2 * Math.PI);
  ctx.fill(); ctx.fillStyle = "red";
  ctx.beginPath();
  ctx.arc(50, 70, 8, 0, 2 * Math.PI);
  ctx.arc(90, 70, 8, 0, 2 * Math.PI);
  ctx.fill(); ctx.strokeStyle = "black";
  ctx.beginPath();
  ctx.moveTo(70, 40);
  ctx.lineTo(70, 30);
  ctx.arc(70, 20, 10, 0.5 * Math.PI, 2.5 * Math.PI);
  ctx.stroke();
  ctx.flush();
}
function App() {
  return (
    <GLView
      style={{
        marginTop: 50, marginLeft: 50, width: 300, height: 300, backgroundColor: "cyan",
        // borderColor: "black", borderStyle: "solid"
      }}
      onContextCreate={onGLContextCreate}
    />
  );
}

export default App