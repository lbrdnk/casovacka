import { View } from 'react-native';
import HomeScreen from "../gen/HomeScreen";

export default {
    title: 'HomeScreen',
    component: HomeScreen,
    args: {
        // title: 'x',
        // duration: 10,
        // repeat: 2,
        // intervals: []
    },
    decorators: [
        (Story) => (
            <View style={{ margin: 2, padding: 2, marginBottom: 60, flex: 1, borderWidth: 2 }}>
                <Story />
            </View>
        ),
    ]
};

const Template = (args) => <HomeScreen {...args} />;

export const Basic = Template.bind({});
Basic.args = {
    intervalListItems: [
        {
            id: "x",
            title: "interval",
        },
        {
            id: "y",
            title: "interval 2",
            duration: "60",
            repeat: "40",
        }
    ],
    newPressedHandler: () => console.log("save"),
}