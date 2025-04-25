# Simple Calendar Date Chooser

## Description
The **Simple Calendar Date Chooser** is a customizable Java Swing component (`JPanel`) for selecting dates. It provides a clean and intuitive calendar interface to pick single dates or date ranges, with extensive options to customize colors, fonts, behavior, and date formatting. Ideal for applications needing a date picker, it integrates seamlessly with NetBeans 8.2's UI Builder, allowing easy customization via the Properties window.

## Features
- **Selection Modes**: Choose single dates (`SINGLE`) or a date range (`RANGE`).
- **Customizable Date Format**:
  - Default format: `MMMM dd, yyyy` (e.g., "April 25, 2025").
  - Set custom formats (e.g., `yyyy-MM-dd`, `dd MMM yyyy`) using `setDateFormat(String pattern)`.
  - Retrieve the current format with `getDateFormat()`.
- **Customizable Colors**:
  - Backgrounds: Single selection, range start/end, range middle, today.
  - Foregrounds: Single selection, range start/end, range middle, today (separate for single/range modes), highlighted dates (default: RGB(0, 0, 0)).
  - Colors for headers, navigation buttons, navigation button hover (default: RGB(180, 180, 180)), and highlighted dates.
- **Customizable Fonts**: For month/year label, day headers, day buttons, and navigation buttons(I recommend to only change the font size).
- **Behavior Options**:
  - Disable past dates or Sundays.
  - Highlight specific dates (e.g., holidays) with a custom color.
  - Right-click to clear selections.
- **Navigation**: Switch months with arrow buttons (includes customizable hover effects for better UX).
- **NetBeans Support**: Edit properties directly in the NetBeans Properties window for quick customization.

