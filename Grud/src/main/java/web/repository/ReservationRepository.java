package web.repository;

import web.model.Reservation;
import web.model.RestaurantTable;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    Reservation saveReservation(Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> findReservationsByTableAndDateTime(RestaurantTable table, LocalDateTime dateTime);
    List<Reservation> findReservationsByCustomerPhone(String phone);
    List<Reservation> findUpcomingReservations();
}