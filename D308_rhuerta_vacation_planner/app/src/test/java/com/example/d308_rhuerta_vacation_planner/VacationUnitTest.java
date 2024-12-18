package com.example.d308_rhuerta_vacation_planner;


import static org.junit.Assert.assertEquals;

import com.example.d308_rhuerta_vacation_planner.entities.Vacation;

import org.junit.Before;
import org.junit.Test;

public class VacationUnitTest {
    private Vacation vacation;

    @Before
    public void setUp() {
        vacation = new Vacation(1, "New York", "Hilton", "2024-06-01", "2024-06-15");
    }

    @Test
    public void testGetVacationId() {
        assertEquals("Vacation ID should be 1", 1, vacation.getVacationId());
    }

    @Test
    public void testSetVacationId() {
        vacation.setVacationId(2);
        assertEquals("Vacation ID should be updated to 2", 2, vacation.getVacationId());
    }

    @Test
    public void testGetVacationName() {
        assertEquals("Vacation name should be 'New York'", "New York", vacation.getVacationName());
    }

    @Test
    public void testSetVacationName() {
        vacation.setVacationName("Raleigh");
        assertEquals("Vacation name should be updated to 'Raleigh'", "Raleigh", vacation.getVacationName());
    }

    @Test
    public void testGetAccommodation() {
        assertEquals("Accommodation should be 'Hilton'", "Hilton", vacation.getAccommodation());
    }

    @Test
    public void testSetAccommodation() {
        vacation.setAccommodation("Marriott");
        assertEquals("Accommodation should be updated to 'Marriott'", "Marriott", vacation.getAccommodation());
    }

    @Test
    public void testGetStartDate() {
        assertEquals("Start date should be '2024-06-01'", "2024-06-01", vacation.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        vacation.setStartDate("2024-07-01");
        assertEquals("Start date should be updated to '2024-07-01'", "2024-07-01", vacation.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        assertEquals("End date should be '2024-06-15'", "2024-06-15", vacation.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        vacation.setEndDate("2024-07-15");
        assertEquals("End date should be updated to '2024-07-15'", "2024-07-15", vacation.getEndDate());
    }
}
