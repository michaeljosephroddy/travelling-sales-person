# Traveling Salesperson Problem (TSP) - Nearest Neighbour Implementation

## Overview
This project implements a graphical user interface (GUI) solution to the Traveling Salesperson Problem (TSP) using the **Nearest Neighbour Algorithm**. It allows users to input delivery locations and computes the optimal route based on proximity, starting from a predefined location.

## Features
1. **Graphical User Interface**:
   - Input delivery data through a text area.
   - Compute the optimal route using the Nearest Neighbour Algorithm.
   - View the calculated route and corresponding addresses in the output area.

2. **Nearest Neighbour Algorithm**:
   - Calculates the shortest route by iteratively visiting the nearest unvisited location.
   - Outputs the order of visits and corresponding distances.

3. **Location Data Processing**:
   - Accepts delivery data in CSV format.
   - Supports distance calculation using the Haversine formula for geographical coordinates.

4. **Real-time Output**:
   - Displays the order of deliveries and their addresses.
   - Highlights the starting location for the route.

## GUI Layout
The application consists of the following components:

1. **Left Panel**:
   - **Input Text Area**: For pasting delivery location data in CSV format.
   - **Compute Button**: Triggers the route computation.

2. **Middle Panel**:
   - **Output Area**: Displays the formatted route with order numbers and addresses.

3. **Bottom Panel**:
   - **Output Text Field**: Displays the order of visit by order numbers.

## How to Use
1. Launch the application.
2. Paste delivery data into the left panel's input text area. Each line should be in the following format:
   ```
   orderNumber,address,minutesWaiting,longitude,latitude
   ```
   Example:
   ```
   1,123 Main St,10,-6.593023972501682,53.38130628714026
   ```
3. Click the **Compute** button.
4. View the computed route in the middle panel and the order of visit in the bottom panel.

## Key Classes and Methods

### 1. **Main Class** (`TSP`)
The entry point of the application, responsible for:
- Creating the GUI layout.
- Handling button clicks to initiate route computation.
- Displaying the results.

### 2. **Location Class**
Represents a delivery location with attributes:
- `longitude` and `latitude`: Geographical coordinates.
- `orderNumber`: The order identifier.
- `minutesWaiting`: Waiting time for the delivery.
- `address`: Delivery address.

#### Constructors:
- Default constructor for initializing empty objects.
- Overloaded constructors for setting specific attributes.

### 3. **Nearest Neighbour Algorithm**
#### Method: `nearestNeighbour(List<Location> deliveries)`
- Implements the Nearest Neighbour Algorithm.
- Calculates the route by finding the closest unvisited location iteratively.

#### Supporting Methods:
- **`distance(double long1, double lat1, double long2, double lat2, double radius)`**:
  Calculates the great-circle distance between two points on a sphere using the Haversine formula.

- **`haversine(double theta)`**:
  Computes the haversine of an angle.

- **`round(double num)`**:
  Rounds a number to three decimal places.

## Example Input and Output

### Input:
```
1,123 Main St,10,-6.5923,53.3813
2,456 Elm St,5,-6.6011,53.4002
3,789 Oak St,15,-6.5700,53.3800
```

### Output:
#### Route Order (Bottom Panel):
```
1,2,3
```

#### Route Details (Middle Panel):
```
Nearest Neighbour Route

Starting at Apache Pizza 3 Mill St, Maynooth

{
    order number: 1
    address: 123 Main St
}
{
    order number: 2
    address: 456 Elm St
}
{
    order number: 3
    address: 789 Oak St
}
```

## Dependencies
- **Java Swing**: For GUI components.
- **Java AWT**: For layout and event handling.

## Known Limitations
- Assumes valid input format; no validation for malformed data.
- Does not handle edge cases like ties in distance.
- Only calculates the route but does not return to the starting location.

## Future Improvements
- Add data validation for input.
- Handle ties in nearest neighbour selection.
- Extend to support round-trip calculations.
- Enhance GUI for better user experience.

