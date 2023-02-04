import { View } from 'react-native';
import EditScreen from "../gen/EditScreen";

export default {
    title: 'EditScreen',
    component: EditScreen,
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

const Template = (args) => <EditScreen {...args} />;

export const AllEmpty = Template.bind({});
AllEmpty.args = {
    savePressedHandler: () => console.log("save"),
    cancelPressedHandler: () => console.log("cancel"),
}

export const Basic = Template.bind({});
Basic.args = {
    duration: "40",
    intervals: [
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
    repeat: "2",
    title: "Basic",
    savePressedHandler: () => console.log("save"),
    cancelPressedHandler: () => console.log("cancel"),
}