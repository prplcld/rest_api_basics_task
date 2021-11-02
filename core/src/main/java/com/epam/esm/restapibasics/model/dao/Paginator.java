package com.epam.esm.restapibasics.model.dao;

public class Paginator {

    private static final int MIN_PAGE = 1;
    private static final int MIN_AMOUNT = 0;
    private static final int MAX_AMOUNT = 50;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_AMOUNT = 10;

    private int page;
    private int amount;

    public Paginator(Integer page, Integer amount) {
        if (page == null) {
            page = DEFAULT_PAGE;
        }

        if (amount == null) {
            amount = DEFAULT_AMOUNT;
        }

        if (page < MIN_PAGE) {
            //FIXME
        }

        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
           //FIXME
        }

        this.page = page;
        this.amount = amount;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStartValue() {
        return amount * (page - 1);
    }
}
