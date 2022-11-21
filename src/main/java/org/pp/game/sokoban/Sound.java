package org.pp.game.sokoban;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public static final String CLASSPATH = ClassLoader.getSystemClassLoader().getResource("").getFile();

    String path = CLASSPATH + "/static/img/";
    String file = "nor.mid";
    Sequence seq;
    Sequencer midi;
    boolean sign;

    //加载音乐
    public void loadSound() {
        try {
            seq = MidiSystem.getSequence(new File(path + file));
            midi = MidiSystem.getSequencer();
            midi.open();
            midi.setSequence(seq);
            midi.start();
            midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);

        } catch (InvalidMidiDataException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sign = true;
    }


    void mystop() {
        midi.stop();
        midi.close();
        sign = false;
    }

    boolean isplay() {
        return sign;
    }

    void setMusic(String e) {
        file = e;
    }

}


