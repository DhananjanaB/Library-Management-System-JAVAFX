package controllers;

public class Person {
    int qty,year;
    String id,title, author, category;

    public Person(int qty, int year, String id, String title, String author, String category){
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.qty = qty;
    }

    public int getQty(){
        return qty;
    }

    public void setQty(int qty){
        this.qty = qty;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getAuthor(){
        return author;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }
}
