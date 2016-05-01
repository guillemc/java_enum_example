package net.guillemc.notes;

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

    protected static int indexOf(String name) {
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
        if (preferredInterval != 0) {
            String altName = n.getAltName();
            if (altName != null && altName.length() > 1) {
                int preferredDegree = getDegreeAtInterval(preferredInterval);
                int alt = Integer.valueOf(altName.substring(1, 2));
                if (preferredDegree == alt) {
                    n.setPreferAltName(true);
                }
            }
        }
        return n;
    }

    public Number plusSemitones(int semitones) {
        return plusSemitones(semitones, 0);
    }

    public Number plusInterval(Interval interval) {
        return plusSemitones(interval.getSemitones(), interval.getInterval());
    }

    public Number minusInterval(Interval interval) {
        return plusSemitones(-1 * interval.getSemitones(), -1 * interval.getInterval());
    }

    protected int getDegreeAtInterval(int interval) {
        int newDegree = interval >= 0 ? getDegree() + interval - 1 : getDegree() + interval + 1;
        while (newDegree > 7) {
            newDegree -= 7;
        }
        while (newDegree < 0) {
            newDegree += 7;
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

    public String getName(char preferredAccidental) {
        return altName != null && altName.charAt(0) == preferredAccidental ? altName : defaultName;
    }

    public Note getNote(Note base) {
        return base.plusSemitones(this.getSemitones() + 12*this.getRelativeOctave(), this.getDegree());
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer(this.defaultName);
        if (this.altName != null) {
            b.append("/").append(this.altName);
        }
        if (relativeOctave != 0) {

        }
        b.append(" (").append(this.relativeOctave).append(")");

        return b.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Number number = (Number) o;

        if (relativeOctave != number.relativeOctave) return false;
        return semitones == number.semitones;

    }

    @Override
    public int hashCode() {
        int result = relativeOctave;
        result = 31 * result + semitones;
        return result;
    }
}
