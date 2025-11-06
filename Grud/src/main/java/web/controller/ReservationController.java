package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.model.Reservation;
import web.model.RestaurantTable;
import web.model.TableType;
import web.service.ReservationService;
import web.service.TableService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final TableService tableService;

    public ReservationController(ReservationService reservationService, TableService tableService) {
        this.reservationService = reservationService;
        this.tableService = tableService;
    }

    @GetMapping({"", "/", "list"})
    public String showAllReservations(Model model,
                                      @ModelAttribute("flashMessage") String flashAttribute) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations/list";
    }

    @GetMapping("/upcoming")
    public String showUpcomingReservations(Model model) {
        model.addAttribute("reservations", reservationService.getUpcomingReservations());
        return "reservations/list";
    }

    @GetMapping("/new")
    public String showReservationForm(@ModelAttribute("reservation") Reservation reservation,
                                      Model model) {
        // Передаем в модель все столики для выпадающего списка (можно будет фильтровать в форме)
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("tableTypes", TableType.values());
        return "reservations/form";
    }

    @PostMapping("/search-available-tables")
    public String searchAvailableTables(@RequestParam("reservationDateTime") String dateTimeStr,
                                        @RequestParam("numberOfGuests") Integer numberOfGuests,
                                        @RequestParam(value = "tableType", required = false) TableType tableType,
                                        Model model) {
        LocalDateTime reservationDateTime = LocalDateTime.parse(dateTimeStr,
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<RestaurantTable> availableTables = reservationService
                .findAvailableTables(reservationDateTime, numberOfGuests, tableType);
        model.addAttribute("availableTables", availableTables);
        model.addAttribute("reservationDateTime", reservationDateTime);
        model.addAttribute("numberOfGuests", numberOfGuests);
        model.addAttribute("tableType", tableType);
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("tableTypes", TableType.values());
        return "reservations/form";
    }

    @PostMapping
    public String createReservation(@ModelAttribute("reservation") @Valid Reservation reservation,
                                    BindingResult bindingResult,
                                    RedirectAttributes attributes,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tables", tableService.getAllTables());
            model.addAttribute("tableTypes", TableType.values());
            return "reservations/form";
        }

        try {
            reservationService.createReservation(reservation);
            attributes.addFlashAttribute("flashMessage",
                    "Reservation for " + reservation.getCustomerName() + " successfully created!");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("flashMessage", "Error: " + e.getMessage());
            return "redirect:/reservations/new";
        }

        return "redirect:/reservations";
    }

    @GetMapping("/{id}/edit")
    public String editReservationForm(@PathVariable("id") Long id, Model model,
                                      RedirectAttributes attributes) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            attributes.addFlashAttribute("flashMessage", "Reservation not found!");
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("tableTypes", TableType.values());
        return "reservations/form";
    }

    @PostMapping("/{id}/update")
    public String updateReservation(@PathVariable("id") Long id,
                                    @ModelAttribute("reservation") @Valid Reservation reservation,
                                    BindingResult bindingResult,
                                    RedirectAttributes attributes,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tables", tableService.getAllTables());
            model.addAttribute("tableTypes", TableType.values());
            return "reservations/form";
        }

        try {
            reservation.setId(id);
            reservationService.updateReservation(reservation);
            attributes.addFlashAttribute("flashMessage",
                    "Reservation for " + reservation.getCustomerName() + " successfully updated!");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("flashMessage", "Error: " + e.getMessage());
            return "redirect:/reservations/" + id + "/edit";
        }

        return "redirect:/reservations";
    }

    @GetMapping("/{id}/confirm")
    public String confirmReservation(@PathVariable("id") Long id,
                                     RedirectAttributes attributes) {
        try {
            reservationService.confirmReservation(id);
            attributes.addFlashAttribute("flashMessage", "Reservation confirmed!");
        } catch (Exception e) {
            attributes.addFlashAttribute("flashMessage", "Error: " + e.getMessage());
        }
        return "redirect:/reservations";
    }

    @GetMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable("id") Long id,
                                    RedirectAttributes attributes) {
        try {
            reservationService.cancelReservation(id);
            attributes.addFlashAttribute("flashMessage", "Reservation cancelled!");
        } catch (Exception e) {
            attributes.addFlashAttribute("flashMessage", "Error: " + e.getMessage());
        }
        return "redirect:/reservations";
    }

    @GetMapping("/search")
    public String searchReservationsByPhone(@RequestParam("phone") String phone, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByCustomerPhone(phone);
        model.addAttribute("reservations", reservations);
        model.addAttribute("searchPhone", phone);
        return "reservations/list";
    }
}