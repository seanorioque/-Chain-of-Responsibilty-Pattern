public class BPI_Atm {
    public static void main(String[] args) {
        ATMDispenseChain atmDispenser = new ATMDispenseChain();
        int amount = 2800; // Amount to be dispensed
        if (amount % 20 != 0) {
            System.out.println("Amount should be in multiples of 20s.");
            
        } else
        atmDispenser.dispense(new Currency(amount));
    }
}
