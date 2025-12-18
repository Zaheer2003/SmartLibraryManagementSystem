package fine;

public class GuestFineStrategy implements FineStrategy {
    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 100;
    }
}
