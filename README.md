# Hostel Online Exchange (HOE) 🏠📦

An Android app built with Jetpack Compose that helps hostel residents donate, sell, or exchange used items with ease. Whether it's books, electronics, or daily essentials — HOE connects students within the campus for a smarter and more sustainable sharing experience.

---

## 📌 Key Highlights

- Upload items with image, name, category, and price  
- Choose to donate or sell with one tap  
- Browse listings added by other students  
- View item details and contact the uploader  
- Organized by categories like Books, Gadgets, and Furniture  
- Clean, intuitive UI designed using Jetpack Compose  

---

## ⚙️ Tech Stack

- **Jetpack Compose** – Modern UI toolkit for Android  
- **Kotlin** – Language for Android development  
- **Supabase** – Backend-as-a-service (auth, database, storage)  
- **Coil** – Image loading and caching  
- **Navigation Compose** – Navigation between screens  

---

## 🛠 Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Abhi95081/New_HOE.git
   
2. Open in Android Studio (Giraffe or newer recommended)

3. Replace the placeholder Supabase keys in your code:

SUPABASE_URL
SUPABASE_ANON_KEY
// make a object to this.
object SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://.supabase.co",

        supabaseKey = ""

    ) {
        install(Storage)
        install(Postgrest)
    }
}


4. Build and run the app on an emulator or real device.
   
🗃 Example Database Schema (PostgreSQL via Supabase)
Table: items

Column | Type | Description
id | UUID | Primary key
name | Text | Item name
price | String | 0 for donation
image_url | Text | Link to uploaded image
uploader | Text | UID or contact

📷 Screenshots

**Splash Screen**
![image](https://github.com/user-attachments/assets/8fbf5dde-b77b-4153-aeec-30ff7697846d)
**Login Page**
![image](https://github.com/user-attachments/assets/2be7510b-fe56-4995-a4ca-83111ee32b27)
**Registration Page**
![image](https://github.com/user-attachments/assets/0d5c5c79-3c58-41bf-8052-dece455c56c8)
**Home Page**
![image](https://github.com/user-attachments/assets/d688d198-9c8f-4eb2-ba68-9df3e241eea0)
**Search page**
![image](https://github.com/user-attachments/assets/691734ad-e32d-4c37-9a82-ca82c6b65fd6)
**Add Items**
![image](https://github.com/user-attachments/assets/1cac0063-bd70-49be-b586-800364219e8b)
**Profile Page**
![image](https://github.com/user-attachments/assets/67c92c94-762e-4e4f-bf9d-cba1b9726cbc)

👤 Author
Developed by Abhishek Roushan
🔗 GitHub(https://github.com/Abhi95081/) | 💼 LinkedIn(https://www.linkedin.com/in/abhishek-roushan/)

🙌 Contributions
Feel free to fork the repo, raise issues, or submit pull requests to improve functionality or UI/UX.

📄 License
This project is released under the MIT License. You’re free to use, modify, and share it.
