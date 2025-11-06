package web.repository;

import org.springframework.stereotype.Repository;
import web.model.Reservation;
import web.model.RestaurantTable;
import web.model.ReservationStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> getAllReservations() {
        return entityManager.createQuery("FROM Reservation r ORDER BY r.reservationDateTime DESC", Reservation.class)
                .getResultList();
    }

    @Override
    public Reservation getReservationById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        if (reservation.getId() == null) {
            entityManager.persist(reservation);
        } else {
            reservation = entityManager.merge(reservation);
        }
        entityManager.flush();
        return reservation;
    }

    @Override
    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        if (reservation != null) {
            entityManager.remove(reservation);
            entityManager.flush();
        }
    }

    @Override
    public List<Reservation> findReservationsByTableAndDateTime(RestaurantTable table, LocalDateTime dateTime) {
        LocalDateTime startOfDay = dateTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = dateTime.toLocalDate().atTime(23, 59, 59);

        TypedQuery<Reservation> query = entityManager.createQuery(
                "FROM Reservation r WHERE r.table = :table AND r.reservationDateTime BETWEEN :start AND :end " +
                        "AND r.status IN (:confirmed, :pending) ORDER BY r.reservationDateTime",
                Reservation.class);

        query.setParameter("table", table);
        query.setParameter("start", startOfDay);
        query.setParameter("end", endOfDay);
        query.setParameter("confirmed", ReservationStatus.CONFIRMED);
        query.setParameter("pending", ReservationStatus.PENDING);

        return query.getResultList();
    }

    @Override
    public List<Reservation> findReservationsByCustomerPhone(String phone) {
        TypedQuery<Reservation> query = entityManager.createQuery(
                "FROM Reservation r WHERE r.customerPhone = :phone ORDER BY r.reservationDateTime DESC",
                Reservation.class);

        query.setParameter("phone", phone);
        return query.getResultList();
    }

    @Override
    public List<Reservation> findUpcomingReservations() {
        TypedQuery<Reservation> query = entityManager.createQuery(
                "FROM Reservation r WHERE r.reservationDateTime >= :now AND r.status IN (:confirmed, :pending) " +
                        "ORDER BY r.reservationDateTime",
                Reservation.class);

        query.setParameter("now", LocalDateTime.now());
        query.setParameter("confirmed", ReservationStatus.CONFIRMED);
        query.setParameter("pending", ReservationStatus.PENDING);

        return query.getResultList();
    }
}