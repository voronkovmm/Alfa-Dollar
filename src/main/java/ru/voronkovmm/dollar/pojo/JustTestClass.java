package ru.voronkovmm.dollar.pojo;

/**
 * Это просто тестовый класс, который используется для тестирования в модуле test класса DollarApplicationTest
 *
 * author: Voronkov M.M.
 * @version 1.0
 */
public class JustTestClass {

    double currencyToday;
    double currencyYesterday;
    String gifTag;

    public double getCurrencyToday() {
        return currencyToday;
    }

    public void setCurrencyToday(double currencyToday) {
        this.currencyToday = currencyToday;
    }

    public double getCurrencyYesterday() {
        return currencyYesterday;
    }

    public void setCurrencyYesterday(double currencyYesterday) {
        this.currencyYesterday = currencyYesterday;
    }

    public String getGifTag() {
        return gifTag;
    }

    public void setGifTag(String gifTag) {
        this.gifTag = gifTag;
    }
}
