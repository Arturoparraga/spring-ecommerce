package com.example.ecommerce.controller;

import com.example.ecommerce.model.Producto;
import com.example.ecommerce.model.Usuario;
import com.example.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private final Logger logger = LoggerFactory.getLogger(ProductoController.class);

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("productos",productoService.findAll());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/save")
    public String save(Producto producto) {
        logger.info("este es el objeto producto [{}]",producto);
        Usuario usuario = new Usuario(1,"","","","","","","");
        producto.setUsuario(usuario);
        productoService.save(producto);
        return "redirect:/productos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional <Producto> optionalProducto = productoService.get(id);
        producto = optionalProducto.get();

        logger.info("producto encontrado [{}]",producto);
        model.addAttribute("producto",producto);
        return "productos/edit";
    }

    @PostMapping("/update")
    public String update (Producto producto) {
        productoService.update(producto);
        return "redirect:/productos";
    }

    @GetMapping("/delete")
    public String delete (@PathVariable Integer id) {
        productoService.delete(id);
        return "redirect:/productos";
    }
}
