package net.guillemc.notes;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NoteTest {

    Note c;
    Note fSharp;
    Note a;
    Note bFlat;

    Note c5;
    Note fSharp5;
    Note a5;
    Note bFlat5;

    @BeforeMethod
    public void setUp() throws Exception {
        c = new Note("C"); // default octave is 4
        fSharp = new Note("F#", 4);
        a = new Note("a"); // case-insensitive
        bFlat = new Note("Bb", 4);

        c5 = new Note(72);
        fSharp5 = new Note(78);
        a5 = new Note(81);
        bFlat5 = new Note(82, true); // prefer flat name
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("C", c.getName());
        assertEquals("F#", fSharp.getName());
        assertEquals("A", a.getName());
        assertEquals("Bb", bFlat.getName());

        assertEquals("C", c5.getName());
        assertEquals("F#", fSharp5.getName());
        assertEquals("A", a5.getName());
        assertEquals("Bb", bFlat5.getName());
    }

    @Test
    public void testGetAltName() throws Exception {

        // Default names are natural or sharp
        // Alt names are the flat versions, or null

        assertEquals("C", c.getDefaultName());
        assertEquals(null, c.getAltName());

        assertEquals("F#", fSharp.getDefaultName());
        assertEquals("Gb", fSharp.getAltName());

        assertEquals("A", a.getDefaultName());
        assertEquals(null, a.getAltName());

        assertEquals("A#", bFlat.getDefaultName());
        assertEquals("Bb", bFlat.getAltName());


        assertEquals("C", c5.getDefaultName());
        assertEquals(null, c5.getAltName());

        assertEquals("F#", fSharp5.getDefaultName());
        assertEquals("Gb", fSharp5.getAltName());

        assertEquals("A", a5.getDefaultName());
        assertEquals(null, a5.getAltName());

        assertEquals("A#", bFlat5.getDefaultName());
        assertEquals("Bb", bFlat5.getAltName());
    }

    @Test
    public void testGetRandomNote() throws Exception {
        Note n = Note.getRandomNote(60, 72);
        assertTrue(n.getMidiNumber() >= 60);
        assertTrue(n.getMidiNumber() < 72);
    }

    @Test
    public void testPlusSemitones() throws Exception {
        assertEquals(c.plusSemitones(12), c5);
        assertEquals(c.plusSemitones(6), fSharp);
        assertEquals(a.plusSemitones(1), bFlat);

        assertEquals(c.plusSemitones(6, 5).getName(), "Gb");
        assertEquals(c.plusSemitones(6, 4).getName(), "F#");

        assertEquals(c.plusSemitones(-6, -5).getName(), "F#");
        assertEquals(c.plusSemitones(-6, -4).getName(), "Gb");
    }

    @Test
    public void testPlusInterval() throws Exception {
        assertEquals(c.plusInterval(Interval.AUG4).getName(), "F#");
        assertEquals(c.plusInterval(Interval.DIM5).getName(), "Gb");
        assertEquals(c.plusInterval(Interval.AUG4), fSharp);
        assertEquals(c.plusInterval(Interval.DIM5), fSharp);

        assertEquals(a.plusInterval(Interval.m3).getName(), "C");
        assertEquals(a.plusInterval(Interval.M6).getName(), "F#");
        assertEquals(a.plusInterval(Interval.m3), c5);
        assertEquals(a.plusInterval(Interval.M6), fSharp5);
    }

    @Test
    public void testMinusInterval() throws Exception {
        assertEquals(c5.minusInterval(Interval.OCTAVE), c);
        assertEquals(bFlat5.minusInterval(Interval.OCTAVE).getName(), "Bb");

        assertEquals(fSharp.minusInterval(Interval.AUG4), c);

        assertEquals(c5.minusInterval(Interval.DIM5), fSharp);
        assertEquals(c5.minusInterval(Interval.AUG4).getName(), "Gb");
    }

    @Test
    public void testGetLetterAtInterval() throws Exception {
        assertEquals(c.getLetterAtInterval(5), 'G');
        assertEquals(c.getLetterAtInterval(-5), 'F');
        assertEquals(c.getLetterAtInterval(4), 'F');
        assertEquals(c.getLetterAtInterval(-4), 'G');

        assertEquals(fSharp.getLetterAtInterval(5), 'C');
        assertEquals(fSharp.getLetterAtInterval(4), 'B');
        assertEquals(fSharp.getLetterAtInterval(-4), 'C');
        assertEquals(fSharp.getLetterAtInterval(-5), 'B');

        assertEquals(bFlat.getLetterAtInterval(9), 'C');
        assertEquals(bFlat.getLetterAtInterval(-9), 'A');
    }

}