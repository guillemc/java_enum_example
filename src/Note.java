
public class Note {

    public final static int MIN_NUMBER = 0;
    public final static int MAX_NUMBER = 127;

    public final static int MIN_OCTAVE = -1;
    public final static int MAX_OCTAVE = 9;

    private int midiNumber;
    private int octave;
    private String defaultName;
    private String flatName;
    private boolean preferFlatName = false;

    private final static String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    private final static char[] letters = {'C', 'D', 'E', 'F', 'G', 'A', 'B'};

    public Note(int midiNumber) {
        if (midiNumber < MIN_NUMBER || midiNumber > MAX_NUMBER) {
            throw new IllegalArgumentException("Invalid note number: " + midiNumber);
        }
        this.midiNumber = midiNumber;
        this.octave = midiNumber / 12 - 1;

        int position = midiNumber % 12;
        this.defaultName = noteNames[position];

        if (this.defaultName.length() == 2) {
            this.flatName = noteNames[position + 1] + "b";
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
            this.preferFlatName = true;
        }
        if (index == -1 ) {
            throw new IllegalArgumentException("Unrecognized note: " + noteName);
        }
        if (this.preferFlatName) {
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
            this.flatName = noteNames[index + 1] + "b";
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

    public String getFlatName() {
        return flatName;
    }


    public String getName() {
        return preferFlatName && flatName != null ? flatName : defaultName;
    }

    public String toString() {
        StringBuffer b = new StringBuffer(this.defaultName);
        if (this.flatName != null) {
            b.append("/").append(this.flatName);
        }
        b.append(" (").append(this.octave).append(")");
        b.append(" ").append(this.getMidiNumber());
        return b.toString();
    }

    public static int indexOf(String note) {
        for (int i = 0; i < noteNames.length; i++) {
            if (noteNames[i].equals(note)) {
                return i;
            }
        }
        return -1;
    }
}