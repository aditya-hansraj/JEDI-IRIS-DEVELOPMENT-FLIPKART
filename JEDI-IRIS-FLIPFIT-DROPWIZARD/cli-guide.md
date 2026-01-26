# FlipFit CLI Guide

This guide provides instructions on how to set up and run the FlipFit CLI application.

## 1. Prerequisites

*   Python 3.6 or later
*   The FlipFit Dropwizard application must be running.

## 2. Setup

1.  **Install dependencies:**

    ```bash
    pip install -r requirements.txt
    ```

## 3. Running the CLI

1.  **Run the CLI application:**

    ```bash
    python flipfit_cli.py
    ```

    The CLI will prompt you with a menu of options.

### Main Menu

*   **Signup:** Register a new user.
*   **Login:** Log in as an existing user.
*   **View All Bookings:** View all bookings in the system.
*   **View All Centers:** View all centers in the system.
*   **View All Payments:** View all payments in the system.
*   **View All Slots:** View all slots in the system.
*   **Exit:** Exit the CLI application.

### Logged-in Menu

Once you log in, you will see a menu based on your role.

#### Admin Menu

*   **Approve Center:** Approve a pending gym center.
*   **Reject Center:** Reject a pending gym center.
*   **Approve Gym Owner:** Approve a pending gym owner.
*   **Reject Gym Owner:** Reject a pending gym owner.
*   **Logout:** Log out of the current session.

#### Gym Owner Menu

*   **Add Center:** Add a new gym center.
*   **Delete Center:** Delete an existing gym center.
*   **Add Slot:** Add a new slot to one of your centers.
*   **Delete Slot:** Delete an existing slot.
*   **List Owned Centers:** List all the centers you own.
*   **Logout:** Log out of the current session.

#### Gym Member Menu

*   **List Centers:** List all available gym centers.
*   **List Available Slots:** List all available slots for a given center.
*   **Book Slot:** Book an available slot.
*   **Join Waitlist:** Join the waitlist for a full slot.
*   **View Schedule:** View your upcoming booked slots.
*   **Logout:** Log out of the current session.
