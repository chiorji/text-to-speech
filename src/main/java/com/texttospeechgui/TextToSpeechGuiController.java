package com.texttospeechgui;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class TextToSpeechGuiController {
    private static final VoiceManager voiceManager = VoiceManager.getInstance();

    public static ArrayList<String> getVoices() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        ArrayList<String> voices = new ArrayList<>();
        for (Voice voice : voiceManager.getVoices()) {
            voices.add(voice.getName());
        }

        return voices;
    }


    public static ArrayList<String> getSpeedRates() {
        String[] rates = {"60", "100", "140", "200", "170"};
        return new ArrayList<>(Arrays.asList(rates));
    }

    public static ArrayList<String> getVolumeLevels() {
        ArrayList<String> volumes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            volumes.add(Integer.toString(i));
        }
        return volumes;
    }

    public static void speak(String msg, String voiceType, String rate, String volume) {
        Voice voice = voiceManager.getVoice(voiceType);
        if(voice == null) {
            System.err.println("Cannot find voice " + voiceType);
            System.exit(1);
        }

        voice.allocate();

        voice.setRate(Integer.parseInt(rate));
        voice.setVolume(Integer.parseInt(volume));

        voice.speak(msg);

        voice.deallocate();
    }
}