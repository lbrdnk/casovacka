import { Text } from "react-native";
import { jsx as _jsx } from "react/jsx-runtime";
export default function HomeItem(props) {
  const {
    title,
    onPressHandler,
    navigation
  } = props;

  // console.log(Object.keys(props))

  return /*#__PURE__*/_jsx(Text, {
    onPress: onPressHandler,
    children: title
  });
}