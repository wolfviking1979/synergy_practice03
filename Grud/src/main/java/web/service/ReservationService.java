package web.service;

import web.model.Reservation;
import web.model.RestaurantTable;
import web.model.TableType;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(Long id);
    Reservation createReservation(Reservation reservation);
    Reservation updateReservation(Reservation reservation);
    void cancelReservation(Long id);
    void confirmReservation(Long id);
    List<RestaurantTable> findAvailableTables(LocalDateTime dateTime, Integer guests, TableType tableType);
    boolean isTableAvailable(RestaurantTable table, LocalDateTime dateTime);
    List<Reservation> getReservationsByCustomerPhone(String phone);
    List<Reservation> getUpcomingReservations();
}