![Calendar Appearance](https://i.imgur.com/ZqAV4ar.png "Simple Calendar Date Chooser in RANGE mode")

## Dependencies

The `SimpleCalendarDateChooser` class relies on the `DateSelectionListener` interface to notify listeners when dates are selected. Below is the source code for `DateSelectionListener.java`:

```java
import java.time.LocalDate;
import java.util.List;

public interface DateSelectionListener {
    void datesSelected(List<LocalDate> selectedDates);
}
```

## Downloading the JAR

The pre-built `SimpleCalendarDateChooser.jar` is available in the release area of this repository. Follow these steps to download it:

1. **Navigate to the Releases Section**:
   - Go to the repository’s release page: [repository-url]/releases (replace `[repository-url]` with the actual repository URL).

2. **Download the JAR**:
   - Find the latest release and download the `SimpleCalendarDateChooser.jar` file from the assets section.
   - Save the JAR file to a location accessible by your project (e.g., a `lib/` folder in your project directory).

The JAR includes both the `SimpleCalendarDateChooser` class and the `DateSelectionListener` interface, so you can start using it immediately in your Java projects.

## Example Usage

### 1. Using in NetBeans
1. **Add to Project**:
   - Compile `SimpleCalendarDateChooser.java` and add it to the NetBeans Palette (`Tools > Palette > Swing/AWT Components`).
2. **Add to Form**:
   - Drag `SimpleCalendarDateChooser` onto a JFrame Form.
3. **Customize**:
   - In the Properties window, set:
     - `selectionMode` to `RANGE`
     - `selectedColor` to red
     - `rangeStartEndColor` to green
     - `selectedSingleForeground` to yellow
     - `dateFormatPattern` to `dd MMM yyyy` (e.g., "25 Apr 2025")
     - `navButtonHoverForeground` to cyan (optional, defaults to RGB(180, 180, 180))
     - `highlightForeground` to red (optional, defaults to RGB(0, 0, 0))

   Below are screenshots of the Properties window showing the customization options for the `SimpleCalendarDateChooser`:

   ![Properties Window 1](https://i.imgur.com/UATibcK.png "Properties Window - Basic Settings")
   
   ![Properties Window 2](https://i.imgur.com/NkwVlyA.png "Properties Window - Color Settings")

### 2. Programmatic Usage with Source Code
```java
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calendar Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());
            frame.setLocationRelativeTo(null);

            // Create SimpleCalendarDateChooser
            SimpleCalendarDateChooser calendar = new SimpleCalendarDateChooser();
            calendar.setSelectionMode(SimpleCalendarDateChooser.SelectionMode.RANGE);
            calendar.setSelectedColor(Color.RED);
            calendar.setRangeStartEndColor(Color.GREEN);
            calendar.setRangeMiddleColor(new Color(150, 255, 150));
            calendar.setSelectedSingleForeground(Color.YELLOW);
            calendar.setRangeStartEndForeground(Color.BLACK);
            calendar.setTodaySingleForeground(Color.BLUE);
            calendar.setDateFormat("dd MMM yyyy"); // Custom date format
            calendar.setHighlightForeground(Color.RED); // Custom highlight foreground color
            // Nav button hover color defaults to RGB(180, 180, 180)

            // Highlight dates
            List<LocalDate> datesToHighlight = new ArrayList<>();
            datesToHighlight.add(LocalDate.of(2025, 4, 25));
            calendar.setHighlightedDates(datesToHighlight);

            // Button to show selected dates and format
            JButton getDatesButton = new JButton("Get Dates");
            getDatesButton.addActionListener(e -> {
                Object selected = calendar.getSelectedDatesAsStrings();
                String currentFormat = calendar.getDateFormat();
                String message = "Current Format: " + currentFormat +
                                 "\nSelected: " + (selected instanceof String ? selected : selected.toString());
                JOptionPane.showMessageDialog(frame, message);
            });

            // Button to change date format
            JButton changeFormatButton = new JButton("Change Format to yyyy-MM-dd");
            changeFormatButton.addActionListener(e -> {
                calendar.setDateFormat("yyyy-MM-dd");
                JOptionPane.showMessageDialog(frame, "Date format changed to: " + calendar.getDateFormat());
            });

            // Button to change hover color
            JButton changeHoverColorButton = new JButton("Change Hover Color to Cyan");
            changeHoverColorButton.addActionListener(e -> {
                calendar.setNavButtonHoverForeground(Color.CYAN);
                JOptionPane.showMessageDialog(frame, "Nav button hover color changed to Cyan");
            });

            // Button to change highlight foreground color
            JButton changeHighlightFgButton = new JButton("Change Highlight FG to Purple");
            changeHighlightFgButton.addActionListener(e -> {
                calendar.setHighlightForeground(Color.MAGENTA);
                JOptionPane.showMessageDialog(frame, "Highlight foreground color changed to Purple");
            });

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(getDatesButton);
            buttonPanel.add(changeFormatButton);
            buttonPanel.add(changeHoverColorButton);
            buttonPanel.add(changeHighlightFgButton);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.add(calendar, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
```

### 3. Using the JAR File
You can use the `SimpleCalendarDateChooser.jar` in your Java project by adding it to your classpath. This allows you to use the calendar component without including the source files directly.

#### Steps to Use the JAR:
1. **Add the JAR to Your Project**:
   - If using an IDE like IntelliJ IDEA or Eclipse:
     - Add the JAR to your project’s libraries:
       - IntelliJ: `File > Project Structure > Libraries > + > Java > Select SimpleCalendarDateChooser.jar`
       - Eclipse: `Right-click project > Build Path > Add External Archives > Select SimpleCalendarDateChooser.jar`
   - If using the command line:
     - Compile and run your code with the JAR in the classpath:
       ```bash
       javac -cp SimpleCalendarDateChooser.jar YourClass.java
       java -cp .:SimpleCalendarDateChooser.jar YourClass  # On Windows, use ; instead of :
       ```

2. **Example Code Using the JAR**:
   Below is an example of using the `SimpleCalendarDateChooser` from the JAR, including implementing the `DateSelectionListener` to handle date selection events:

   ```java
   import javax.swing.*;
   import java.awt.*;
   import java.time.LocalDate;
   import java.util.ArrayList;
   import java.util.List;

   public class CalendarDemoWithJar implements DateSelectionListener {
       private JLabel selectedDatesLabel;

       public CalendarDemoWithJar() {
           JFrame frame = new JFrame("Calendar Demo with JAR");
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setSize(400, 400);
           frame.setLayout(new BorderLayout());
           frame.setLocationRelativeTo(null);

           // Create SimpleCalendarDateChooser
           SimpleCalendarDateChooser calendar = new SimpleCalendarDateChooser();
           calendar.setSelectionMode(SimpleCalendarDateChooser.SelectionMode.RANGE);
           calendar.setSelectedColor(Color.RED);
           calendar.setRangeStartEndColor(Color.GREEN);
           calendar.setRangeMiddleColor(new Color(150, 255, 150));
           calendar.setSelectedSingleForeground(Color.YELLOW);
           calendar.setRangeStartEndForeground(Color.BLACK);
           calendar.setTodaySingleForeground(Color.BLUE);
           calendar.setDateFormat("dd MMM yyyy"); // Custom date format
           calendar.setHighlightForeground(Color.RED); // Custom highlight foreground color

           // Add DateSelectionListener
           calendar.addDateSelectionListener(this);

           // Highlight dates
           List<LocalDate> datesToHighlight = new ArrayList<>();
           datesToHighlight.add(LocalDate.of(2025, 4, 25));
           calendar.setHighlightedDates(datesToHighlight);

           // Label to display selected dates
           selectedDatesLabel = new JLabel("Selected Dates: None", SwingConstants.CENTER);
           selectedDatesLabel.setForeground(Color.DARK_GRAY);

           // Button to change date format
           JButton changeFormatButton = new JButton("Change Format to yyyy-MM-dd");
           changeFormatButton.addActionListener(e -> {
               calendar.setDateFormat("yyyy-MM-dd");
               JOptionPane.showMessageDialog(frame, "Date format changed to: " + calendar.getDateFormat());
           });

           // Button to change hover color
           JButton changeHoverColorButton = new JButton("Change Hover Color to Cyan");
           changeHoverColorButton.addActionListener(e -> {
               calendar.setNavButtonHoverForeground(Color.CYAN);
               JOptionPane.showMessageDialog(frame, "Nav button hover color changed to Cyan");
           });

           // Button to change highlight foreground color
           JButton changeHighlightFgButton = new JButton("Change Highlight FG to Purple");
           changeHighlightFgButton.addActionListener(e -> {
               calendar.setHighlightForeground(Color.MAGENTA);
               JOptionPane.showMessageDialog(frame, "Highlight foreground color changed to Purple");
           });

           JPanel buttonPanel = new JPanel();
           buttonPanel.add(changeFormatButton);
           buttonPanel.add(changeHoverColorButton);
           buttonPanel.add(changeHighlightFgButton);

           frame.add(calendar, BorderLayout.CENTER);
           frame.add(selectedDatesLabel, BorderLayout.NORTH);
           frame.add(buttonPanel, BorderLayout.SOUTH);
           frame.setVisible(true);
       }

       @Override
       public void datesSelected(List<LocalDate> selectedDates) {
           if (selectedDates.isEmpty()) {
               selectedDatesLabel.setText("Selected Dates: None");
           } else {
               StringBuilder displayText = new StringBuilder("Selected Dates: ");
               for (LocalDate date : selectedDates) {
                   displayText.append(date.toString()).append(" ");
               }
               selectedDatesLabel.setText(displayText.toString());
           }
       }

       public static void main(String[] args) {
           SwingUtilities.invokeLater(CalendarDemoWithJar::new);
       }
   }
   ```

#### Notes on Using the JAR:
- **Classpath**: Ensure the JAR is in your classpath when compiling and running your application.
- **DateSelectionListener**: The JAR includes the `DateSelectionListener` interface, which you must implement to handle date selection events, as shown in the example above.
- **No External Dependencies**: The JAR uses only standard Java libraries (Swing and `java.time`), so no additional dependencies are required.

### 4. Testing
- **Single Mode**: Set `selectionMode` to `SINGLE`, click a date—it turns red with yellow text (e.g., "25 Apr 2025" with the custom format).
- **Range Mode**: Set `selectionMode` to `RANGE`, select two dates—start/end turn green with black text, middle dates are light green (e.g., ["25 Apr 2025", "26 Apr 2025"]).
- **Date Format Change**: Click "Change Format to yyyy-MM-dd", then select dates—they’ll display as "2025-04-25".
- **Hover Color Change**: Hover over the navigation buttons (initially light gray, RGB(180, 180, 180)), then click "Change Hover Color to Cyan"—hovering should now show cyan.
- **Highlight Foreground Change**: Highlighted dates (e.g., April 25, 2025) initially have red text; click "Change Highlight FG to Purple"—highlighted dates should now have purple text.
- **Clear Selection**: Right-click to clear selected dates.

![Calendar Interaction](https://i.imgur.com/CaJI9AD.gif "Selecting a range and clearing selection")
