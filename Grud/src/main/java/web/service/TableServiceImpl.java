package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.repository.TableRepository;
import web.model.RestaurantTable;

import java.util.List;

@Service
@Transactional
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    public TableServiceImpl(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public List<RestaurantTable> getAllTables() {
        return tableRepository.getAllTables();
    }

    @Override
    public RestaurantTable getTableById(Long id) {
        return tableRepository.getTableById(id);
    }

    @Override
    public RestaurantTable createTable(RestaurantTable table) {
        return tableRepository.saveTable(table);
    }

    @Override
    public RestaurantTable updateTable(RestaurantTable table) {
        return tableRepository.saveTable(table);
    }

    @Override
    public void deleteTable(Long id) {
        tableRepository.deleteTable(id);
    }

    @Override
    public List<RestaurantTable> getTablesByCapacity(Integer minCapacity) {
        return tableRepository.findTablesByCapacity(minCapacity);
    }
}