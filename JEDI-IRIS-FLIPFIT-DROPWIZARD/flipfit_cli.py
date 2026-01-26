import requests
import json

BASE_URL = "http://localhost:8080"
user_info = {}

def signup(username, email, password, role):
    """Signs up a new user."""
    url = f"{BASE_URL}/users/signup"
    data = {
        "name": username,
        "email": email,
        "password": password,
        "role": role.upper()
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Signup successful!")
        return True
    else:
        print(f"Signup failed: {response.text}")
        return False

def login(email, password):
    """Logs in a user."""
    url = f"{BASE_URL}/users/login"
    data = {
        "email": email,
        "password": password
    }
    response = requests.post(url, json=data)
    if response.status_code == 200 and response.json():
        print("Login successful!")
        user_info['email'] = email
        # In a real app, you'd get the user's role and ID here
        # For simplicity, we'll ask for it after login
        user_info['id'] = input("Enter your user ID: ")
        user_info['role'] = input("Enter your role (ADMIN, GYM_MEMBER, GYM_OWNER): ").upper()
        return True
    else:
        print("Login failed.")
        return False

def approve_center():
    center_id = input("Enter the center ID to approve: ")
    url = f"{BASE_URL}/admin/centers/{center_id}/approve"
    response = requests.post(url)
    if response.status_code == 200:
        print("Center approved successfully.")
    else:
        print(f"Failed to approve center: {response.text}")

def reject_center():
    center_id = input("Enter the center ID to reject: ")
    url = f"{BASE_URL}/admin/centers/{center_id}/reject"
    response = requests.post(url)
    if response.status_code == 200:
        print("Center rejected successfully.")
    else:
        print(f"Failed to reject center: {response.text}")

def approve_gym_owner():
    owner_id = input("Enter the gym owner ID to approve: ")
    url = f"{BASE_URL}/admin/gymowners/{owner_id}/approve"
    response = requests.post(url)
    if response.status_code == 200:
        print("Gym owner approved successfully.")
    else:
        print(f"Failed to approve gym owner: {response.text}")

def reject_gym_owner():
    owner_id = input("Enter the gym owner ID to reject: ")
    url = f"{BASE_URL}/admin/gymowners/{owner_id}/reject"
    response = requests.post(url)
    if response.status_code == 200:
        print("Gym owner rejected successfully.")
    else:
        print(f"Failed to reject gym owner: {response.text}")

def add_center():
    name = input("Enter center name: ")
    address = input("Enter center address: ")
    url = f"{BASE_URL}/gymowner/centers"
    data = {"name": name, "address": address, "gymOwnerId": user_info.get("id")}
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Center added successfully.")
    else:
        print(f"Failed to add center: {response.text}")

def delete_center():
    center_id = input("Enter the center ID to delete: ")
    url = f"{BASE_URL}/gymowner/centers/{center_id}"
    response = requests.delete(url)
    if response.status_code == 204:
        print("Center deleted successfully.")
    else:
        print(f"Failed to delete center: {response.text}")

def add_slot():
    center_id = input("Enter center ID: ")
    start_time = input("Enter start time (YYYY-MM-DDTHH:MM:SS): ")
    end_time = input("Enter end time (YYYY-MM-DDTHH:MM:SS): ")
    capacity = int(input("Enter capacity: "))
    url = f"{BASE_URL}/gymowner/slots"
    data = {
        "centerId": center_id,
        "startTime": start_time,
        "endTime": end_time,
        "capacity": capacity
    }
    response = requests.post(url, json=data)
    if response.status_code == 201:
        print("Slot added successfully.")
    else:
        print(f"Failed to add slot: {response.text}")

def delete_slot():
    slot_id = input("Enter the slot ID to delete: ")
    url = f"{BASE_URL}/gymowner/slots/{slot_id}"
    response = requests.delete(url)
    if response.status_code == 204:
        print("Slot deleted successfully.")
    else:
        print(f"Failed to delete slot: {response.text}")

def list_owned_centers():
    url = f"{BASE_URL}/gymowner/{user_info.get('id')}/centers"
    response = requests.get(url)
    if response.status_code == 200:
        print("Your centers:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to list centers: {response.text}")

def list_centers():
    url = f"{BASE_URL}/gymmember/centers"
    response = requests.get(url)
    if response.status_code == 200:
        print("Available centers:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to list centers: {response.text}")

def list_available_slots():
    center_id = input("Enter center ID: ")
    url = f"{BASE_URL}/gymmember/centers/{center_id}/slots"
    response = requests.get(url)
    if response.status_code == 200:
        print("Available slots:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to list slots: {response.text}")

def book_slot():
    slot_id = input("Enter slot ID to book: ")
    url = f"{BASE_URL}/gymmember/{user_info.get('id')}/book/{slot_id}"
    response = requests.post(url)
    if response.status_code == 201:
        print("Slot booked successfully.")
    else:
        print(f"Failed to book slot: {response.text}")

def join_waitlist():
    slot_id = input("Enter slot ID to join waitlist: ")
    url = f"{BASE_URL}/gymmember/{user_info.get('id')}/waitlist/{slot_id}"
    response = requests.post(url)
    if response.status_code == 201:
        print("Joined waitlist successfully.")
    else:
        print(f"Failed to join waitlist: {response.text}")

def view_schedule():
    url = f"{BASE_URL}/gymmember/{user_info.get('id')}/schedule"
    response = requests.get(url)
    if response.status_code == 200:
        print("Your schedule:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to get schedule: {response.text}")

def get_all_bookings():
    url = f"{BASE_URL}/bookings"
    response = requests.get(url)
    if response.status_code == 200:
        print("All bookings:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to get bookings: {response.text}")

def get_all_centers():
    url = f"{BASE_URL}/centers"
    response = requests.get(url)
    if response.status_code == 200:
        print("All centers:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to get centers: {response.text}")

def get_all_payments():
    url = f"{BASE_URL}/payments"
    response = requests.get(url)
    if response.status_code == 200:
        print("All payments:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to get payments: {response.text}")

def get_all_slots():
    url = f"{BASE_URL}/slots"
    response = requests.get(url)
    if response.status_code == 200:
        print("All slots:")
        print(json.dumps(response.json(), indent=2))
    else:
        print(f"Failed to get slots: {response.text}")


def logged_in_menu():
    """Menu for logged in users."""
    role = user_info.get("role")
    while True:
        print(f"\nLogged in as {user_info.get('email')} ({role})")
        if role == "ADMIN":
            print("1. Approve Center")
            print("2. Reject Center")
            print("3. Approve Gym Owner")
            print("4. Reject Gym Owner")
            print("5. Logout")
            choice = input("Enter your choice: ")
            if choice == '1':
                approve_center()
            elif choice == '2':
                reject_center()
            elif choice == '3':
                approve_gym_owner()
            elif choice == '4':
                reject_gym_owner()
            elif choice == '5':
                user_info.clear()
                break
        elif role == "GYM_OWNER":
            print("1. Add Center")
            print("2. Delete Center")
            print("3. Add Slot")
            print("4. Delete Slot")
            print("5. List Owned Centers")
            print("6. Logout")
            choice = input("Enter your choice: ")
            if choice == '1':
                add_center()
            elif choice == '2':
                delete_center()
            elif choice == '3':
                add_slot()
            elif choice == '4':
                delete_slot()
            elif choice == '5':
                list_owned_centers()
            elif choice == '6':
                user_info.clear()
                break
        elif role == "GYM_MEMBER":
            print("1. List Centers")
            print("2. List Available Slots")
            print("3. Book Slot")
            print("4. Join Waitlist")
            print("5. View Schedule")
            print("6. Logout")
            choice = input("Enter your choice: ")
            if choice == '1':
                list_centers()
            elif choice == '2':
                list_available_slots()
            elif choice == '3':
                book_slot()
            elif choice == '4':
                join_waitlist()
            elif choice == '5':
                view_schedule()
            elif choice == '6':
                user_info.clear()
                break
        else:
            print("Invalid role.")
            user_info.clear()
            break

def main():
    """Main function for the FlipFit CLI."""
    while True:
        print("\nWelcome to FlipFit CLI!")
        print("1. Signup")
        print("2. Login")
        print("3. View All Bookings")
        print("4. View All Centers")
        print("5. View All Payments")
        print("6. View All Slots")
        print("7. Exit")
        choice = input("Enter your choice: ")

        if choice == '1':
            username = input("Enter username: ")
            email = input("Enter email: ")
            password = input("Enter password: ")
            role = input("Enter role (ADMIN, GYM_MEMBER, GYM_OWNER): ")
            signup(username, email, password, role)
        elif choice == '2':
            email = input("Enter email: ")
            password = input("Enter password: ")
            if login(email, password):
                logged_in_menu()
        elif choice == '3':
            get_all_bookings()
        elif choice == '4':
            get_all_centers()
        elif choice == '5':
            get_all_payments()
        elif choice == '6':
            get_all_slots()
        elif choice == '7':
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
