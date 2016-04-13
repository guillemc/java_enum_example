public class Test {

    public static void main(String[] args) {

        Note n;

        n = new Note(60);
        System.out.println(n);

        n = new Note("C");
        System.out.println(n);


        n = new Note(61);
        System.out.println(n);

        n = new Note("C#", 4);
        System.out.println(n);

        n = new Note("Db", 4);
        System.out.println(n);


        n = new Note(38);
        System.out.println(n);

        n = new Note("D", 2);
        System.out.println(n);


        n = new Note(111);
        System.out.println(n);

        n = new Note("D#", 8);
        System.out.println(n);

        n = new Note("Eb#", 8);
        System.out.println(n);


        n = new Note(127);
        System.out.println(n);

        n = new Note("G", 9);
        System.out.println(n);

        for (int i = 0; i < 5; i++) {
            n = Note.getRandomNote(60, 80);
            System.out.println(n);
        }

        n = new Note("C");
        System.out.println(n);

        Note m = n.plusInterval(Interval.AUG4);
        System.out.println(m);

        m = m.plusInterval(Interval.DIM5);
        System.out.println(m);

        m = m.plusInterval(Interval.m9);
        System.out.println(m);

        m = m.minusInterval(Interval.m9).minusInterval(Interval.OCTAVE);
        System.out.println(m);



        n = new Note("Bb");
        System.out.println(n.getName());

        m = n.plusInterval(Interval.m3);
        System.out.println(m.getName());


        Number number = new Number("#4");
        System.out.println(number.getName());
        System.out.println(number.getNote(new Note("C")).getName());

        number = number.plusInterval(Interval.P5);
        System.out.println(number.getName() + ", " + number.getRelativeOctave());
        System.out.println(number.getNote(new Note("C")).getName());

        number = number.minusInterval(Interval.m3);
        System.out.println(number.getName() + ", " + number.getRelativeOctave());
        System.out.println(number.getNote(new Note("C")).getName());
    }
}
