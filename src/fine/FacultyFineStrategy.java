package fine;


public class FacultyFineStrategy implements FineStrategy{
    @Override
    public double calculateFine(int daysLate) {
        return daysLate * 20;
    }
}
