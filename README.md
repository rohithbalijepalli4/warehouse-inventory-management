# Warehouse Inventory Management System

A Java object-oriented inventory management project that tracks items, pallets, and shelves across a two-dimensional warehouse grid.

## Features
- Add and remove inventory while respecting pallet size and shelf weight limits
- Fill existing pallets before creating additional ones
- Locate items across shelves and transfer pallets between locations
- Track remaining storage capacity and item quantities
- Apply centralized constraints through the course-provided `Policies` class

## Technologies
Java, object-oriented programming, arrays, 2D arrays, encapsulation, input validation.

## Project Structure
- `Item.java` — models products and their attributes
- `Pallet.java` — manages quantities and item capacity
- `Shelf.java` — tracks pallets, open slots, and weight limits
- `Warehouse.java` — manages inventory across a grid of shelves

## Setup
The course-provided `Policies.java` file is included. To compile the Java classes:

```bash
javac *.java
```

The classes are designed as a reusable system; there is no standalone `main` method. You can create a separate driver class for testing.

## Context
Java coursework project, Fall 2026.