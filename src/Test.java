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
    }
}
