package web.repository;

import web.model.RestaurantTable;
import web.model.TableType;
import java.util.List;

public interface TableRepository {
    List<RestaurantTable> getAllTables();
    RestaurantTable getTableById(Long id);
    RestaurantTable saveTable(RestaurantTable table);
    void deleteTable(Long id);
    List<RestaurantTable> findAvailableTables(Integer capacity, TableType tableType);
    List<RestaurantTable> findTablesByCapacity(Integer minCapacity);
}