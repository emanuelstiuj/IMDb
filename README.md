
# Project Implementation Overview

## 1. File Reading
- Utilized an ObjectMapper object for file reading.
- JsonNode objects were used for accurate handling of input data.
- For each Production class, two ImageIcon variables store the same picture in different sizes.

## 2. Homepage
- Features a search bar and buttons for various options.
- Provides movie and series recommendations displayed as images.

## 3. Menu
### 3.1 Viewing Productions in the System
- Displayed using a JScrollPane.
- Each production includes a button for additional information.
- User reviews are validated, allowing only integer values between 0 and 10.

### 3.2 Viewing Actors in the System
- Displayed using a JScrollPane.
- Essential information includes name and biography.

### 3.3 Viewing Favorites
- Used a JSplitPane for display.
- Left side: actors displayed similarly to those in the system.
- Right side: productions displayed similarly to those in the system.

### 3.4 Viewing Contributions
- Content organized in a JSplitPane.
- Actors and productions have a button for deletion or information update.

### 3.5 Updating Information
- Contributors can modify any information.
- Productions can have changes in title, release year, directors, seasons, etc.
- Actors can modify name, biography, and performances.

### 3.6 Adding New Productions and Actors
- Interface similar to information update, with slight differences.
- Inputs are verified.

### 3.7 Adding/Deleting New Users. Information Update
- Usernames generated by the administrator must be unique.
- Admin cannot create a user if inputs do not follow the specified format or if all fields are not filled.
- Information update involves changing personal data.

### 3.8 Creating/Withdrawing a Request
- Users can choose the type of request and the movie or production.
- Description has a character limit.
- A list allows users to delete any sent request.

### 3.9 Resolving a Request
- The responsible person can choose between request solved or reject request.

## 4. Notifications
- Five types of notifications based on user actions.

## 5. Features
1. Reset button on the actor/production viewing page.
2. Inputs are verified using regex and must follow a specific format.
3. Information update allows almost any modification.
4. Ability to delete notifications.
5. If a request is withdrawn, the recipient's notification is deleted.
6. If the recipient of requests is deleted, the requests and notifications are transferred to admins.
7. If a contributor is deleted, admins receive notifications about productions taken from them.
8. If a user is deleted, the requests they sent and notifications received are deleted for each user.
9. If a user is deleted, their reviews for all productions are deleted.
10. If a user withdraws a request, notifications related to that request are deleted.



