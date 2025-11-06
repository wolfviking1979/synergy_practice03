package web.repository;

import org.springframework.stereotype.Repository;
import web.model.RestaurantTable;
import web.model.TableType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TableRepositoryImpl implements TableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RestaurantTable> getAllTables() {
        return entityManager.createQuery("FROM RestaurantTable t ORDER BY t.capacity", RestaurantTable.class)
                .getResultList();
    }

    @Override
    public RestaurantTable getTableById(Long id) {
        return entityManager.find(RestaurantTable.class, id);
    }

    @Override
    public RestaurantTable saveTable(RestaurantTable table) {
        if (table.getId() == null) {
            entityManager.persist(table);
        } else {
            table = entityManager.merge(table);
        }
        entityManager.flush();
        return table;
    }

    @Override
    public void deleteTable(Long id) {
        RestaurantTable table = getTableById(id);
        if (table != null) {
            entityManager.remove(table);
            entityManager.flush();
        }
    }

    @Override
    public List<RestaurantTable> findAvailableTables(Integer capacity, TableType tableType) {
        // Строим базовый запрос
        StringBuilder queryBuilder = new StringBuilder(
                "FROM RestaurantTable t WHERE t.isAvailable = true AND t.capacity >= :capacity");

        // Добавляем условие по типу столика, если указан
        if (tableType != null) {
            queryBuilder.append(" AND t.tableType = :tableType");
        }

        queryBuilder.append(" ORDER BY t.capacity");

        // Создаем типизированный запрос
        TypedQuery<RestaurantTable> query = entityManager.createQuery(
                queryBuilder.toString(), RestaurantTable.class);

        // Устанавливаем параметры
        query.setParameter("capacity", capacity);

        if (tableType != null) {
            query.setParameter("tableType", tableType);
        }

        return query.getResultList();
    }

    @Override
    public List<RestaurantTable> findTablesByCapacity(Integer minCapacity) {
        return entityManager.createQuery(
                        "FROM RestaurantTable t WHERE t.capacity >= :capacity ORDER BY t.capacity",
                        RestaurantTable.class)
                .setParameter("capacity", minCapacity)
                .getResultList();
    }
}