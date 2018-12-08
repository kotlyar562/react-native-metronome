/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, { Component } from "react";
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  TextInput
} from "react-native";
import Metronome from "react-native-metronome";

export default class App extends Component {
  state = {
    bpm: 80,
    soundNumber: 0
  };

  changeBpm = bpm => {
    this.setState({ bpm: +bpm });
  };

  play = () => {
    Metronome.play(this.state.bpm);
  };

  nextSound = () => {
    Metronome.nextSound();
  };

  stop = () => {
    Metronome.stop();
  };
  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={this.play}>
          <Text>Play</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.nextSound}>
          <Text>Next Sound</Text>
        </TouchableOpacity>
        <TextInput value={"" + this.state.bpm} onChangeText={this.changeBpm} />
        <TouchableOpacity onPress={this.stop}>
          <Text>Stop</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "space-around",
    alignItems: "center",
    backgroundColor: "#F5FCFF"
  }
});
