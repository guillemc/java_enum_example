
public class Note {

    public final static int MIN_NUMBER = 0;
    public final static int MAX_NUMBER = 127;

    public final static int MIN_OCTAVE = -1;
    public final static int MAX_OCTAVE = 9;

    private int midiNumber;
    private int octave;
    private String defaultName;
    private String altName;
    private boolean preferAltName = false;

    private final static String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private final static String letters = "CDEFGAB";

    public Note(int midiNumber) {
        if (midiNumber < MIN_NUMBER || midiNumber > MAX_NUMBER) {
            throw new IllegalArgumentException("Invalid note number: " + midiNumber);
        }
        this.midiNumber = midiNumber;
        this.octave = midiNumber / 12 - 1;

        int position = midiNumber % 12;
        this.defaultName = noteNames[position];

        if (this.defaultName.length() == 2) {
            this.altName = noteNames[position + 1] + "b";
        }
    }

    public Note(String noteName, int octave) {
        noteName = noteName.toUpperCase();
        int l = noteName.length();
        int index;
        if (l == 1 || noteName.charAt(1) == '#') {
            index = indexOf(noteName);
        } else { // assume it's a flat
            index = indexOf(String.valueOf(noteName.charAt(0)));
            this.preferAltName = true;
        }
        if (index == -1 ) {
            throw new IllegalArgumentException("Unrecognized note: " + noteName);
        }
        if (this.preferAltName) {
            index = index - 1;
            if (index < 0) index = noteNames.length - 1;
        }
        int midiNumber = (octave + 1) * noteNames.length + index;
        if (midiNumber < MIN_NUMBER || midiNumber > MAX_NUMBER) {
            throw new IllegalArgumentException("Note/octave combination is out of range: " + noteName + "/" + octave);
        }
        this.midiNumber = midiNumber;
        this.octave = octave;
        this.defaultName = noteNames[index];
        if (this.defaultName.length() == 2) {
            this.altName = noteNames[index + 1] + "b";
        }
    }

    public Note(String noteName) {
        this(noteName, 4);
    }


    public int getMidiNumber() {
        return midiNumber;
    }

    public int getOctave() {
        return octave;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getAltName() {
        return altName;
    }

    public String getName() {
        return preferAltName && altName != null ? altName : defaultName;
    }

    public void setPreferAltName(boolean preferAltName) {
        this.preferAltName = preferAltName;
    }

    public String toString() {
        StringBuffer b = new StringBuffer(this.defaultName);
        if (this.altName != null) {
            b.append("/").append(this.altName);
        }
        b.append(" (").append(this.octave).append(")");
        b.append(" ").append(this.getMidiNumber());
        return b.toString();
    }

    private static int indexOf(String note) {
        for (int i = 0; i < noteNames.length; i++) {
            if (noteNames[i].equals(note)) {
                return i;
            }
        }
        return -1;
    }

    public static Note getRandomNote(int minNumber, int maxNumber) {
        return new Note(minNumber + 1 + (int)((maxNumber - minNumber) * Math.random()));
    }

    public Note plusSemitones(int semitones, int preferredInterval) {
        Note note = new Note(this.midiNumber + semitones);
        String altName = note.getAltName();
        if (altName != null && getLetterAtInterval(getName().charAt(0), preferredInterval) == altName.charAt(0)) {
            note.setPreferAltName(true);
        }
        return note;
    }

    public Note plusInterval(Interval interval) {
        return plusSemitones(interval.getSemitones(), interval.getInterval());
    }

    public Note minusInterval(Interval interval) {
        return plusSemitones(-1 * interval.getSemitones(), interval.getInterval());
    }

    public static char getLetterAtInterval(char letter, int interval) {
        int currentIndex = letters.indexOf(letter);
        if (currentIndex == -1) {
            throw new IllegalArgumentException("Invalid note letter");
        }
        int newIndex = currentIndex + interval - 1;
        int l = letters.length();
        while (newIndex > l - 1) {
            newIndex -= l;
        }
        while (newIndex < 0) {
            newIndex += l;
        }
        return letters.charAt(newIndex);
    }
}