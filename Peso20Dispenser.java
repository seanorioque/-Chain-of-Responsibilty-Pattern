public class Peso20Dispenser implements DispenseChain {
    private DispenseChain chain;


    @Override
    public void setNextChain(DispenseChain nextChain) {
        this.chain = nextChain;
    }


    @Override
    public void dispense(Currency cur) {
        if (cur.getAmount() >= 20) {
            int num = cur.getAmount() / 20; // 1
            int remainder = cur.getAmount() % 20; //300
            System.out.println("Dispensing " + num + " 20 bills");
            if (remainder != 0) {
                System.out.println("Cannot dispense remaining amount: " + remainder);
            }
        } else {
            System.out.println("Cannot dispense amount: " + cur.getAmount());
        }
    }
}
