import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TSP {

    public static void main(String[] args) {

        // GUI
        JFrame frame = new JFrame();
        frame.setSize(1200, 900);
        frame.setLocation(100, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = frame.getContentPane();
        container.setLayout(new BorderLayout(6,6));
        container.setBackground(Color.CYAN);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.YELLOW));

        // left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(6, 6));
        leftPanel.setBorder(new LineBorder(Color.BLACK, 2));
        leftPanel.setBackground(Color.RED);

        // middle panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setBorder(new LineBorder(Color.BLACK, 2));

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new LineBorder(Color.BLACK, 2));
        bottomPanel.setBackground(Color.GREEN);

        // input text area for left panel
        JTextArea inputTextArea = new JTextArea(48, 20);
        inputTextArea.setText("Paste orders in here..");
        JScrollPane scroll = new JScrollPane(inputTextArea);

        // compute button for left panel
        JButton button = new JButton("Compute");
        button.setPreferredSize(new Dimension(400, 58));

        // output text field for bottom panel
        JTextArea outputTextField = new JTextArea(1, 50);
        JScrollPane outputScroll = new JScrollPane(outputTextField);

        // output text area for route and addresses
        JTextArea outputAddress = new JTextArea();
        JScrollPane outputAddressScroll = new JScrollPane(outputAddress);

        // add components to the frame
        middlePanel.add(outputAddressScroll, BorderLayout.CENTER);
        leftPanel.add(scroll, BorderLayout.NORTH);
        leftPanel.add(button, BorderLayout.SOUTH);
        bottomPanel.add(outputScroll, BorderLayout.CENTER);
        container.add(middlePanel, BorderLayout.CENTER);
        container.add(leftPanel, BorderLayout.WEST);
        container.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // will hold the delivery locations
        List<Location> deliveries = new ArrayList<>();

        // when the "compute" button is clicked
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // read the input line by line
                for (String line : inputTextArea.getText().split("\\n")) {
                    String[] arr = line.split(",");
                    // extract data
                    double longitude = Double.parseDouble(arr[3]);
                    double latitude = Double.parseDouble(arr[4]);
                    int orderNumber = Integer.parseInt(arr[0]);
                    String address = arr[1];
                    int minutesWaiting = Integer.parseInt(arr[2]);
                    // add location objects to the list
                    deliveries.add(new Location(longitude, latitude, orderNumber, minutesWaiting, address));
                }

                // call nearest neighbour algorithm and pass in the list of locations
                // nearestNeighbour returns a list of locations
                List<Location> route = nearestNeighbour(deliveries);

                String output = ""; // will hold the output string of order numbers
                for (int i = 0; i < route.size(); i++) {
                    if (i != route.size()-1) {
                        output += route.get(i).orderNumber + ",";
                    }
                    if (i == route.size()-1) {
                        output += route.get(i).orderNumber;
                    }
                }

                // add the output of order numbers to the text area at the bottom of the GUI
                outputTextField.setText(output);

                // will hold the order number and addresses of the route formatted as objects
                String str = "";
                for (Location l : route) {
                    str +=  "{" + "\r\n" + "    order number: " + l.orderNumber + "\r\n" + "    address: " + l.address + "\r\n" + "}" + "\r\n";
                }

                // add the the routes to the text area in the middle of the screen
                outputAddress.setText("Nearest Neighbour Route" + "\r\n" + " " + "\r\n" + "Starting at Apache Pizza 3 Mill St, Maynooth" + "\r\n" + " " + "\r\n" + str);
                frame.setVisible(true);
            }
        });

    }

    // the nearest neighbour algorithm
    // computes the distance from the current location to the next location in the deliveries list
    // visits the nearest location
    public static List<Location> nearestNeighbour(List<Location> deliveries) {
        Location currentLocation = new Location(53.38130628714026,-6.593023972501682, "Apache Pizza 3 Mill St, Maynooth");
        Location nearestNeighbour = new Location();
        double shortestDistance = 1000000; // initially some arbitrarily large number
        List<Location> route = new ArrayList<>(); // will hold the route to take according to nearest neighbour
        int x = deliveries.size();
        while (x != 0) {
            x--;
            // for each location in the deliveries list
            for (Location nextLocation : deliveries) {
                // compute the distance from the current location to the next location
                double distance = round(distance(currentLocation.longitude, currentLocation.latitude, nextLocation.longitude, nextLocation.latitude, 6371));
                if (distance <= shortestDistance) { // if a distance is found that is less than the current shortest distance
                    shortestDistance = distance;
                    nearestNeighbour = nextLocation; // update nearest neighbour to the next location
                }
                // printing the locations and distances to the console
                System.out.println(currentLocation.address + " to " + nextLocation.address + " order num = " + nextLocation.orderNumber + " = " + distance + "km");
            }
            route.add(nearestNeighbour); // add the nearest nearest neighbour to the route
            currentLocation = nearestNeighbour; // update the current location to the nearest neighbour
            deliveries.remove(nearestNeighbour); // remove nearest neighbour so we don not visit it again
            shortestDistance = 1000000; // reset shortest distance
            nearestNeighbour = new Location(); // reset nearest neighbour
        }
        return route;
    }

    // calculates distance between two points on a sphere given latitude, longitude and sphere radius.
    public static double distance(double long1, double lat1, double long2, double lat2, double radius) {
        lat1 = Math.toRadians(lat1);
        long1 = Math.toRadians(long1);
        lat2 = Math.toRadians(lat2);
        long2 = Math.toRadians(long2);
        return 2 * radius * Math.asin(Math.sqrt(haversine(lat2-lat1) + (Math.cos(lat1) * Math.cos(lat2) * haversine(long2-long1))));
    }

    // returns haversine of an angle
    public static double haversine(double theta) { return (1-Math.cos(theta)) / 2; }

    // returns input rounded to nearest 3 decimal places
    public static double round(double num) { return Math.round(num * 1000d) / 1000d; };
}

// used for creating the location objects
class Location {

    double longitude;
    double latitude;
    int orderNumber;
    int minutesWaiting;
    String address;

    public Location() {

    }

    public Location(double longitude, double latitude, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public Location(double longitude, double latitude, int orderNumber, int minutesWaiting, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.orderNumber = orderNumber;
        this.minutesWaiting = minutesWaiting;
        this.address = address;
    }
}







