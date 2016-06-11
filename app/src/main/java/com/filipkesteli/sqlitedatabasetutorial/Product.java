package com.filipkesteli.sqlitedatabasetutorial;

/**
 * Created by Filip on 11.6.2016..
 */
public class Product {

    private int _id;
    private String _productName;
    private int _quantity;

    public Product() {
    }

    public Product(int _id, String _productName, int _quantity) {
        this._id = _id;
        this._productName = _productName;
        this._quantity = _quantity;
    }

    public Product(String _productName, int _quantity) {
        this._productName = _productName;
        this._quantity = _quantity;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productName() {
        return _productName;
    }

    public void set_productName(String _productName) {
        this._productName = _productName;
    }

    public int get_quantity() {
        return _quantity;
    }

    public void set_quantity(int _quantity) {
        this._quantity = _quantity;
    }
}
