package ShoppingCart;

import java.util.*;

public class ShoppingCart {
    private Map<Item, Integer> itemList;
    private int currentTotalPrice;
    private BuyLaterList buyLater;
    private User user;

    public ShoppingCart(User u) {
        user = u;
        itemList = new HashMap<Item, Integer>();
        currentTotalPrice = 0;
        buyLater = new BuyLaterList();
    }

    public void addItem(Item item) {
        if (itemList.containsKey(item)) {
            itemList.put(item, itemList.get(item) + 1);
        } else {
            itemList.put(item, 1);
        }
        currentTotalPrice = currentTotalPrice + item.getPrice();
    }

    public void deleteItem(Item item) throws Exception {
        if (!itemList.containsKey(item)) {
            throw new Exception("Item does not exit!");
        }
        currentTotalPrice = currentTotalPrice - item.getPrice() * itemList.get(item);
        itemList.remove(item);
    };

    public void itemAmountPlusOne(Item item) throws Exception{
        if (!itemList.containsKey(item)) {
            addItem(item);
        }
        if (itemList.get(item) == item.getStockAmount()) {
            throw new Exception("Sorry, already maximum amount.");
        }
        itemList.put(item, itemList.get(item) + 1);
        currentTotalPrice = currentTotalPrice + item.getPrice();
    };

    public void itemAmountMinusOne(Item item) throws Exception {
        if (!itemList.containsKey(item)) {
            throw new Exception("There is no this item.");
        }
        if (itemList.get(item) == 1) {
            deleteItem(item);
        }
        itemList.put(item, itemList.get(item) - 1);
        currentTotalPrice = currentTotalPrice - item.getPrice();
    };

    public void moveItemToBuyLaterList(Item item) {
        try {
            deleteItem(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        buyLater.addItem(item);
    };


    public Map<Item, Integer> getItemList() {
        return new HashMap<>(itemList);
    }

    public int getTotalPrice() {
        return currentTotalPrice;
    }

    public Order createOrder() {
        return new Order(this);
    }
}

class BuyLaterList {
    private Set<Item> itemList;
    private ShoppingCart cart;
    private User user;

    public BuyLaterList() {
        itemList = new HashSet<Item>();
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void deleteItem(Item item) {
        itemList.remove(item);
    }

    public void moveItemToCart(Item item) {
        deleteItem(item);
        cart.addItem(item);
    }
}

class Item {
    private int id;
    private int price;
    private int stockAmount;

    public int getPrice() {
        return price;
    }

    public int getStockAmount() {
        return stockAmount;
    }
}

class Order {
    private Map<Item, Integer> orderList;
    private int totalPrice;

    public Order(ShoppingCart cart) {
        orderList = new HashMap<Item, Integer>(cart.getItemList());
        totalPrice = 0;
        for (Item i : orderList.keySet()) {
            totalPrice = totalPrice + i.getPrice() * orderList.get(i);
        }
    }
}

class User {
    private int id;
}
