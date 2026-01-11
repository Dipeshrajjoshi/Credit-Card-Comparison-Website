package com.example.demo.model;

import java.util.Objects;

/**
 * Represents a Credit Card entity with its attributes and properties.
 */
public class CreditCard {
    private String cardTitle;
    private String cardImages;
    private String annualFees;
    private String purchaseInterestRate;
    private String cashInterestRate;
    private String productValueProp;
    private String productBenefits;
    private String bankName;
    private String cardLink;

    // No-args constructor for frameworks (Jackson, JPA, etc.)
    public CreditCard() {}

    // All-args constructor
    public CreditCard(String cardTitle, String cardImages, String annualFees,
                      String purchaseInterestRate, String cashInterestRate,
                      String productValueProp, String productBenefits,
                      String bankName, String cardLink) {
        this.cardTitle = cardTitle;
        this.cardImages = cardImages;
        this.annualFees = annualFees;
        this.purchaseInterestRate = purchaseInterestRate;
        this.cashInterestRate = cashInterestRate;
        this.productValueProp = productValueProp;
        this.productBenefits = productBenefits;
        this.bankName = bankName;
        this.cardLink = cardLink;
    }

    // Getters and setters

    public String getCardTitle() {
        return cardTitle;
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public String getCardImages() {
        return cardImages;
    }

    public void setCardImages(String cardImages) {
        this.cardImages = cardImages;
    }

    public String getAnnualFees() {
        return annualFees;
    }

    public void setAnnualFees(String annualFees) {
        this.annualFees = annualFees;
    }

    public String getPurchaseInterestRate() {
        return purchaseInterestRate;
    }

    public void setPurchaseInterestRate(String purchaseInterestRate) {
        this.purchaseInterestRate = purchaseInterestRate;
    }

    public String getCashInterestRate() {
        return cashInterestRate;
    }

    public void setCashInterestRate(String cashInterestRate) {
        this.cashInterestRate = cashInterestRate;
    }

    public String getProductValueProp() {
        return productValueProp;
    }

    public void setProductValueProp(String productValueProp) {
        this.productValueProp = productValueProp;
    }

    public String getProductBenefits() {
        return productBenefits;
    }

    public void setProductBenefits(String productBenefits) {
        this.productBenefits = productBenefits;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardLink() {
        return cardLink;
    }

    public void setCardLink(String cardLink) {
        this.cardLink = cardLink;
    }

    // Override toString() for debugging
    @Override
    public String toString() {
        return "CreditCard{" +
                "cardTitle='" + cardTitle + '\'' +
                ", bankName='" + bankName + '\'' +
                ", annualFees='" + annualFees + '\'' +
                ", purchaseInterestRate='" + purchaseInterestRate + '\'' +
                ", productBenefits='" + productBenefits + '\'' +
                '}';
    }

    // Override equals and hashCode based on cardTitle and bankName
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(cardTitle, that.cardTitle) &&
               Objects.equals(bankName, that.bankName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardTitle, bankName);
    }
}
