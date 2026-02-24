public class BPI_Atm {
    public static void main(String[] args) {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        int amount = 250; // Amount to be dispensed
        if (amount % 10 != 0) {
            System.out.println("Amount should be in multiples of 20s.");
            
        } else
        atmDispenser.dispense(new Currency(amount));
    }
}
