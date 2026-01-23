This diagram is a **UML Class Diagram** for a Gym Management System. It illustrates the data structures, relationships, and behaviors of various entities within the application.

The layout is organized hierarchically, starting with a central User entity and branching into specific roles and operational components like centers, bookings, and payments.

---

## 1. User Hierarchy and Roles

At the core of the system is the **User** class, which serves as a base for different types of actors.

* **User (Base Class):** Contains common attributes like `id`, `name`, `email`, `password`, and a `Role`.
* **Specialized Roles:** Through an aggregation relationship, the User is linked to three specific subclasses:
* **Admin:** Responsible for system-level management. It includes methods like `approveCenter()`, `rejectCenter()`, `approveGymOwner()`, and `rejectGymOwner()`.
* **GymMember:** Represents the customer. It contains a `creditCard` attribute and methods for business logic such as `listCenters()`, `bookSlot()`, and `getSchedule()`.
* **GymOwner:** Manages the facilities. It holds a list of `GymCenters` and an `approvalStatus`. It can `addCenter()`, `deleteCenter()`, and `listCenters()`.



---

## 2. Infrastructure and Scheduling

The middle section of the diagram focuses on the physical gym locations and how time is managed.

* **Center:** Represents a gym location. It includes an address, a list of available slots, and an `approvalStatus` (managed by the Admin).
* **Slot:** Defines a specific time block within a `Center`. It tracks `capacity` and holds a list of `Bookings`. It includes logic to calculate `remainingSeats()` and to `addMember()` or `removeMember()`.

---

## 3. Transactional and Operational Entities

These classes handle the interactions between members and the gym infrastructure.

* **Booking:** A record connecting a `gym_member_id` to a specific `slot_id`.
* **WaitList:** If a slot is full, this entity manages the queue. It tracks the `gymmember_id`, their `rank` in the queue, and their current `status`.
* **Payment:** A record of a transaction, linking a member to a slot with a specific `timestamp`.

---

## 4. Supporting Enums and Metadata

On the right side, there are helper structures used for classification:

* **Role:** Defined as a class (intended to replace an Enum) containing types like `ADMIN`, `GYMMEMBER`, and `GYMADMIN`.
* **Status:** An Enum used to track the lifecycle of centers or owners with values: `PENDING`, `APPROVED`, and `REJECTED`.
* **Notification:** A simple class containing a `message` and a `user_id`.

---

## 5. Notable Design Feedback

The diagram includes a prominent **yellow sticky note** providing a critique of the current architecture:

> **"NOTIFICATION SHOULD NOT BE A CLASS, IT SHOULD BE AN EVENT THAT HAPPENS AFTER OTHER EVENTS"**

This suggests a shift from a static data model to an **Event-Driven Architecture** for handling system alerts.

---

