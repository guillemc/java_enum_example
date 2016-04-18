package net.guillemc.notes;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class NumberTest {

    Number one, sharpFour, flatFive, six;

    @BeforeMethod
    public void setUp() throws Exception {
        one = new Number("1");
        sharpFour = new Number("#4");
        flatFive = new Number("b5");
        six = new Number("6");

        assertEquals(sharpFour, flatFive);
    }

    @Test
    public void testGetDegree() throws Exception {
        assertEquals(one.getDegree(), 1);
        assertEquals(sharpFour.getDegree(), 4);
        assertEquals(flatFive.getDegree(), 5);
        assertEquals(six.getDegree(), 6);
    }

    @Test
    public void testPlusSemitones() throws Exception {
        assertEquals(one.plusSemitones(6), sharpFour);
        assertEquals(one.plusSemitones(9), six);
        assertEquals(six.plusSemitones(-3), sharpFour);
        assertEquals(six.plusSemitones(-9), one);

        assertEquals(one.plusSemitones(6, 4).getName(), "#4");
        assertEquals(one.plusSemitones(6, 5).getName(), "b5");

        assertEquals(one.plusSemitones(6, 4).getName(), "#4");
        assertEquals(one.plusSemitones(6, 5).getName(), "b5");

        assertEquals(six.plusSemitones(-3, -3).getName(), "#4");
        assertEquals(six.plusSemitones(-3, -2).getName(), "b5");
    }

    @Test
    public void testPlusInterval() throws Exception {
        assertEquals(sharpFour.plusInterval(Interval.DIM5), one.plusInterval(Interval.OCTAVE));
        assertEquals(six.plusInterval(Interval.m3), one.plusInterval(Interval.OCTAVE));
        assertEquals(one.plusInterval(Interval.M9), one.plusInterval(Interval.OCTAVE).plusInterval(Interval.M2));

        assertEquals(one.plusInterval(Interval.AUG4).getName(), sharpFour.getName());
        assertEquals(one.plusInterval(Interval.DIM5).getName(), flatFive.getName());
    }

    @Test
    public void testMinusInterval() throws Exception {

        assertEquals(flatFive.minusInterval(Interval.DIM5), one);
        assertEquals(six.minusInterval(Interval.M6), one);
        assertEquals(one.plusInterval(Interval.OCTAVE).minusInterval(Interval.m3), six);

        assertEquals(one.plusInterval(Interval.OCTAVE).minusInterval(Interval.DIM5).getName(), sharpFour.getName());
        assertEquals(one.plusInterval(Interval.OCTAVE).minusInterval(Interval.AUG4).getName(), flatFive.getName());
    }

    @Test
    public void testGetDegreeAtInterval() throws Exception {
        assertEquals(one.getDegreeAtInterval(6), 6);
        assertEquals(one.getDegreeAtInterval(-6), 3);
    }

    @Test
    public void testGetNote() throws Exception {
        assertEquals(one.getNote(new Note("C")), new Note("C"));
        assertEquals(one.getNote(new Note("G#")), new Note("G#"));
        assertEquals(one.plusInterval(Interval.OCTAVE).getNote(new Note("C")), new Note("C", 5));
        assertEquals(six.getNote(new Note("C")), new Note("A"));
        assertEquals(flatFive.getNote(new Note("G")), new Note("Db", 5));
    }

}