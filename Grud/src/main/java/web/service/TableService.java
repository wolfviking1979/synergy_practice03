package web.service;

import web.model.RestaurantTable;
import java.util.List;

public interface TableService {
    List<RestaurantTable> getAllTables();
    RestaurantTable getTableById(Long id);
    RestaurantTable createTable(RestaurantTable table);
    RestaurantTable updateTable(RestaurantTable table);
    void deleteTable(Long id);
    List<RestaurantTable> getTablesByCapacity(Integer minCapacity);
}