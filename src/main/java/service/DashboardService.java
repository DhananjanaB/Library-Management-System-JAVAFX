package service;

import repository.DashboardRepository;

import java.sql.SQLException;

public class DashboardService {

    private final DashboardRepository dashboardRepository = new DashboardRepository();

    public long getTotalBooks() throws SQLException {
        return dashboardRepository.countBooks();
    }

    public long getTotalMembers() throws SQLException {
        return dashboardRepository.countMembers();
    }

    public long getBorrowed() throws SQLException {
        return dashboardRepository.countBorrowed();
    }

    public long getOverdue() throws SQLException {
        return dashboardRepository.countOverdue();
    }
}