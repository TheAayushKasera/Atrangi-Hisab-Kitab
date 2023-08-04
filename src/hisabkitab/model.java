/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hisabkitab;

/**
 *
 * @author aayus
 */
public class model {
    String item,via;
    int price;
    String date;
    
    
    public model(String item, int price, String via,String date) {
        this.item = item;
        this.via = via;
        this.price=price;
        this.date=date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    model(String string, int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    

    

    public void setItem(String item) {
        this.item = item;
    }


    public void setVia(String via) {
        this.via = via;
    }


    public String getItem() {
        return item;
    }


    public String getVia() {
        return via;
    }
}
