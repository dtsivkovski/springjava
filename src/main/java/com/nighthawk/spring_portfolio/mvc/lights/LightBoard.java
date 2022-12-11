package com.nighthawk.spring_portfolio.mvc.lights;

public class LightBoard {
    private Light[][] lights;

    /* Initialize LightBoard and Lights */
    public LightBoard(int numRows, int numCols) {
        this.lights = new Light[numRows][numCols];
        // 2D array nested loops, used for initialization
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                lights[row][col] = new Light();  // each cell needs to be constructed
            }
        }
    }

    /* Output is intended for API key/values */
    public String toString() { 
        String outString = "[";
        // 2D array nested loops, used for reference
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                outString += 
                // data
                "{" + 
                "\"row\": " + row + "," +
                "\"column\": " + col + "," +
                "\"light\": " + lights[row][col] +   // extract toString data
                "}," ;
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString = outString.substring(0,outString.length() - 1) + "]";
		return outString;
    }

    /* Output is intended for Terminal, effects added to output */
    public String toTerminal() { 
        String outString = "[";
        // 2D array nested loops, used for reference
        for (int row = 0; row < lights.length; row++) {
            for (int col = 0; col < lights[row].length; col++) {
                outString += 
                // reset
                "\033[m" +
                
                // color
                "\033[38;2;" + 
                lights[row][col].getRed() + ";" +  // set color using getters
                lights[row][col].getGreen() + ";" +
                lights[row][col].getBlue() + ";" +
                lights[row][col].getEffect() + "m" +
                // data, extract custom getters
                "{" +
                "\"" + "isOn\": " + lights[row][col].isOn() +
                "," +
                "\"" + "RGB\": " + "\"" + lights[row][col].getRGB() + "\"" +
                "," +
                "\"" + "Effect\": " + "\"" + lights[row][col].getEffectTitle() + "\"" +
                "}," +
                // newline
                "\n" ;
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString = outString.substring(0,outString.length() - 2) + "\033[m" + "]";
		return outString;
    }

    /* Output is intended for Terminal, draws color palette */
    public String toColorPalette() {
        // block sizes
        final int ROWS = 5;
        final int COLS = 10;

        // Build large string for entire color palette
        String outString = "";
        // find each row
        for (int row = 0; row < lights.length; row++) {
            // repeat each row for block size
            for (int i = 0; i < ROWS; i++) {
                // find each column
                for (int col = 0; col < lights[row].length; col++) {
                    // repeat each column for block size
                    for (int j = 0; j < COLS; j++) {
                        // print single character, except at midpoint print color code
                        if (lights[row][col].isOn()) {
                        String c = (i == (int) (ROWS / 2) && j == (int) (COLS / 2) ) 
                            ? lights[row][col].getRGB()
                            : (j == (int) (COLS / 2))  // nested ternary
                            ? " ".repeat(lights[row][col].getRGB().length())
                            : " ";

                        outString += 
                        // reset
                        "\033[m" +
                        
                        // color
                        "\033[38;2;" + 
                        lights[row][col].getRed() + ";" +
                        lights[row][col].getGreen() + ";" +
                        lights[row][col].getBlue() + ";" +
                        "7m" +

                        // color code or blank character
                        c +

                        // reset
                        "\033[m";
                        }
                    }
                }
                outString += "\n";
            }
        }
        // remove last comma, newline, add square bracket, reset color
        outString += "\033[m";
		return outString;
    }

    public void lightToggle(int row, int col) {
        if (lights[row][col].isOn()) {
            lights[row][col].setOn(false);
        }
        else {
            lights[row][col].setOn(true);
        }
        System.out.println("Toggled light " + row + ", " + col + " to " + lights[row][col].isOn() + "!");
    }

    public void allOn() {
        for (int i = 0; i < lights.length; i++) {
            for (int j = 0; j < lights[i].length; j++) {
                lights[i][j].setOn(true);
            }
        }
        System.out.println("Set all lights to on!");
    }

    public void setColor(int row, int col, short r, short g, short b) {
        lights[row][col].setRGB(r,g,b);
    }
    
    static public void main(String[] args) {
        // create and display LightBoard
        LightBoard lightBoard = new LightBoard(5, 5);
        System.out.println(lightBoard);  // use toString() method
        System.out.println(lightBoard.toTerminal());
        System.out.println(lightBoard.toColorPalette());
        lightBoard.allOn();
        System.out.println(lightBoard.toColorPalette());
        lightBoard.lightToggle(2, 3);
        System.out.println(lightBoard.toColorPalette());
        short r = 255; short g = 255; short b = 255;
        lightBoard.setColor(0,0,r,g,b);
        System.out.println(lightBoard.toColorPalette());
    }
}
