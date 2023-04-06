package com.company.controller;

import com.company.exception.PizzaNotFoundException;
import com.company.model.Pizza;
import com.company.model.PizzaOrder;
import com.company.repository.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final PizzaRepository pizzaRepository;

    public OrderController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/current")
    public String orderForm(@RequestParam UUID pizzaId, Model model) {

        PizzaOrder pizzaOrder = new PizzaOrder();

        // Fix the getPizza method below in line 49.
        pizzaOrder.setPizza(getPizza(pizzaId));

        model.addAttribute("pizzaOrder", pizzaOrder);

        return "/orderForm";
    }

    @PostMapping("/{pizzaId}")
    public String processOrder(@PathVariable UUID pizzaId, PizzaOrder pizzaOrder) {

        // Save the order

        pizzaOrder.setPizza(getPizza(pizzaId));
        return "redirect:/home";
    }


    private Pizza getPizza(UUID pizzaId) throws PizzaNotFoundException {

        return pizzaRepository.readAll().stream().filter(p -> p.getId().equals(pizzaId))
                .findFirst().orElseThrow(() -> new PizzaNotFoundException("Pizza not found"));
    }

}


//    pizzaOrder.setName("asd");
//    pizzaOrder.setPizza(null);        Note: If there is no field in the form, this will run.
