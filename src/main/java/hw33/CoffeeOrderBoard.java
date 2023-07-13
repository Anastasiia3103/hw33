package hw33;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class CoffeeOrderBoard {
    private static final Logger logger = Logger.getLogger(CoffeeOrderBoard.class.getName());
    private static final String LOG_FILE_NAME = "coffee_order.log";

    static {
        try {
            Logger rootLogger = Logger.getLogger("");
            ConsoleHandler consoleHandler = new ConsoleHandler ();
            consoleHandler.setLevel(Level.INFO);
            rootLogger.addHandler(consoleHandler);

            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error configuring logging.", e);
        }
    }

    private List<Order> orderQueue;
    private int nextOrderNumber;

    public CoffeeOrderBoard() {
        orderQueue = new ArrayList<>();
        nextOrderNumber = 1;
        logger.log(Level.INFO, "CoffeeOrderBoard initialized.");
    }

    public void add(String orderName) {
        logger.log(Level.INFO, "Adding order for {0}", orderName);
        Order order = new Order(nextOrderNumber, orderName);
        orderQueue.add(order);
        nextOrderNumber++;
    }

    public Order deliver() {
        if (!orderQueue.isEmpty()) {
            logger.log(Level.INFO, "Delivering closest order.");
            Order order = orderQueue.remove(0);
            logger.log(Level.INFO, "Delivered order: {0}", order);
            return order;
        } else {
            logger.log(Level.INFO, "No orders in the queue.");
            return null;
        }
    }

    public Order deliverByNumber(int orderNumber) {
        for (int i = 0; i < orderQueue.size(); i++) {
            Order order = orderQueue.get(i);
            if (order.getOrderNumber() == orderNumber) {
                logger.log(Level.INFO, "Delivering order with number {0}", orderNumber);
                Order deliveredOrder = orderQueue.remove(i);
                logger.log(Level.INFO, "Delivered order: {0}", deliveredOrder);
                return deliveredOrder;
            }
        }
        logger.log(Level.INFO, "No order with number {0} found.", orderNumber);
        return null;
    }

    public void draw() {
        if (!orderQueue.isEmpty()) {
            logger.log(Level.INFO, "Current state of the queue:");
            logger.log(Level.INFO, "=================");
            logger.log(Level.INFO, "Num | Name");
            for (Order order : orderQueue) {
                logger.log(Level.INFO, order.toString());
            }
            logger.log(Level.INFO, "=================");
        } else {
            logger.log(Level.INFO, "No orders in the queue.");
        }
    }

    public static void main(String[] args) {
        CoffeeOrderBoard board = new CoffeeOrderBoard();
        board.add("Allen");
        board.add("Yoda");
        board.add("Obi-wan");
        board.add("John Snow");
        board.draw();
        Order deliveredOrder = board.deliver();
        board.draw();
        board.deliverByNumber(33);
        board.draw();
    }
}

