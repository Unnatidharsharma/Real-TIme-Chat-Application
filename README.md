# 💬 Real-Time Chat Application – Spring Boot + Angular

A full-stack **Real-Time Chat Application** powered by **Spring Boot (backend)** and **Angular (frontend)**. Utilizes **WebSocket (STOMP over SockJS)** for low-latency, bi-directional messaging, enabling seamless communication between users in real time.

---

## 🚀 Key Features

- 🔄 Real-time chat using WebSocket + STOMP
- 👥 Multi-user support with JOIN/LEAVE events
- 🕒 Timestamps and message types (CHAT, JOIN, LEAVE)
- 🔁 Typing indicator and connection status
- 📶 Auto reconnect on connection drop
- 📦 REST API support (user registration/login optional)
- 🌐 Clean UI using Angular + RxJS + Bootstrap/Tailwind (optional)
- 🧪 Fully testable with tools like WebSocket King or Postman WebSocket

---

## ⚙️ Tech Stack

| Layer         | Technology                        |
|---------------|------------------------------------|
| Frontend      | Angular 15+                        |
| Real-time     | WebSocket (STOMP + SockJS)         |
| Backend       | Spring Boot, Spring WebSocket      |
| Build Tools   | Maven, Angular CLI                 |
| Communication | STOMP protocol over SockJS         |
| Message Format| JSON                               |

---

## 📐 Architecture

### 🔧 Backend (Spring Boot)
- **`WebSocketConfig.java`** – WebSocket/STOMP endpoint config
- **`ChatController.java`** – Handle message mapping
- **`Message.java`** – Chat message model (POJO)
- **`User.java`** – Connected user metadata
- **`ChatService.java`** – Message processing logic (optional)

### 🎨 Frontend (Angular)
- **Components**:
  - `ChatComponent` – Message input/output
  - `UserComponent` – Online users
- **Services**:
  - `WebSocketService` – Handles connection and message flow
- **Routing & UI**:
  - Clean UI with Bootstrap/Tailwind or Angular Material

---

## 🧾 Message Format

```json
{
  "sender": "Alice",
  "content": "Hello world!",
  "timestamp": "2025-07-02T12:45:00Z",
  "type": "CHAT" // or JOIN, LEAVE
}



---

### ✅ Next Steps:
- Replace placeholder links with your GitHub and LinkedIn
- Add screenshots or short demo video for UI impact
- Upload both `backend/` and `frontend/` folders to GitHub in a monorepo

Would you like:
- Full `WebSocketConfig`, `ChatController`, and Angular `WebSocketService` code?
- Angular `chat.component.ts` and basic UI template?

Let me know and I’ll provide a complete working version.
