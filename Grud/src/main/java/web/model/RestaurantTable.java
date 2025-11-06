package web.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", unique = true)
    private String tableNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "table_type")
    private TableType tableType;

    @Column(name = "location_description")
    private String locationDescription;

    @Column(name = "is_available")
    private Boolean isAvailable;

    // Конструкторы
    public RestaurantTable() {
        this.isAvailable = true;
    }

    public RestaurantTable(String tableNumber, Integer capacity, TableType tableType, String locationDescription) {
        this();
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.tableType = tableType;
        this.locationDescription = locationDescription;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTableNumber() { return tableNumber; }
    public void setTableNumber(String tableNumber) { this.tableNumber = tableNumber; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public TableType getTableType() { return tableType; }
    public void setTableType(TableType tableType) { this.tableType = tableType; }

    public String getLocationDescription() { return locationDescription; }
    public void setLocationDescription(String locationDescription) { this.locationDescription = locationDescription; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }
}