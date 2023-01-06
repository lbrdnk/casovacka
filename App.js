
// const App = import("./app/index");

// export default App;

// import { StatusBar } from 'expo-status-bar';
// import { StyleSheet, Text, View } from 'react-native';

// export default function App() {
//   return (
//     <View style={styles.container}>
//       <Text>Open up App.js to start working on your app!</Text>
//       <StatusBar style="auto" />
//     </View>
//   );
// }

// const styles = StyleSheet.create({
//   container: {
//     flex: 1,
//     backgroundColor: '#fff',
//     alignItems: 'center',
//     justifyContent: 'center',
//   },
// });

// import React from 'react';
// import { View } from 'react-native';
import { GLView } from 'expo-gl';

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
// import { GLView } from 'expo';
import Expo2DContext from 'expo-2d-context';
import React from 'react'; export default class App extends React.Component {
  render() {
    return (
      <GLView
        style={{ flex: 1 }}
        onContextCreate={this._onGLContextCreate}
      />
    );
  }
  _onGLContextCreate = (gl) => {
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
    ctx.fill(); ctx.fillStyle = "grey";
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
}