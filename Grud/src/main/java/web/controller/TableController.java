package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.model.RestaurantTable;
import web.model.TableType;
import web.service.TableService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping({"", "/", "list"})
    public String showAllTables(Model model,
                                @ModelAttribute("flashMessage") String flashAttribute) {
        model.addAttribute("tables", tableService.getAllTables());
        return "tables/list";
    }

    @GetMapping("/new")
    public String showTableForm(@ModelAttribute("table") RestaurantTable table) {
        return "tables/form";
    }

    @PostMapping
    public String createTable(@ModelAttribute("table") @Valid RestaurantTable table,
                              BindingResult bindingResult,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "tables/form";
        }

        tableService.createTable(table);
        attributes.addFlashAttribute("flashMessage",
                "Table " + table.getTableNumber() + " successfully created!");
        return "redirect:/tables";
    }

    @GetMapping("/{id}/edit")
    public String editTableForm(@PathVariable("id") Long id, Model model,
                                RedirectAttributes attributes) {
        RestaurantTable table = tableService.getTableById(id);
        if (table == null) {
            attributes.addFlashAttribute("flashMessage", "Table not found!");
            return "redirect:/tables";
        }
        model.addAttribute("table", table);
        return "tables/form";
    }

    @PostMapping("/{id}/update")
    public String updateTable(@PathVariable("id") Long id,
                              @ModelAttribute("table") @Valid RestaurantTable table,
                              BindingResult bindingResult,
                              RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return "tables/form";
        }

        table.setId(id);
        tableService.updateTable(table);
        attributes.addFlashAttribute("flashMessage",
                "Table " + table.getTableNumber() + " successfully updated!");
        return "redirect:/tables";
    }

    @GetMapping("/{id}/delete")
    public String deleteTable(@PathVariable("id") Long id,
                              RedirectAttributes attributes) {
        try {
            tableService.deleteTable(id);
            attributes.addFlashAttribute("flashMessage", "Table successfully deleted!");
        } catch (Exception e) {
            attributes.addFlashAttribute("flashMessage", "Error: " + e.getMessage());
        }
        return "redirect:/tables";
    }
}