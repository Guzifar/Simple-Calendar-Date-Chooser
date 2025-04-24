/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guzifar.main.scdc;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.time.*;
import java.util.Locale;

import java.awt.Color;
import java.awt.Font;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Guzifar
 */
public class SimpleCalendarDateChooser extends JPanel {
    private LocalDate currentDate;
    private LocalDate today;
    private JPanel calendarGrid;
    private JPanel navPanel;
    private JLabel monthYearLabel;
    private Color headerColor;
    private Color highlightColor;
    private Color navBarColor;
    private Color monthYearForeground;
    private Color headerForeground;
    private Color navButtonForeground;
    private Color dayButtonForeground;
    private Color todayColor;
    private Color selectedColor;
    private Color rangeStartEndColor;
    private Color rangeMiddleColor;
    private Color selectedSingleForeground;
    private Color rangeStartEndForeground;
    private Color rangeMiddleForeground;
    private Color todaySingleForeground;
    private Color todayRangeForeground;
    private List<LocalDate> selectedDates;
    private Set<LocalDate> highlightedDates;
    private Font monthYearFont;
    private Font headerFont;
    private Font dayButtonFont;
    private Font navButtonFont;
    private JButton prevButton;
    private JButton nextButton;
    private boolean disablePastDates;
    private boolean disableSundays;
    private boolean enableHighlightedDateSelection;
    private SelectionMode selectionMode;
    private LocalDate rangeStart;
    private LocalDate rangeEnd;

    public enum SelectionMode {
        SINGLE, RANGE
    }

    public SimpleCalendarDateChooser() {
        this(Color.DARK_GRAY, Color.YELLOW, new Color(240, 240, 240),
             new Color(50, 50, 50), Color.BLACK, Color.BLACK, Color.BLACK,
             new Color(200, 255, 200), Color.ORANGE, Color.MAGENTA, null,
             Color.WHITE, Color.WHITE, Color.WHITE, Color.BLACK, Color.BLACK,
             new Font("Arial", Font.BOLD, 16),
             new Font("Arial", Font.PLAIN, 12),
             new Font("Arial", Font.PLAIN, 12),
             new Font("Segoe UI Symbol", Font.BOLD, 14),
             true, true, false, SelectionMode.SINGLE);
    }

    public SimpleCalendarDateChooser(Color headerColor, Color highlightColor, Color navBarColor,
                         Color monthYearForeground, Color headerForeground,
                         Color navButtonForeground, Color dayButtonForeground,
                         Color todayColor, Color selectedColor,
                         Color rangeStartEndColor, Color rangeMiddleColor,
                         Color selectedSingleForeground, Color rangeStartEndForeground, Color rangeMiddleForeground,
                         Color todaySingleForeground, Color todayRangeForeground,
                         Font monthYearFont, Font headerFont,
                         Font dayButtonFont, Font navButtonFont,
                         boolean disablePastDates, boolean disableSundays,
                         boolean enableHighlightedDateSelection, SelectionMode selectionMode) {
        this.headerColor = headerColor != null ? headerColor : Color.DARK_GRAY;
        this.highlightColor = highlightColor != null ? highlightColor : Color.YELLOW;
        this.navBarColor = navBarColor != null ? navBarColor : new Color(240, 240, 240);
        this.monthYearForeground = monthYearForeground != null ? monthYearForeground : new Color(50, 50, 50);
        this.headerForeground = headerForeground != null ? headerForeground : Color.BLACK;
        this.navButtonForeground = navButtonForeground != null ? navButtonForeground : Color.BLACK;
        this.dayButtonForeground = dayButtonForeground != null ? dayButtonForeground : Color.BLACK;
        this.todayColor = todayColor != null ? todayColor : new Color(200, 255, 200);
        this.selectedColor = selectedColor != null ? selectedColor : Color.ORANGE;
        this.rangeStartEndColor = rangeStartEndColor != null ? rangeStartEndColor : Color.MAGENTA;
        this.rangeMiddleColor = rangeMiddleColor != null ? rangeMiddleColor : getLighterColor(this.rangeStartEndColor);
        this.selectedSingleForeground = selectedSingleForeground != null ? selectedSingleForeground : Color.WHITE;
        this.rangeStartEndForeground = rangeStartEndForeground != null ? rangeStartEndForeground : Color.WHITE;
        this.rangeMiddleForeground = rangeMiddleForeground != null ? rangeMiddleForeground : Color.WHITE;
        this.todaySingleForeground = todaySingleForeground != null ? todaySingleForeground : Color.BLACK;
        this.todayRangeForeground = todayRangeForeground != null ? todayRangeForeground : Color.BLACK;
        this.monthYearFont = monthYearFont != null ? monthYearFont : new Font("Arial", Font.BOLD, 16);
        this.headerFont = headerFont != null ? headerFont : new Font("Arial", Font.PLAIN, 12);
        this.dayButtonFont = dayButtonFont != null ? dayButtonFont : new Font("Arial", Font.PLAIN, 12);
        this.navButtonFont = navButtonFont != null ? navButtonFont : new Font("Segoe UI Symbol", Font.BOLD, 14);
        this.disablePastDates = disablePastDates;
        this.disableSundays = disableSundays;
        this.enableHighlightedDateSelection = enableHighlightedDateSelection;
        this.selectionMode = selectionMode != null ? selectionMode : SelectionMode.SINGLE;
        currentDate = LocalDate.now();
        today = LocalDate.now();
        highlightedDates = new HashSet<>();
        selectedDates = new ArrayList<>();
        rangeStart = null;
        rangeEnd = null;
        initializeUI();
        updateCalendar();
    }

