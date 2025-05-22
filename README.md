🛒 Price Comparison Application


A Spring Boot application for comparing product prices across different stores. It loads product and discount data from CSV files and provides RESTful APIs for managing products, price alerts, shopping baskets, and price history. The system includes logging, simulated email alerts, and scheduled tasks.

🚀 Features
📦 Product Management: List, filter, search, and get product recommendations.
🔔 Price Alerts: Set alerts for product prices and simulate email notifications.
🛍️ Shopping Basket: Add items, view contents, and optimize purchases by store.
📈 Price History: Track historical prices filtered by product, store, brand, or category.

🛠️ Technology Stack

Java 17+	-> Main programming language and JDK requirement
Spring Boot	 -> Application framework with embedded Tomcat
Apache Commons CSV	-> CSV file parsing at startup (products.csv, discounts.csv)
Lombok	-> Reduces boilerplate code with annotations (e.g., @Data, @Slf4j)
SLF4J + Logback	 ->Logging abstraction and implementation (configurable levels)
Spring Scheduler	-> Runs periodic tasks (e.g., scheduled price checks)
Spring Mail	-> Sends simulated email notifications for price alerts

📂 Data Files
Located in src/main/resources/

Example – products.csv
product_id;product_name;product_category;brand;package_quantity;package_unit;price;currency  
P001;lapte zuzu;lactate;Zuzu;1;l;13.00;RON  
P004;iaurt grecesc;lactate;Proxi;0.4;kg;11.40;RON  
P007;ouă mărimea M;ouă;Din Ogradă;10;buc;12.50;RON   

Example – discounts.csv
product_id;product_name;brand;package_quantity;package_unit;product_category;from_date;to_date;percentage_of_discount  
P001;lapte zuzu;Zuzu;1;l;lactate;2025-05-08;2025-05-14;9  
P006;ouă mărimea M;Ferma Veche;10;buc;ouă;2025-05-08;2025-05-14;11  
P012;pâine albă;K-Classic;500;g;panificație;2025-05-09;2025-05-15;7  


📡 REST API Endpoints
🔍 Product Management
GET /products	-> List all products
GET /products/category?category={category}	-> Filter products by category
GET /products/search?query={text}	-> Search by name or brand
GET /products/best-discounts?date={YYYY-MM-DD}	-> Show best discounts by date
GET /products/{productId}/recommendations	-> Show similar products in same category

🔔 Price Alerts
Create Alert

POST /alerts
{
  "productId": "P001",
  "targetPrice": 8.0,
  "storeName": "Lidl"
}

Get Alert by ID : GET /alerts/{alertId}
Get Alerts by Product: GET /alerts/product/{productId}

🛒 Shopping Basket
POST /basket/{userId}/add
{
  "productId": "P001",
  "storeName": "Profi",
  "quantity": 2
}

View Basket: GET /basket/{userId}
Optimize Basket : GET /basket/{userId}/optimize

🕓 Price History
Get Historical Data
GET /price-history?productId={id}&storeName={store}&brand={brand}&category={cat}

🧪 Build & Run
Prerequisites
Java 17+
Maven

Build

Build: mvn clean install

Run: mvn spring-boot:run

📬 Notifications
Email alerts are stubbed (simulated).

Real email support can be added by configuring application.properties with SMTP settings.

📅 Scheduled Tasks
Runs periodically using @Scheduled, e.g., for checking price alerts.

📎 Future Improvements
-Add real email integration.
-Migrate from in-memory storage to a database (e.g.,MongoDB, PostgreSQL).
-Implement user authentication and role management.
-Add a front-end interface.

🧑‍💻 Author
Cristiana-Silvia Bazîru
Final-year engineering student passionate about full-stack development and data-driven applications.

