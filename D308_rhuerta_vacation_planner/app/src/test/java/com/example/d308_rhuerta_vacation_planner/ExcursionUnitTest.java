package com.example.d308_rhuerta_vacation_planner;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.d308_rhuerta_vacation_planner.entities.Excursion;


public class ExcursionUnitTest {
    private Excursion excursion;

    @Before
    public void setUp() {
        excursion = new Excursion(1, "Scuba Diving", "2024-05-10", 101);
    }

    @Test
    public void testGetExcursionId() {
        assertEquals("Excursion ID should be 1", 1, excursion.getExcursionId());
    }

    @Test
    public void testSetExcursionId() {
        excursion.setExcursionId(2);
        assertEquals("Excursion ID should be 2 after update", 2, excursion.getExcursionId());
    }

    @Test
    public void testGetExcursionName() {
        assertEquals("Excursion name should be 'Scuba Diving'", "Scuba Diving", excursion.getExcursionName());
    }

    @Test
    public void testSetExcursionName() {
        excursion.setExcursionName("Snorkeling");
        assertEquals("Excursion name should be 'Snorkeling' after update", "Snorkeling", excursion.getExcursionName());
    }

    @Test
    public void testGetDate() {
        assertEquals("Excursion date should be '2024-05-10'", "2024-05-10", excursion.getDate());
    }

    @Test
    public void testSetDate() {
        excursion.setDate("2024-06-15");
        assertEquals("Excursion date should be '2024-06-15' after update", "2024-06-15", excursion.getDate());
    }

    @Test
    public void testGetVacationId() {
        assertEquals("Vacation ID should be 101", 101, excursion.getVacationId());
    }

    @Test
    public void testSetVacationId() {
        excursion.setVacationId(202);
        assertEquals("Vacation ID should be 202 after update", 202, excursion.getVacationId());
    }
}