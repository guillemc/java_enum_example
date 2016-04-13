public class Number {

    private int relativeOctave;
    private int semitones;
    private String defaultName;
    private String altName;
    private boolean preferAltName = false;

    private final static String[] defaultNames = {"1", "#1", "2", "#2", "3", "4", "#4", "5", "#5", "6", "#6", "7"};

    public Number(String name, int relativeOctave) {
        int l = name.length();
        int index;
        if (l == 1 || name.charAt(0) == '#') {
            index = indexOf(name);
        } else { // assume it's a flat
            index = indexOf(String.valueOf(name.charAt(1)));
            this.preferAltName = true;
        }
        if (index == -1 ) {
            throw new IllegalArgumentException("Unrecognized number: " + name);
        }
        if (this.preferAltName) {
            index = index - 1;
            if (index < 0) index = defaultNames.length - 1;
        }
        this.defaultName = defaultNames[index];
        this.relativeOctave = relativeOctave;
        if (this.defaultName.length() == 2) {
            this.altName = "b" + defaultNames[index + 1];
        }
        this.semitones = index;
    }

    public Number(String name) {
        this(name, 0);
    }

    public int getDegree() {
        if (defaultName.length() == 1) {
            return Integer.valueOf(defaultName);
        } else {
            if (altName != null && preferAltName) {
                return Integer.valueOf(altName.substring(1, 2));
            } else {
                return Integer.valueOf(defaultName.substring(1, 2));
            }
        }
    }

    private static int indexOf(String name) {
        for (int i = 0; i < defaultNames.length; i++) {
            if (defaultNames[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public Number plusSemitones(int semitones, int preferredInterval) {
        int relativeOctave = this.relativeOctave;
        int newIndex = this.semitones + semitones;
        int l = defaultNames.length;
        while (newIndex > l - 1) {
            newIndex -= l;
            relativeOctave += 1;
        }
        while (newIndex < 0) {
            newIndex += l;
            relativeOctave -= 1;
        }
        Number n = new Number(defaultNames[newIndex], relativeOctave);
        String altName = n.getAltName();
        if (altName != null && altName.length() > 1) {
            int preferredDegree = getDegreeAtInterval(getDegree(), preferredInterval);
            int alt = Integer.valueOf(altName.substring(1,2));
            if (preferredDegree == alt) {
                n.setPreferAltName(true);
            }
        }
        return n;

    }

    public Number plusInterval(Interval interval) {
        return plusSemitones(interval.getSemitones(), interval.getInterval());
    }

    public Number minusInterval(Interval interval) {
        return plusSemitones(-1 * interval.getSemitones(), interval.getInterval());
    }

    public static int getDegreeAtInterval(int degree, int interval) {
        int newDegree = degree + interval - 1;
        while (newDegree > 7) {
            newDegree -= 7;
        }
        return newDegree;
    }

    public int getRelativeOctave() {
        return relativeOctave;
    }

    public int getSemitones() {
        return semitones;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public String getAltName() {
        return altName;
    }

    public void setPreferAltName(boolean preferAltName) {
        this.preferAltName = preferAltName;
    }

    public String getName() {
        return preferAltName && altName != null ? altName : defaultName;
    }

    public Note getNote(Note base) {
        return base.plusSemitones(this.getSemitones() + 12*this.getRelativeOctave(), this.getDegree());
    }
}
