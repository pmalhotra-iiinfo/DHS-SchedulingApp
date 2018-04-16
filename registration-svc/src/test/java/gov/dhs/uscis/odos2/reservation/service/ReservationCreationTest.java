package gov.dhs.uscis.odos2.reservation.service;


import gov.dhs.uscis.odos2.reservation.exception.InvalidReservationException;
import gov.dhs.uscis.odos2.reservation.model.Reservation;
import gov.dhs.uscis.odos2.reservation.repository.ReservationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReservationCreationTest {

    @Autowired
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    Reservation reservation;

    @TestConfiguration
    static class ReservationServiceImplTestContextConfiguration {

        @Bean
        public ReservationService reservationService() {
            return new ReservationServiceImpl();
        }
    }

    @Before
    public void setUp() {
        this.reservation = new Reservation();
        this.reservation.setConferenceTitle("Some title");
        this.reservation.setReservationDate(LocalDate.now());
        this.reservation.setStartTime(LocalTime.of(12, 00));
        this.reservation.setEndTime(LocalTime.of(13, 00));
    }

    @Test(expected = InvalidReservationException.class)
    public void shouldThrowExceptionDueToMinTimeConstraint() throws InvalidReservationException {

        this.reservation.setEndTime(LocalTime.of(12, 20));
        reservationService.createNewReservation(this.reservation);
    }

    @Test(expected = InvalidReservationException.class)
    public void shouldThrowExceptionDueToMaxTimeConstraint() throws InvalidReservationException {

        this.reservation.setEndTime(LocalTime.of(17, 20));
        reservationService.createNewReservation(this.reservation);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionDueToEmptyValues() throws InvalidReservationException {

        Reservation reservation = new Reservation();
        reservationService.createNewReservation(reservation);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionDueToEmptyConferenceTitle() throws InvalidReservationException {

        this.reservation.setConferenceTitle("");
        reservationService.createNewReservation(this.reservation);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionDueToEmptyDate() throws InvalidReservationException {

        this.reservation.setReservationDate(null);
        reservationService.createNewReservation(this.reservation);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionDueToEmptyStartTime() throws InvalidReservationException {

        this.reservation.setStartTime(null);
        reservationService.createNewReservation(this.reservation);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionDueToEmptyEndTime() throws InvalidReservationException {

        this.reservation.setEndTime(null);
        reservationService.createNewReservation(this.reservation);

    }

    @Test
    public void shouldPersist() {

        when(reservationRepository.save(any(Reservation.class))).thenReturn(this.reservation);

        try {
            Reservation newReservation = reservationService.createNewReservation(this.reservation);
            assertThat(newReservation).isNotNull();
        } catch (InvalidReservationException e) {
            fail(e.getMessage());
        }

    }

}
