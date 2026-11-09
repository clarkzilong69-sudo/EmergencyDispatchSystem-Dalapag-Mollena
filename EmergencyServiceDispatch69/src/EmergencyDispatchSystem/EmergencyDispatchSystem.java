
package EmergencyDispatchSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmergencyDispatchSystem extends JFrame {

    static class Incident {
        String id;
        String reporter;
        String location;
        String emergencyType;
        int priority;
        String description;

        Incident(String id, String reporter, String location,
                 String emergencyType, int priority, String description) {
            this.id = id;
            this.reporter = reporter;
            this.location = location;
            this.emergencyType = emergencyType;
            this.priority = priority;
            this.description = description;
        }
    }

    private Incident[] incidents = new Incident[100];
    private int count = 0;

    private JTextField txtID, txtReporter, txtLocation, txtSearch;
    private JTextArea txtDescription;
    private JComboBox<String> cmbEmergency, cmbPriority;
    private JTable incidentTable;
    private DefaultTableModel tableModel;

    public EmergencyDispatchSystem() {

        setTitle("Emergency Service Dispatch System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel(
            "EMERGENCY SERVICE DISPATCH SYSTEM",
            SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JPanel header = new JPanel();
        header.setBackground(new Color(150, 25, 25));
        header.add(title);

        add(header, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder(
            "Incident Registration"
        ));

        txtID = new JTextField();
        txtReporter = new JTextField();
        txtLocation = new JTextField();

        cmbEmergency = new JComboBox<>(new String[]{
            "Road Accident",
            "Fire",
            "Medical Emergency",
            "Crime",
            "Natural Disaster",
            "Other"
        });

        cmbPriority = new JComboBox<>(new String[]{
            "1 - High",
            "2 - Medium",
            "3 - Low"
        });

        txtDescription = new JTextArea(3, 20);
        txtDescription.setLineWrap(true);

        form.add(new JLabel("Incident ID:"));
        form.add(txtID);

        form.add(new JLabel("Reporter Name:"));
        form.add(txtReporter);

        form.add(new JLabel("Location:"));
        form.add(txtLocation);

        form.add(new JLabel("Emergency Type:"));
        form.add(cmbEmergency);

        form.add(new JLabel("Priority:"));
        form.add(cmbPriority);

        form.add(new JLabel("Description:"));
        form.add(new JScrollPane(txtDescription));

        JPanel buttons = new JPanel(new FlowLayout());

        JButton btnRegister = new JButton("Register Incident");
        JButton btnDispatch = new JButton("Dispatch Incident");
        JButton btnSearch = new JButton("Search Incident");
        JButton btnClear = new JButton("Clear");

        txtSearch = new JTextField(12);

        buttons.add(btnRegister);
        buttons.add(btnDispatch);
        buttons.add(new JLabel("Search ID:"));
        buttons.add(txtSearch);
        buttons.add(btnSearch);
        buttons.add(btnClear);

        String[] columns = {
            "Incident ID",
            "Reporter",
            "Location",
            "Emergency Type",
            "Priority",
            "Description"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        incidentTable = new JTable(tableModel);
        incidentTable.setRowHeight(25);

        JScrollPane tableScroll = new JScrollPane(incidentTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder(
            "Registered Emergency Incidents"
        ));

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        center.add(form, BorderLayout.NORTH);
        center.add(buttons, BorderLayout.CENTER);
        center.add(tableScroll, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        btnRegister.addActionListener(e -> registerIncident());

        btnDispatch.addActionListener(e -> dispatchIncident());

        btnSearch.addActionListener(e -> searchIncident());

        btnClear.addActionListener(e -> clearFields());
    }

    private void registerIncident() {

        String id = txtID.getText().trim();
        String reporter = txtReporter.getText().trim();
        String location = txtLocation.getText().trim();
        String description = txtDescription.getText().trim();

        if (id.isEmpty() || reporter.isEmpty()
                || location.isEmpty() || description.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Please complete all required fields."
            );
            return;
        }

        if (count >= incidents.length) {
            JOptionPane.showMessageDialog(
                this,
                "Incident queue is full."
            );
            return;
        }

        if (findIncident(id) != -1) {
            JOptionPane.showMessageDialog(
                this,
                "Incident ID already exists!"
            );
            return;
        }

        int priority = cmbPriority.getSelectedIndex() + 1;

        Incident newIncident = new Incident(
            id,
            reporter,
            location,
            cmbEmergency.getSelectedItem().toString(),
            priority,
            description
        );

        incidents[count] = newIncident;
        count++;

        refreshTable();

        JOptionPane.showMessageDialog(
            this,
            "Incident successfully registered!"
        );

        clearFields();
    }

    private void dispatchIncident() {

        if (count == 0) {
            JOptionPane.showMessageDialog(
                this,
                "No incidents available for dispatch."
            );
            return;
        }

        int highestIndex = 0;

        for (int i = 1; i < count; i++) {

            if (incidents[i].priority
                    < incidents[highestIndex].priority) {

                highestIndex = i;
            }
        }

        Incident dispatched = incidents[highestIndex];

        JOptionPane.showMessageDialog(
            this,
            "Dispatching Emergency Incident!\n\n"
            + "Incident ID: " + dispatched.id
            + "\nReporter: " + dispatched.reporter
            + "\nLocation: " + dispatched.location
            + "\nPriority: " + dispatched.priority
        );

        for (int i = highestIndex; i < count - 1; i++) {
            incidents[i] = incidents[i + 1];
        }

        count--;
        incidents[count] = null;

        refreshTable();
    }

    private int findIncident(String id) {

        for (int i = 0; i < count; i++) {

            if (incidents[i].id.equalsIgnoreCase(id)) {
                return i;
            }
        }

        return -1;
    }

    private void searchIncident() {

        String id = txtSearch.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter an Incident ID."
            );
            return;
        }

        int index = findIncident(id);

        if (index != -1) {

            Incident found = incidents[index];

            JOptionPane.showMessageDialog(
                this,
                "Incident Found!\n\n"
                + "ID: " + found.id
                + "\nReporter: " + found.reporter
                + "\nLocation: " + found.location
                + "\nEmergency: " + found.emergencyType
                + "\nPriority: " + found.priority
                + "\nDescription: " + found.description
            );

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Incident ID not found!"
            );
        }
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        for (int i = 0; i < count; i++) {

            Incident incident = incidents[i];

            tableModel.addRow(new Object[]{
                incident.id,
                incident.reporter,
                incident.location,
                incident.emergencyType,
                incident.priority,
                incident.description
            });
        }
    }

    private void clearFields() {

        txtID.setText("");
        txtReporter.setText("");
        txtLocation.setText("");
        txtDescription.setText("");
        txtSearch.setText("");

        cmbEmergency.setSelectedIndex(0);
        cmbPriority.setSelectedIndex(0);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new EmergencyDispatchSystem().setVisible(true);
        });
    }
}
