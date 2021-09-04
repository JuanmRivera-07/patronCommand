/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicauca.commandrestaurant.domain;

import co.edu.unicauca.commandrestaurant.access.IFoodRepository;
import co.edu.unicauca.commandrestaurant.access.RepositoryFactory;
import co.edu.unicauca.commandrestaurant.domain.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Rivera
 */
public class DeleteCommand extends Command {

    /**
     * Comida Eliminada
     */
    private Food food;
    /**
     * id de la comida a Eliminar
     */
    private int foodId;

    /**
     * Comida previa, que permitirá deshacer la operación de modificar
     */
    private Food foodPrevious;

    /**
     * Instancia al receptor
     */
    private FoodService service;

    public DeleteCommand(int foodId) {
        this.foodId = foodId;
        IFoodRepository repo = RepositoryFactory.getInstance().getRepository("default");
        service = new FoodService(repo);
        this.hasUndo = true;

    }

    @Override
    public void execute() {
        Logger logger = LoggerFactory.getLogger(DeleteCommand.class);
        logger.info("Comando de edición se ha ejecutado. Comida con id " + this.foodId + " eliminado");
        service.delete(foodId);
    }

    @Override
    public void undo() {
        Logger logger = LoggerFactory.getLogger(DeleteCommand.class);
        logger.info("Comando de edición se ha deshecho. Se deshizo la comida " + foodPrevious);
        service.create(foodPrevious);
    }

    public Food getFoodPrevious() {
        return foodPrevious;
    }

    public void setFoodPrevious(Food componentPrevious) {
        this.foodPrevious = componentPrevious;
    }
}
