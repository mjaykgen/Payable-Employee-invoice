package Inhertiance;

import java.util.Scanner;

interface Payable {
    abstract double getPaymentAmount();

    // final is const
    public static final double tax = 0.1;
    // double tax = 0.1;
}

class Invoice implements Payable {
    private String partNumber, partDescription;
    private int quantity;
    private double pricePerItem;

    // constructor
    public Invoice(String partNumber, String partDescription, int quantity, double pricePerItem) {
        this.partNumber = partNumber;
        this.partDescription = partDescription;
        setQuantity(quantity);
        setPricePerItem(pricePerItem);
    }

    // getters/ setters/
    public int getQuantity() {
        return quantity;
    }

    public double getPricePerItem() {
        return pricePerItem;
    }

    private void setQuantity(int quantity) {
        this.quantity = quantity > 0 ? quantity : 0;
    }

    private void setPricePerItem(double pricePerItem) {
        this.pricePerItem = pricePerItem > 0 ? pricePerItem : 0;
    }

    public double getPaymentAmount() {

        double price = quantity * pricePerItem;
        return (1 + Payable.tax) * price;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    @Override
    public String toString() {
        return partNumber + ": " + partDescription + " - " + quantity
                + ", $" + pricePerItem;
    }
}

class Employee implements Payable {
    private String firstName, lastName, ssn;
    private double weeklySalary;

    // constructor
    public Employee(String firstName, String lastName, String ssn, double weeklySalary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
        setWeekLySalary(weeklySalary);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public double getWeeklySalary() {
        return weeklySalary;
    }

    private void setWeekLySalary(double weeklySalary2) {
        this.weeklySalary = weeklySalary2 > 0 ? weeklySalary2 : 0;
    }

    @Override
    public double getPaymentAmount() {
        return (1 - tax) * (4 * weeklySalary);
    }

    @Override
    public String toString() {
        return "Name:" + firstName + " " + lastName + ", SSN:" + ssn +
                " Weekly salary:" + weeklySalary;
    }
}

public class PayableInvEmp {
    public static void main(String[] args) {
        // create an array
        Payable[] list = new Payable[10];
        // user input for 5 elements
        Scanner in = new Scanner(System.in);
        int choice;
        for (int i = 0; i < 3;) {
            System.out.println("Enter 1 for Invoice, 2 for employee:");
            choice = in.nextInt();
            if (choice == 1) {
                list[i++] = new Invoice(in.next(), in.next(), in.nextInt(), in.nextDouble());
            } else if (choice == 2)
                list[i++] = new Employee(in.next(), in.next(), in.next(), in.nextDouble());
            else
                System.out.println("Choice is either 1 or 2");
        }
        // display the info about objects chosen by the user
        char ch;
        System.out.println("Enter I to see Invoice details and E to see employee details:");
        ch = in.next().charAt(0);
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null)
                break;
            if (list[i] instanceof Invoice && ch == 'I')
                System.out.println(list[i]); // toString
            else if (list[i] instanceof Employee && ch == 'E')
                System.out.println(list[i]);
        }
        // display the part number for invoice with highest payment
        String res = getPNHSPayment(list);
        if (res != null)
            System.out.println("Part number for the invoice with highest payment:" + res);
        else
            System.out.println("No invoice was found in the list");

        // considering that a 0 sum means no employee
        double s = totalExpenses(list);
        if (s == 0)
            System.out.println("No employees found, so no expenses");
        else
            System.out.println("Total expenses are:" + s);
    }

    public static void display(char choice, Payable[] list) {
        // same code as in main
    }

    // method that returns partNumber of the invoice with the highest payment amount
    public static String getPNHSPayment(Payable[] list) {
        double max = 0, amount;
        int pos = -1;// because we mmight not have an invoice in the array
        for (int i = 0; i < list.length; i++) {
            if (list[i] != null && list[i] instanceof Invoice) {
                amount = list[i].getPaymentAmount();
                if (amount > max) {
                    max = amount;
                    pos = i;
                }
            }
        }
        if (pos != -1) // invoice found
            return ((Invoice) list[pos]).getPartNumber();
        // no invoice found
        // return ""; //empty string
        return null;
    }

    // method to compute the total expenses of employees
    public static double totalExpenses(Payable[] list) {
        double sum = 0;
        for (int i = 0; i < list.length; i++)
            if (list[i] != null) {
                if (list[i] instanceof Employee)
                    sum += list[i].getPaymentAmount();
            }

        return sum;
    }
}
