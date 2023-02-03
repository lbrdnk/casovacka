import { Button } from 'react-native';
import EditScreen from "../gen/EditScreen";

export default {
    title: 'EditScreen',
    component: EditScreen,
    args: {
        title: 'x',
        duration: 10,
        repeat: 2,
        intervals: []
    },
};

export const EmptyIntervals = args => <EditScreen {...args} />;

// export const nb = args => <Button {...args} />;