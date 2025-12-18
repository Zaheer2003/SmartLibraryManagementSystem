package fine;

public class StudentFineStrategy implements FineStrategy{
    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 50;
    }
}
