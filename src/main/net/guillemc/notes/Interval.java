package net.guillemc.notes;

public enum Interval {
    UNISON(0, 1, "Unison"),
    m2(1, 2, "Minor 2nd"),
    M2(2, 2, "Major 2nd"),
    m3(3, 3, "Minor 3rd"),
    M3(4, 3, "Major 3rd"),
    P4(5, 4, "Perfect 4th"),
    AUG4(6, 4, "Augmented 4th"),
    DIM5(6, 5, "Diminished 5th"),
    P5(7, 5, "Perfect 5th"),
    m6(8, 6, "Minor 6th"),
    M6(9, 6, "Major 6th"),
    m7(10, 7, "Minor 7th"),
    M7(11, 7, "Major 7th"),
    OCTAVE(12, 1, "Octave"),
    m9(13, 2, "Minor 9th"),
    M9(14, 2, "Major 9th"),
    m10(15, 3, "Minor 10th"),
    M10(16, 3, "Major 10th"),
    P11(17, 4, "Eleventh"),
    m13(20, 6, "Minor 13th"),
    M13(21, 6, "Major 13th");

    private final int semitones;
    private final int interval;
    private final String name;


    Interval(int semitones, int interval, String name) {
        this.semitones = semitones;
        this.interval = interval;
        this.name = name;
    }

    public int getSemitones() {
        return semitones;
    }

    public int getInterval() {
        return interval;
    }

    public String getName() {
        return name;
    }
}