    // Helper method to create a lighter color
    private Color getLighterColor(Color color) {
        int r = Math.min(255, color.getRed() + 50);
        int g = Math.min(255, color.getGreen() + 50);
        int b = Math.min(255, color.getBlue() + 50);
        return new Color(r, g, b);
    }

    // Getter for todayDate
    public LocalDate getTodayDate() {
        return today;
    }

    // Getter and Setter for todayColor
    public Color getTodayColor() {
        return todayColor;
    }

    public void setTodayColor(Color todayColor) {
        this.todayColor = todayColor != null ? todayColor : new Color(200, 255, 200);
        updateCalendar();
    }

    // Getter and Setter for selectedColor (for single selection background)
    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor != null ? selectedColor : Color.ORANGE;
        updateCalendar();
    }

    // Getter and Setter for rangeStartEndColor
    public Color getRangeStartEndColor() {
        return rangeStartEndColor;
    }

    public void setRangeStartEndColor(Color rangeStartEndColor) {
        this.rangeStartEndColor = rangeStartEndColor != null ? rangeStartEndColor : Color.MAGENTA;
        if (this.rangeMiddleColor.equals(getLighterColor(this.rangeStartEndColor))) {
            this.rangeMiddleColor = getLighterColor(this.rangeStartEndColor);
        }
        updateCalendar();
    }

    // Getter and Setter for rangeMiddleColor
    public Color getRangeMiddleColor() {
        return rangeMiddleColor;
    }

    public void setRangeMiddleColor(Color rangeMiddleColor) {
        this.rangeMiddleColor = rangeMiddleColor != null ? rangeMiddleColor : getLighterColor(this.rangeStartEndColor);
        updateCalendar();
    }

    // Getter and Setter for selectedSingleForeground
    public Color getSelectedSingleForeground() {
        return selectedSingleForeground;
    }

    public void setSelectedSingleForeground(Color selectedSingleForeground) {
        this.selectedSingleForeground = selectedSingleForeground != null ? selectedSingleForeground : Color.WHITE;
        updateCalendar();
    }

    // Getter and Setter for rangeStartEndForeground
    public Color getRangeStartEndForeground() {
        return rangeStartEndForeground;
    }

    public void setRangeStartEndForeground(Color rangeStartEndForeground) {
        this.rangeStartEndForeground = rangeStartEndForeground != null ? rangeStartEndForeground : Color.WHITE;
        updateCalendar();
    }

    // Getter and Setter for rangeMiddleForeground
    public Color getRangeMiddleForeground() {
        return rangeMiddleForeground;
    }

    public void setRangeMiddleForeground(Color rangeMiddleForeground) {
        this.rangeMiddleForeground = rangeMiddleForeground != null ? rangeMiddleForeground : Color.WHITE;
        updateCalendar();
    }

    // Getter and Setter for todaySingleForeground
    public Color getTodaySingleForeground() {
        return todaySingleForeground;
    }

    public void setTodaySingleForeground(Color todaySingleForeground) {
        this.todaySingleForeground = todaySingleForeground != null ? todaySingleForeground : Color.BLACK;
        updateCalendar();
    }

    // Getter and Setter for todayRangeForeground
    public Color getTodayRangeForeground() {
        return todayRangeForeground;
    }

    public void setTodayRangeForeground(Color todayRangeForeground) {
        this.todayRangeForeground = todayRangeForeground != null ? todayRangeForeground : Color.BLACK;
        updateCalendar();
    }

    // Getter for selectedDates
    public List<LocalDate> getSelectedDates() {
        return new ArrayList<>(selectedDates);
    }

    // Getter and Setter for disablePastDates
    public boolean isDisablePastDates() {
        return disablePastDates;
    }

    public void setDisablePastDates(boolean disablePastDates) {
        this.disablePastDates = disablePastDates;
        updateCalendar();
    }

    // Getter and Setter for disableSundays
    public boolean isDisableSundays() {
        return disableSundays;
    }

    public void setDisableSundays(boolean disableSundays) {
        this.disableSundays = disableSundays;
        updateCalendar();
    }

    // Getter and Setter for enableHighlightedDateSelection
    public boolean isEnableHighlightedDateSelection() {
        return enableHighlightedDateSelection;
    }

    public void setEnableHighlightedDateSelection(boolean enableHighlightedDateSelection) {
        this.enableHighlightedDateSelection = enableHighlightedDateSelection;
        updateCalendar();
    }

    // Getter and Setter for selectionMode
    public SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(SelectionMode selectionMode) {
        this.selectionMode = selectionMode != null ? selectionMode : SelectionMode.SINGLE;
        selectedDates.clear();
        rangeStart = null;
        rangeEnd = null;
        updateCalendar();
    }

    // Getter and Setter for monthYearForeground
    public Color getMonthYearForeground() {
        return monthYearForeground;
    }

    public void setMonthYearForeground(Color monthYearForeground) {
        this.monthYearForeground = monthYearForeground != null ? monthYearForeground : new Color(50, 50, 50);
        monthYearLabel.setForeground(this.monthYearForeground);
        revalidate();
        repaint();
    }

    // Getter and Setter for headerForeground
    public Color getHeaderForeground() {
        return headerForeground;
    }

    public void setHeaderForeground(Color headerForeground) {
        this.headerForeground = headerForeground != null ? headerForeground : Color.BLACK;
        updateCalendar();
    }

    // Getter and Setter for navButtonForeground
    public Color getNavButtonForeground() {
        return navButtonForeground;
    }

    public void setNavButtonForeground(Color navButtonForeground) {
        this.navButtonForeground = navButtonForeground != null ? navButtonForeground : Color.BLACK;
        styleNavButton(prevButton);
        styleNavButton(nextButton);
        revalidate();
        repaint();
    }

    // Getter and Setter for dayButtonForeground
    public Color getDayButtonForeground() {
        return dayButtonForeground;
    }

    public void setDayButtonForeground(Color dayButtonForeground) {
        this.dayButtonForeground = dayButtonForeground != null ? dayButtonForeground : Color.BLACK;
        updateCalendar();
    }

    // Getter and Setter for navBarColor
    public Color getNavBarColor() {
        return navBarColor;
    }

    public void setNavBarColor(Color navBarColor) {
        this.navBarColor = navBarColor != null ? navBarColor : new Color(240, 240, 240);
        navPanel.setBackground(this.navBarColor);
        revalidate();
        repaint();
    }

    // Getter and Setter for monthYearFont
    public Font getMonthYearFont() {
        return monthYearFont;
    }

    public void setMonthYearFont(Font monthYearFont) {
        this.monthYearFont = monthYearFont != null ? monthYearFont : new Font("Arial", Font.BOLD, 16);
        monthYearLabel.setFont(this.monthYearFont);
        revalidate();
        repaint();
    }

    // Getter and Setter for headerFont
    public Font getHeaderFont() {
        return headerFont;
    }

    public void setHeaderFont(Font headerFont) {
        this.headerFont = headerFont != null ? headerFont : new Font("Arial", Font.PLAIN, 12);
        updateCalendar();
    }

    // Getter and Setter for dayButtonFont
    public Font getDayButtonFont() {
        return dayButtonFont;
    }

    public void setDayButtonFont(Font dayButtonFont) {
        this.dayButtonFont = dayButtonFont != null ? dayButtonFont : new Font("Arial", Font.PLAIN, 12);
        updateCalendar();
    }

    // Getter and Setter for navButtonFont
    public Font getNavButtonFont() {
        return navButtonFont;
    }

    public void setNavButtonFont(Font navButtonFont) {
        this.navButtonFont = navButtonFont != null ? navButtonFont : new Font("Segoe UI Symbol", Font.BOLD, 14);
        styleNavButton(prevButton);
        styleNavButton(nextButton);
        revalidate();
        repaint();
    }

    // Getter for headerColor
    public Color getHeaderColor() {
        return headerColor;
    }

    // Setter for headerColor
    public void setHeaderColor(Color headerColor) {
        this.headerColor = headerColor != null ? headerColor : Color.DARK_GRAY;
        updateCalendar();
    }

    // Getter for highlightColor
    public Color getHighlightColor() {
        return highlightColor;
    }

    // Setter for highlightColor
    public void setHighlightColor(Color highlightColor) {
        this.highlightColor = highlightColor != null ? highlightColor : Color.YELLOW;
        updateCalendar();
    }

    // Method to set highlighted dates
    public void setHighlightedDates(List<? extends LocalDate> dates) {
        highlightedDates.clear();
        try {
            if (dates != null) {
                for (LocalDate date : dates) {
                    if (date != null) {
                        highlightedDates.add(date);
                    }
                }
            }
            updateCalendar();
        } catch (Exception e) {
            showError("Error setting highlighted dates: " + e.getMessage());
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        setBackground(new Color(240, 240, 240));
        setPreferredSize(new Dimension(300, 300));

        monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setFont(monthYearFont);
        monthYearLabel.setForeground(monthYearForeground);

        navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
        navPanel.setBackground(navBarColor);

        prevButton = new JButton("◀");
        styleNavButton(prevButton);
        prevButton.addActionListener(e -> {
            try {
                currentDate = currentDate.minusMonths(1);
                updateCalendar();
            } catch (Exception ex) {
                showError("Error navigating to previous month.");
            }
        });

        nextButton = new JButton("▶");
        styleNavButton(nextButton);
        nextButton.addActionListener(e -> {
            try {
                currentDate = currentDate.plusMonths(1);
                updateCalendar();
            } catch (Exception ex) {
                showError("Error navigating to next month.");
            }
        });

        navPanel.add(prevButton);
        navPanel.add(monthYearLabel);
        navPanel.add(nextButton);

        calendarGrid = new JPanel();
        calendarGrid.setBackground(Color.WHITE);
        calendarGrid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(navPanel, BorderLayout.NORTH);
        add(calendarGrid, BorderLayout.CENTER);
    }

    private void updateCalendar() {
        try {
            calendarGrid.removeAll();

            today = LocalDate.now();

            int daysInMonth = currentDate.lengthOfMonth();
            LocalDate firstOfMonth = currentDate.withDayOfMonth(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
            int startOffset = (dayOfWeek == 7) ? 0 : dayOfWeek;
            int totalCells = startOffset + daysInMonth;
            int rowsNeeded = 1 + (int) Math.ceil(totalCells / 7.0);
            calendarGrid.setLayout(new GridLayout(rowsNeeded, 7, 2, 2));

            String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            for (String day : days) {
                JLabel label = new JLabel(day, SwingConstants.CENTER);
                label.setFont(headerFont);
                label.setForeground(headerForeground);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                calendarGrid.add(label);
            }

            for (int i = 0; i < startOffset; i++) {
                calendarGrid.add(new JLabel(""));
            }

            for (int day = 1; day <= daysInMonth; day++) {
                LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
                CustomButton dayButton = new CustomButton(String.valueOf(day), date);

                boolean isHighlighted = highlightedDates.contains(date);
                boolean isToday = date.equals(today);
                boolean isSelected = selectedDates.contains(date);

                if (isSelected) {
                    dayButton.setEnabled(true);
                    if (selectionMode == SelectionMode.SINGLE) {
                        dayButton.setBackground(selectedColor);
                        dayButton.setForeground(selectedSingleForeground);
                    } else if (selectionMode == SelectionMode.RANGE) {
                        if (date.equals(rangeStart) || date.equals(rangeEnd)) {
                            dayButton.setBackground(rangeStartEndColor);
                            dayButton.setForeground(rangeStartEndForeground);
                        } else {
                            dayButton.setBackground(rangeMiddleColor);
                            dayButton.setForeground(rangeMiddleForeground);
                        }
                    }
                } else if (isHighlighted) {
                    dayButton.setEnabled(enableHighlightedDateSelection);
                    dayButton.setBackground(highlightColor);
                    dayButton.setForeground(dayButtonForeground);
                } else if (isToday) {
                    dayButton.setEnabled(true);
                    dayButton.setBackground(todayColor);
                    dayButton.setForeground(selectionMode == SelectionMode.SINGLE ? todaySingleForeground : todayRangeForeground);
                } else {
                    if (disablePastDates && date.isBefore(today)) {
                        dayButton.setEnabled(false);
                        dayButton.setBackground(new Color(220, 220, 220));
                        dayButton.setForeground(new Color(150, 150, 150));
                    } else if (disableSundays && date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        dayButton.setEnabled(false);
                        dayButton.setBackground(new Color(220, 220, 220));
                        dayButton.setForeground(new Color(150, 150, 150));
                    } else if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        dayButton.setEnabled(true);
                        dayButton.setBackground(Color.WHITE);
                        dayButton.setForeground(dayButtonForeground);
                    } else {
                        dayButton.setEnabled(true);
                        dayButton.setBackground(Color.WHITE);
                        dayButton.setForeground(dayButtonForeground);
                    }
                }

                if (dayButton.isEnabled()) {
                    dayButton.addActionListener(e -> {
                        if (selectionMode == SelectionMode.SINGLE) {
                            selectedDates.clear();
                            selectedDates.add(date);
                            rangeStart = null;
                            rangeEnd = null;
                        } else if (selectionMode == SelectionMode.RANGE) {
                            if (rangeStart == null) {
                                selectedDates.clear();
                                rangeStart = date;
                                selectedDates.add(date);
                                rangeEnd = null;
                            } else {
                                LocalDate newRangeEnd = date;
                                selectedDates.clear();
                                rangeEnd = newRangeEnd;
                                LocalDate start = rangeStart.isBefore(rangeEnd) ? rangeStart : rangeEnd;
                                LocalDate end = rangeStart.isBefore(rangeEnd) ? rangeEnd : rangeStart;
                                LocalDate current = start;
                                while (!current.isAfter(end)) {
                                    selectedDates.add(current);
                                    current = current.plusDays(1);
                                }
                                rangeStart = start;
                                rangeEnd = end;
                            }
                        }
                        updateCalendar();
                    });
                }

                calendarGrid.add(dayButton);
            }

            monthYearLabel.setText(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.US) + " " + currentDate.getYear());

            calendarGrid.revalidate();
            calendarGrid.repaint();
        } catch (Exception ex) {
            showError("Error updating calendar.");
        }
    }

    private void styleNavButton(JButton button) {
        button.setFont(navButtonFont);
        button.setForeground(navButtonForeground);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(180, 180, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(navButtonForeground);
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private class CustomButton extends JButton {
        private LocalDate date;
        private Color originalBackground;
        private Color originalForeground;

        public CustomButton(String text, LocalDate date) {
            super(text);
            this.date = date;
            setFont(dayButtonFont);
            setBackground(Color.WHITE);
            setForeground(dayButtonForeground);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            setFocusPainted(false);
            setMargin(new Insets(2, 2, 2, 2));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        originalBackground = getBackground();
                        originalForeground = getForeground();
                        if (originalBackground.equals(selectedColor) || originalBackground.equals(rangeStartEndColor)) {
                            setBackground(originalBackground.darker());
                            setForeground(originalForeground.darker());
                        } else if (originalBackground.equals(rangeMiddleColor)) {
                            setBackground(rangeMiddleColor.darker());
                            setForeground(originalForeground.darker());
                        } else if (originalBackground.equals(todayColor)) {
                            setBackground(todayColor.darker());
                            setForeground(originalForeground.darker());
                        } else {
                            setBackground(new Color(230, 230, 230));
                            setForeground(dayButtonForeground);
                        }
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (isEnabled()) {
                        setBackground(originalBackground != null ? originalBackground : Color.WHITE);
                        setForeground(originalForeground != null ? originalForeground : dayButtonForeground);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (isEnabled() && e.getButton() == MouseEvent.BUTTON3) { // Right-click
                        selectedDates.clear();
                        rangeStart = null;
                        rangeEnd = null;
                        updateCalendar();
                    }
                }
            });
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            setForeground(enabled ? (getForeground() != null ? getForeground() : dayButtonForeground) : new Color(150, 150, 150));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Calendar Demo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 400);
                frame.setLayout(new BorderLayout());
                frame.setLocationRelativeTo(null);
                //Flat laf
                FlatLightLaf.setup();
                FlatLightLaf.setUseNativeWindowDecorations(true);
                UIManager.setLookAndFeel(new FlatLightLaf());
                UIManager.put("TitlePane.font", new java.awt.Font("Segoe UI Semibold", 0, 12));
                UIManager.put("TitlePane.showIcon", false);
                UIManager.put("TitlePane.titleMargins", new java.awt.Insets(0, 8, 0, 0));
                
                // Create CalendarPanel
                SimpleCalendarDateChooser calendar = new SimpleCalendarDateChooser();
                calendar.setSelectionMode(SimpleCalendarDateChooser.SelectionMode.RANGE);
                calendar.setSelectedColor(Color.RED);
                calendar.setRangeStartEndColor(Color.GREEN);
                calendar.setRangeMiddleColor(new Color(150, 255, 150));
                calendar.setSelectedSingleForeground(Color.YELLOW);
                calendar.setRangeStartEndForeground(Color.BLACK);
                calendar.setTodaySingleForeground(Color.BLUE);
                
                // Highlight dates
                List datesToHighlight = new ArrayList<LocalDate>();
                datesToHighlight.add(LocalDate.of(2025, 4, 25));
                calendar.setHighlightedDates(datesToHighlight);
                
                // Button to show selected dates
                JButton getDatesButton = new JButton("Get Dates");
                getDatesButton.addActionListener(e -> {
                    String message = "Selected: " + calendar.getSelectedDates();
                    JOptionPane.showMessageDialog(frame, message);
                });
                frame.add(getDatesButton, BorderLayout.SOUTH);
                
                frame.add(calendar, BorderLayout.CENTER);
                frame.setVisible(true);
            } catch (UnsupportedLookAndFeelException ex) {
                Logger.getLogger(SimpleCalendarDateChooser.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}