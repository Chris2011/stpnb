package de.stpnb.stpnb.antlr.dto;


public class CharRange {
    private int start;
    private int stop;

    public CharRange(int start, int stop) {
        if(stop < start) {
            throw new IllegalArgumentException("Stop before start");
        }
        this.start = start;
        this.stop = stop;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }
    
    
}
