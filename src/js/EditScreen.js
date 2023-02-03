import { useState } from "react";
import { Button, FlatList, Text, TextInput, View } from "react-native";

function IntervalsItem(props) {

    const {
        title = "interval",
        duration = 10,
        repeat = 3,
        pressedHandler = () => console.log("pressed")
    } = props

    return (
        <View
            style={{ borderWidth: 2, padding: 2, margin: 2 }}
            onPress={pressedHandler}
        >
            {title && <Text>title: {title}</Text>}
            {title && <Text>duration: {duration}</Text>}
            {title && <Text>repeat: {repeat}</Text>}
        </View>
    )
}

// todo into storybook
const items = [
    {
        id: "xixi",
        title: "x",
    },
    {
        id: "y",
        title: "y",
    }
]

function Intervals(props) {

    return (
        <View
            style={{ flex: 1 }}
        >
            <View style={{ borderWidth: 2, padding: 2, margin: 2, alignSelf: "flex-end" }}>
                <Button title="old interval" />
            </View>
            <FlatList
                data={items}
                renderItem={({ item }) => <IntervalsItem {...item} />}
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
            />
        </View>
    )
}

export default function EditScreen(props) {

    const [title, setTitle] = useState("");
    const [duration, setDuration] = useState("");
    const [repeat, setRepeat] = useState("");

    return (
        <View
            style={{ flex: 1 }}
        >
            <Text>Edit!</Text>
            <TextInput
                editable
                onChangeText={setTitle}
                placeholder="title"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={title}
            />
            <TextInput
                editable
                onChangeText={setDuration}
                placeholder="duration"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={duration}
            />
            <TextInput
                editable
                onChangeText={setRepeat}
                placeholder="repeat"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={repeat}
            />
            <Intervals />
        </View>
    )
}