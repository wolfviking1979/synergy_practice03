package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.repository.ReservationRepository;
import web.repository.TableRepository;
import web.model.Reservation;
import web.model.RestaurantTable;
import web.model.ReservationStatus;
import web.model.TableType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.getAllReservations();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.getReservationById(id);
    }

    @Override
    public Reservation createReservation(Reservation reservation) {
        // Проверяем доступность столика
        if (!isTableAvailable(reservation.getTable(), reservation.getReservationDateTime())) {
            throw new IllegalArgumentException("Table is not available at the selected time");
        }

        reservation.setStatus(ReservationStatus.PENDING);
        return reservationRepository.saveReservation(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        Reservation existing = getReservationById(reservation.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        // Если изменили столик или время, проверяем доступность
        if (!existing.getTable().equals(reservation.getTable()) ||
                !existing.getReservationDateTime().equals(reservation.getReservationDateTime())) {
            if (!isTableAvailable(reservation.getTable(), reservation.getReservationDateTime())) {
                throw new IllegalArgumentException("Table is not available at the selected time");
            }
        }

        return reservationRepository.saveReservation(reservation);
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservationRepository.saveReservation(reservation);
        }
    }

    @Override
    public void confirmReservation(Long id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservationRepository.saveReservation(reservation);
        }
    }

    @Override
    public List<RestaurantTable> findAvailableTables(LocalDateTime dateTime, Integer guests, TableType tableType) {
        List<RestaurantTable> potentialTables = tableRepository.findAvailableTables(guests, tableType);

        // Фильтруем по доступности во времени
        return potentialTables.stream()
                .filter(table -> isTableAvailable(table, dateTime))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTableAvailable(RestaurantTable table, LocalDateTime dateTime) {
        List<Reservation> conflictingReservations = reservationRepository
                .findReservationsByTableAndDateTime(table, dateTime);

        return conflictingReservations.isEmpty();
    }

    @Override
    public List<Reservation> getReservationsByCustomerPhone(String phone) {
        return reservationRepository.findReservationsByCustomerPhone(phone);
    }

    @Override
    public List<Reservation> getUpcomingReservations() {
        return reservationRepository.findUpcomingReservations();
    }
}