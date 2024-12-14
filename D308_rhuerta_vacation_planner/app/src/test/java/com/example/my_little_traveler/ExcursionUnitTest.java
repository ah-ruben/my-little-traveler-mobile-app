package com.example.my_little_traveler;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.application.my_little_traveler.entities.Excursion;


public class ExcursionUnitTest {
    private Excursion excursion;

    @Before
    public void setUp() {
        excursion = new Excursion(1, "Scuba Diving", "2024-05-10", 101);
    }

    @Test
    public void testGetExcursionId() {
        assertEquals("Excursion ID should be 1", 1, excursion.getExcursionId());
        System.out.println("Test passed! \n Excursion ID is " + excursion.getVacationId());
    }

    @Test
    public void testSetExcursionId() {
        excursion.setExcursionId(2);
        assertEquals("Excursion ID should be 2 after update", 2, excursion.getExcursionId());
        System.out.println("Test passed! \nExcursion ID is " + excursion.getVacationId());
    }

    @Test
    public void testGetExcursionName() {
        assertEquals("Excursion name should be 'Scuba Diving'", "Scuba Diving", excursion.getExcursionName());
        System.out.println("Test passed! \nExcursion name is " + excursion.getExcursionName());
    }

    @Test
    public void testSetExcursionName() {
        excursion.setExcursionName("Snorkeling");
        assertEquals("Excursion name should be 'Snorkeling' after update", "Snorkeling", excursion.getExcursionName());
        System.out.println("Test passed! \nExcursion name is " + excursion.getExcursionName());
    }

    @Test
    public void testGetDate() {
        assertEquals("Excursion date should be '2024-05-10'", "2024-05-10", excursion.getDate());
        System.out.println("Test passed! \nExcursion date is " + excursion.getDate());
    }

    @Test
    public void testSetDate() {
        excursion.setDate("2024-06-15");
        assertEquals("Excursion date should be '2024-06-15' after update", "2024-06-15", excursion.getDate());
        System.out.println("Test passed! \nExcursion date is " + excursion.getDate());
    }

    @Test
    public void testGetVacationId() {
        assertEquals("Vacation ID should be 101", 101, excursion.getVacationId());
        System.out.println("Test passed! \nVacation ID is " + excursion.getVacationId());
    }

    @Test
    public void testSetVacationId() {
        excursion.setVacationId(202);
        assertEquals("Vacation ID should be 202 after update", 202, excursion.getVacationId());
        System.out.println("Test passed! \nVacation ID is " + excursion.getVacationId());
    }
}