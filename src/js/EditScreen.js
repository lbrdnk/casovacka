import { useEffect, useState } from "react";
import { Button, FlatList, Pressable, Text, TextInput, View } from "react-native";

function IntervalsItem(props) {

    const {
        title, //= "interval",
        duration, //= 10,
        repeat, //= 3,
        pressedHandler, // = () => console.log("pressed")
    } = props

    // console.log("props")
    // console.log(props)
    // console.log(pressedHandler)

    return (
        <Pressable
            style={{ borderWidth: 2, padding: 2, margin: 2 }}
            onPress={pressedHandler}
        >
            {title && <Text>title: {title}</Text>}
            {duration && <Text>duration: {duration}</Text>}
            {repeat && <Text>repeat: {repeat}</Text>}
        </Pressable>
    )
}

function Intervals(props) {

    // TODO
    //  - new button should be container "uplevel"
    const {
        intervals,
        newPressedHandler,
    } = props

    // console.log("intervals");
    // console.log(intervals);

    return (
        <View
            style={{ flex: 1 }}
        >
            <View style={{ borderWidth: 2, padding: 2, margin: 2, alignSelf: "flex-end" }}>
                <Button title="new interval" onPress={newPressedHandler} />
            </View>
            <FlatList
                data={intervals}
                renderItem={({ item }) => <IntervalsItem {...item} />}
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
            />
        </View>
    )
}

export function EditScreen(props) {

    const {
        // currently may be undefined, [], null
        deletePressedHandler,
        newPressedHandler,
        intervals,
        savePressedHandler,

        title,
        duration,
        repeat,

        updateTitle,
        updateDuration,
        updateRepeat,
    } = props;

    const [cacheTitle, setCacheTitle] = useState(props.title || "");
    const [cacheDuration, setCacheDuration] = useState(props.duration || "");
    const [cacheRepeat, setCacheRepeat] = useState(props.repeat || "");

    // console.log("toplevel intervals")
    // console.log(intervals)

    return (
        <View
            style={{ flex: 1 }}
        >
            <Text style={{ borderWidth: 4, padding: 2, margin: 2 }}>Edit</Text>
            <TextInput
                editable
                // onChangeText={setCacheTitle}
                onChangeText={text => { setCacheTitle(text); updateTitle(text) }}
                // onEndEditing={({ nativeEvent: { text } }) => updateTitle(text)}
                // onEndEditing={(e) => console.log(e)}
                placeholder="title"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={cacheTitle}
            />
            <TextInput
                editable
                // onChangeText={setCacheDuration}
                onChangeText={text => { setCacheDuration(text); updateDuration(text) }}
                // onEndEditing={({ nativeEvent: { text } }) => updateDuration(text)}
                placeholder="duration"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={cacheDuration}
            />
            <TextInput
                editable
                onChangeText={(text) => { setCacheRepeat(text); updateRepeat(text) }}
                // onEndEditing={({ nativeEvent: { text } }) => updateRepeat(text)}
                // onBlur={({ nativeEvent: { text } }) => {console.log("blur");updateRepeat(text)}}
                placeholder="repeat"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={cacheRepeat}
            />
            <Intervals intervals={intervals} {...{ newPressedHandler }} />
        </View>
    )
}