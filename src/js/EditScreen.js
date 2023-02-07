import { useState } from "react";
import { Button, FlatList, Text, TextInput, View } from "react-native";

function IntervalsItem(props) {

    const {
        title, //= "interval",
        duration, //= 10,
        repeat, //= 3,
        pressedHandler = () => console.log("pressed")
    } = props

    // console.log("props")
    // console.log(props)

    return (
        <View
            style={{ borderWidth: 2, padding: 2, margin: 2 }}
            onPress={pressedHandler}
        >
            {title && <Text>title: {title}</Text>}
            {duration && <Text>duration: {duration}</Text>}
            {repeat && <Text>repeat: {repeat}</Text>}
        </View>
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

export default function EditScreen(props) {

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

    // const [title, setTitle] = useState(props.title || "");
    // const [duration, setDuration] = useState(props.duration || "");
    // const [repeat, setRepeat] = useState(props.repeat || "");

    // console.log("toplevel intervals")
    // console.log(intervals)

    return (
        <View
            style={{ flex: 1 }}
        >
            <Text>Edit!</Text>
            <TextInput
                editable
                onChangeText={updateTitle}
            //     onEndEditing={ //(any) => {/*console.log(any.nativeEvent.text);*/ /*updateTitle(any.nativeEvent.text);}*/
            //         updateTitle
            // }
                placeholder="title"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={title}
            />
            <TextInput
                editable
                onChangeText={updateDuration}
                // onEndEditing={updateDuration}
                placeholder="duration"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={duration}
            />
            <TextInput
                editable
                onChangeText={updateRepeat}
                // onEndEditing={updateRepeat}
                placeholder="repeat"
                style={{ borderWidth: 2, padding: 2, margin: 2 }}
                value={repeat}
            />
            <Intervals intervals={intervals} {...{newPressedHandler}} />
            <View style={{
                flexDirection: "row",
                justifyContent: "space-between",
                marginTop: 2,
                padding: 2,
            }}>
                <View style={{ borderWidth: 2, }}>
                    <Button
                        title="delete"
                        onPress={deletePressedHandler}
                    />
                </View>
                <View style={{ flexDirection: "row" }}>
                    {/*                     
                    <View style={{ borderWidth: 2, }}>
                        <Button title="cancel" onPress={cancelPressedHandler} />
                    </View>
                     */}
                    <View style={{ borderWidth: 2, marginLeft: 4 }}>
                        <Button
                            title="save all and exit"
                            onPress={savePressedHandler}
                        />
                    </View>
                </View>
            </View>
        </View>
    )
}