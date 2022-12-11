package com.nighthawk.spring_portfolio.mvc.lights;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Light {
    boolean on;
    short red;
    short green;
    short blue;
    short effect;

    /*  ANSI effects
        n	Name	Note
        0	Reset or normal	All attributes off
        1	Bold or increased intensity	As with faint, the color change is a PC (SCO / CGA) invention.[38][better source needed]
        2	Faint, decreased intensity, or dim	May be implemented as a light font weight like bold.[39]
        3	Italic	Not widely supported. Sometimes treated as inverse or blink.[38]
        4	Underline	Style extensions exist for Kitty, VTE, mintty and iTerm2.[40][41]
        5	Slow blink	Sets blinking to less than 150 times per minute
        6	Rapid blink	MS-DOS ANSI.SYS, 150+ per minute; not widely supported
        7	Reverse video or invert	Swap foreground and background colors; inconsistent emulation[42]
        8	Conceal or hide	Not widely supported.
        9	Crossed-out, or strike	Characters legible but marked as if for deletion. Not supported in Terminal.app
     */
    private final Map<Short, String> EFFECT = new HashMap<>();
    {
        // Map<"separator", not_used>
        EFFECT.put((short) 0, "Normal");
        EFFECT.put((short) 1, "Bold");
        EFFECT.put((short) 2, "Faint");
        EFFECT.put((short) 3, "Italic");
        EFFECT.put((short) 4, "Underline");
        EFFECT.put((short) 5, "Slow Blink");
        EFFECT.put((short) 6, "Fast Blink");
        EFFECT.put((short) 7, "Reverse");
        EFFECT.put((short) 8, "Conceal");
        EFFECT.put((short) 9, "Crossed_out");
    }

    /* Assign random colors and effects */
    public Light() {
        int maxColor = 255;
        int effect = 9;

        //Randomly on or off
        Random a = new Random();
        this.on = a.nextBoolean();

        // Randomly assign colors and effects
        this.red = (short) (Math.random()*(maxColor+1));
        this.green = (short) (Math.random()*(maxColor+1));
        this.blue = (short) (Math.random()*(maxColor+1));
        this.effect = (short) (Math.random()*(effect+1));
    }

    public String getEffectTitle() {
        return EFFECT.get(this.effect);
    }

    public String getRGB() {
        return ( "#" +
         String.format("%02X", this.red) +
         String.format("%02X", this.green) + 
         String.format("%02X", this.blue) 
         );
    }

    public void setRGB(short r, short g, short b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    /* toString output as key/values */
    public String toString() {
        return( "{" + 
            "\"on\": " + on + "," +
            "\"red\": " + red + "," +
            "\"green\": " +  green + "," + 
            "\"blue\": " + blue + "," +
            "\"effect\": " + "\"" + EFFECT.get(effect) + "\"" +
            "}" );
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    public short getEffect() {
        return effect;
    }

    static public void main(String[] args) {
        // create and display LightBoard
        Light light = new Light();
        System.out.println(light);  // use toString() method
    }
    

}

// Light.main(null);
