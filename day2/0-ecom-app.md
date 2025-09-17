# E-Commer Application with Admin App and Customer App ie multi-modules app (multi-actors)
* products: {_id,name,description,category,price,stock,tags}
  * category: Electronics / Furniture / Home / Fashion
* users: {_id,name,email,password,street,city,zip,createdAt}
* orders: {_id,userId,items,totalAmount,status,createdAt}
  * items[I].item {productId, qty, price}
  * status: pending / shipped / delivered / cancelled
* reviews: {_id,productId,userId,rating,comment,createdAt}
* admins: {_id,name,email,password,role,createdAt} 
  * role: Manager / Supervisor / Agent
  * Hard Coded admins (no pages)

## **🟢 Admin App Modules**

**Purpose:** Manage the platform, products, users, orders, and reviews.
### 1. **Header and Sidebar**
```
Header: [Logo] [Admin Name] [Logout]
Sidebar: [Dashboard] [Users] [Products] [Orders] [Reviews] [Reports]
```

### 2. **Product Management** 
* View all products 
* Add / Edit / Delete product.
* Stock management: update stock quantity.
* Assign categories and tags.
* Search / filter by category, price range, stock availability.

```
Header + Sidebar
Main Content:
  - Search & Filter:
      - Search by Name
      - Filter by Category / Price / Stock
  - Product Table:
      Columns: ID | Name | Category | Price | Stock | Tags | Actions
      Actions: [Edit] [Delete]
  - Add Product Button
  - Stock Alerts Section (low stock products)
```


#### 📐 **Product Management Table (Wireframe)**

```
Header: [Logo] [Admin Name] [Logout]
Sidebar: [Dashboard] [Users] [Products] [Orders] [Reviews] [Reports]
-------------------------------------------------- Product Management ---------------------------------------------------
[ Search: __________ ] [ Category v ] [ Price Range v ] [ Stock: All v ]      [ + Add Product ]

------------------------------------------------------------------------------------------------------------------------
|  ID  |      Name       |   Category   | Price ($) | Stock |          Tags           |        Actions        |
------------------------------------------------------------------------------------------------------------------------
| 101  | Laptop Pro 15   | Electronics  | 1200      |   50  | laptop, tech, computer | [Edit] [Delete]       |
| 102  | Wireless Mouse  | Electronics  | 25        |  200  | mouse, accessories     | [Edit] [Delete]       |
| 103  | Office Chair    | Furniture    | 150       |   80  | chair, office          | [Edit] [Delete]       |
| 104  | Coffee Mug      | Home         | 10        |  500  | mug, kitchen           | [Edit] [Delete]       |
| 105  | Smartphone X    | Electronics  | 900       |  100  | phone, tech, mobile    | [Edit] [Delete]       |
| 106  | Headphones      | Electronics  | 200       |  120  | audio, music           | [Edit] [Delete]       |
| 107  | Bookshelf       | Furniture    | 180       |   30  | wood, furniture,storage| [Edit] [Delete]       |
| 108  | Table Lamp      | Home         | 40        |  150  | lamp, lighting         | [Edit] [Delete]       |
| 109  | Backpack        | Fashion      | 60        |   75  | bag, travel            | [Edit] [Delete]       |
| 110  | Sneakers        | Fashion      | 120       |   60  | shoes, running         | [Edit] [Delete]       |
------------------------------------------------------------------------------------------------------------------------

📦 Stock Alerts:
- Bookshelf (Stock: 30) ⚠ Low Stock
- Sneakers (Stock: 60) ⚠ Low Stock Threshold (configurable)
------------------------------------------------------------------------------------------------------------------------
```


##### 🔎 Key Sections:

1. **Search & Filter Bar**

   * Search by name
   * Filter by category (Electronics, Furniture, Home, Fashion)
   * Filter by price range (dropdown: `<50, 50-200, 200+`)
   * Filter by stock (`All`, `In Stock`, `Low Stock`, `Out of Stock`)

2. **Product Table**

   * Columns: ID, Name, Category, Price, Stock, Tags, Actions
   * Actions:

     * **\[Edit]** → opens full form
     * **\[Delete]** → confirmation popup

3. **Add Product Button**

   * Top-right corner
   * Opens the Add Product form

4. **Stock Alerts**

   * Highlight low-stock items (threshold e.g., < 50)


#### Design the **Product Management Form UI** for both **Add Product** and **Edit Product** (same form, pre-filled in edit mode).

##### 📝 **Form Layout**
* **Header**: `Add New Product` / `Edit Product` (dynamic title)
* **Form Fields (in a Card/Modal):**
  * **Product Name** (Text Input)
  * **Description** (Textarea)
  * **Category** (Dropdown / Select)
  * **Price** (Number Input with currency prefix `$`)
  * **Stock Quantity** (Number Input)
  * **Tags** (Multi-select chips input or comma-separated input)

##### 🎛 **Actions**
* **Save / Update** (Primary button)
* **Cancel** (Secondary button, returns to product list)

##### 📐 **Wireframe (Text UI Design)**
```
 -----------------------------------------------------
|  Add New Product                                    |
 -----------------------------------------------------
| Product Name:    [_________________________]        |
|                                                     |
| Description:     [_________________________]        |
|                 [_________________________]         |
|                                                     |
| Category:       [Dropdown v]                        |
|                                                     |
| Price:          [$ _________ ]                      |
|                                                     |
| Stock:          [______]                            |
|                                                     |
| Tags:           [tag1] [tag2] [+ Add]               |
|                 (or comma separated input)          |
|                                                     |
 -----------------------------------------------------
| [ Save ]   [ Cancel ]                               |
 -----------------------------------------------------
```

##### ✨ **Extra Features**
* **Validation**:
  * Name, Price, Stock required
  * Price ≥ 0, Stock ≥ 0
* **Stock Alerts**: If stock < 5, show warning below input (`⚠ Low stock, consider increasing`)
* **Dynamic Tags**: Show as removable chips with `x`

##### 🔹 **Option 2: Quick Stock Update (separate action)** (Optional)

Sometimes, stock is the most frequently updated field. Instead of going into full **Edit Product**, you can provide a **Quick Update option** in the table:

* In the product table under **Stock** column → make it **inline editable** (input box + save icon).
* OR add an **\[Update Stock]** button in **Actions**.
* This opens a small **popup / modal** with only one field:

```
--------------------------
 Update Stock - Product A
--------------------------
 Current Stock: 12
 New Stock: [____]

 [ Update ]   [ Cancel ]
```

This is faster for stock managers who don’t need to edit price, tags, etc.

👉 So, **Yes, you can update stock from Edit Product**,
but for better usability, you might also provide a **dedicated quick stock update option**.


### 3. **Order Management** 
* View all orders 
* Filter orders by status (pending, shipped, delivered, cancelled)
* Update order status (e.g., mark as shipped)
* View order details (items, quantities, totalAmount, customer info)
```
Header + Sidebar
Main Content:
  - Filter Orders: Status Dropdown (Pending / Shipped / Delivered)
  - Search by User or Order ID
  - Orders Table:
      Columns: Order ID | User Name | Items Summary | Total Amount | Status | Created At | Actions
      Actions: [View Details] [Update Status]
  - View Order Modal:
      - List of items: Product Name, Quantity, Price
      - Total Amount
      - Shipping Info
      - Change Status Dropdown
```

#### **Wireframes (text UI)**:

##### 📋 **View All Orders Page**

```
-------------------------------------------------------------------------------------------------
Header: [ Admin Logo ]    [ Dashboard ]   [ Orders ]   [ Products ]   [ Logout ]
Sidebar: 
  - Dashboard
  - Product Management
  - Order Management
  - Users
-------------------------------------------------------------------------------------------------

Main Content: Orders
-------------------------------------------------------------------------------------------------
Filters:
  Status: [ Pending v ] [ Shipped v ] [ Delivered v ] [ Cancelled v ] [ All ]
  Search: [ Order ID / User Name _____________ ]  [ Search Button ]

-------------------------------------------------------------------------------------------------
Orders Table:
-------------------------------------------------------------------------------------------------
| Order ID  | User Name    | Items Summary            | Total | Status    | Created At  | Actions                |
-------------------------------------------------------------------------------------------------
| ORD-1001  | John Doe     | Laptop Pro 15, Mouse     | $1250 | Pending   | 2025-08-10 | [View Details] [Update]|
| ORD-1002  | Alice Smith  | Coffee Mug x3, Lamp      | $70   | Shipped   | 2025-08-05 | [View Details] [Update]|
| ORD-1003  | Mark Taylor  | Sneakers, Backpack       | $180  | Delivered | 2025-07-28 | [View Details] [Update]|
-------------------------------------------------------------------------------------------------
```


##### 🔎 **View Order Details Page (Modal or Separate Page)**

```
----------------------------------------------------------
Order Details (ORD-1001)
----------------------------------------------------------
Customer Info:
  Name: John Doe
  Email: john@example.com
  Street: 123 Main St
  City: New York
  Zip: 10001

Items:
----------------------------------------------------------
Product            Qty    Price     Total
----------------------------------------------------------
Laptop Pro 15      1      $1200     $1200
Wireless Mouse     2      $25       $50
----------------------------------------------------------
Total Amount: $1250
Status: Pending
Created At: 2025-08-10

[ Update Status ]   [ Close ]
----------------------------------------------------------
```


##### 🔄 **Update Status Page (or Dropdown inside Modal)**

```
----------------------------------------------------------
Update Order Status (ORD-1001)
----------------------------------------------------------
Current Status: Pending

Change Status:
[ Pending v ]
[ Shipped  ]
[ Delivered ]
[ Cancelled ]

[ Save Changes ]   [ Cancel ]
----------------------------------------------------------
```

##### ✨ Flow for Admin:

* **View All Orders Page** → quick overview, filters, actions
* **View Details Page** → full breakdown of items + customer info
* **Update Status Page** → simple dropdown to change status

### 4. **Dashboard** (Optional)

* Overview metrics: total users, total orders, total revenue, total products, pending shipments.
* Charts: sales trends, top-selling products, top-rated products.
* Quick links: Add Product, Manage Orders, Manage Users.
```
Header: [Logo] [Admin Name] [Logout]
Sidebar: [Dashboard] [Users] [Products] [Orders] [Reviews] [Reports]
Main Content:
  - Metrics Cards:
      - Total Users: 5
      - Total Orders: 5
      - Total Revenue: $2840
      - Total Products: 10
  - Charts Section:
      - Sales Trend Line Chart
      - Top Selling Products Bar Chart
      - Top Rated Products Pie Chart
  - Quick Actions:
      - Add Product Button
      - View Pending Orders Button
      - View Low Stock Products Button
```

### 5. **User Management** 

* View all users 
* Search / filter users by name, city, email.
* Edit user info (street, city, zip).
* Delete users.
```
Header + Sidebar (same as Dashboard)
Main Content:
  - Search Bar: [Search by name/email/city]
  - Table:
      Columns: ID | Name | Email | City | Zip | Created At | Actions
      Actions: [Edit] [Delete]
  - Add User Button
```

### 6. **Review Management**
* View all reviews 
* Filter by product or user.
* Delete inappropriate reviews.
* Analyze product ratings (average rating per product)
```
Header + Sidebar
Main Content:
  - Search / Filter: By Product / User / Rating
  - Reviews Table:
      Columns: Review ID | Product Name | User Name | Rating | Comment | Date | Actions
      Actions: [Delete]
  - Product Rating Summary: Avg Rating / Total Reviews
```

### 7. **Reports & Analytics** (Optional)

* Revenue reports by date/month
* Product sales reports
* User activity reports
* Top-rated and worst-rated products
```
Header + Sidebar
Main Content:
  - Revenue Report:
      - Date Range Picker
      - Sales Chart
      - Export CSV Button
  - Product Sales Report:
      - Top Selling Products Table
  - User Activity Report:
      - Active Users Table
```

---

## **🟢 Customer App Modules**

**Purpose:** Allow users to browse, purchase, and review products.
### 1. **User Authentication** 
* Sign Up / Login / Logout
* Password reset
* Profile management (view/update personal info)
#### **Login / Signup Page**
```
Header: [Logo]
Main Content:
  - Tabs: [Login] [Sign Up]
  - Form Fields:
      - Login: Email, Password, [Login Button]
      - Signup: Name, Email, Password, Confirm Password, [Signup Button]
  - Forgot Password Link
  - Redirect to Signup/Login Link
```
#### **Profile Page**
```
Header
Main Content:
  - User Info Form:
      - Name, Email, Street, City, Zip
      - [Save Changes Button]
  - Option to Change Password
```

#### Wireframes (Text UI)
👉 During **Sign Up**, we usually **only ask for basic details** (Name, Email, Password, Confirm Password).
👉 **Street, City, Zip** are part of the **Profile Page** (where the user can complete/update details later).

##### 🔑 **1. Login Page**
```
-------------------------------------------------
[ Logo ]
-------------------------------------------------
[ Tabs:  ( Login )   |   ( Sign Up ) ]

Email:    [______________________]
Password: [______________________]

[ Login Button ]

[ Forgot Password? ] 

Don’t have an account? [ Sign Up ]
-------------------------------------------------
```

##### 📝 **2. Sign Up Page**
```
-------------------------------------------------
[ Logo ]
-------------------------------------------------
[ Tabs:  ( Login )   |   ( Sign Up ) ]

Name:             [______________________]
Email:            [______________________]
Password:         [______________________]
Confirm Password: [______________________]

[ Sign Up Button ]

Already have an account? [ Login ]
-------------------------------------------------
```

##### 👤 **3. Profile Page**
```
-------------------------------------------------
Header:  [Logo]   [My Account]   [Logout]
-------------------------------------------------
Profile Information

Name:   [ John Doe            ]
Email:  [ john@example.com     ]
Street: [ 123 Main Street      ]
City:   [ New York             ]
Zip:    [ 10001                ]

[ Save Changes Button ]

[ Change Password ]
-------------------------------------------------
```

##### 🔒 **4. Change Password Page**
```
-------------------------------------------------
Header: [Logo]   [My Account]   [Logout]
-------------------------------------------------
Change Password

Current Password: [______________________]
New Password:     [______________________]
Confirm Password: [______________________]

[ Update Password Button ]
[ Cancel ]
-------------------------------------------------
```

✨ This way:
* **Login & Sign Up** → minimal fields (easy entry).
* **Profile Page** → full user info (Street, City, Zip, etc.).

### 2. **Product Catalog**

* Browse all products 
* Search by name, category, or tags
* Filter by price, stock availability, ratings
* Product details page:
  * Description, price, stock
  * User reviews & ratings
  * “Add to Cart” button
#### **Home / Product Listing Page**
```
Header: [Logo] [Search Bar] [Cart Icon] [Profile Dropdown]
Sidebar / Filters:
  - Category Filter
  - Price Range Slider
  - Ratings Filter
Main Content:
  - Product Grid (Cards):
      Card:
          - Name
          - Price
          - Rating Stars
          - [View Details] Button
          - [Add to Cart] Button
```

#### **Product Details Page**
```
Header
Main Content:
  - Product Info:
      - Name, Category, Price, Stock
      - Description
      - Tags
      - Rating Stars + Review Count
  - Add to Cart Section:
      - Quantity Selector
      - [Add to Cart Button]
  - Reviews Section:
      - List of reviews: User Name | Rating | Comment | Date
      - Add Review Form (if purchased)
```

#### **Wireframes (Text UI)** 

##### 🏠 **Home / Product Listing Page**

```
-------------------------------------------------------------------------------------------------
Header:  [ Logo ]   [ Search Bar: ______________________ ]   [ 🛒 Cart (2) ]   [ Profile v ]
-------------------------------------------------------------------------------------------------

Sidebar / Filters:
--------------------------------
 Category
   [ ] Electronics
   [ ] Furniture
   [ ] Home
   [ ] Fashion

 Price Range
   [ 0 ------------------ 1200 ]
        (Slider Control)

 Ratings
   [ ★★★★★ & up ]
   [ ★★★★☆ & up ]
   [ ★★★☆☆ & up ]
   [ ★★☆☆☆ & up ]

 Stock Availability
   [ ] In Stock Only
--------------------------------

Main Content: Product Grid (Cards)
---------------------------------------------------------------
[ Laptop Pro 15 ]                       [ Wireless Mouse ]                      [ Office Chair ]
 $1200   ★★★★☆ (120)                  $25   ★★★★☆ (240)                   $150   ★★★★☆ (80)
 [ View Details ] [Add to Cart]         [ View Details ] [Add to Cart]          [ View Details ] [Add to Cart]

[ Coffee Mug ]                          [ Smartphone X ]                        [ Headphones ]
 $10   ★★★★☆ (55)                      $900   ★★★★★ (300)                 $200   ★★★★☆ (210)
 [ View Details ] [Add to Cart]         [ View Details ] [Add to Cart]          [ View Details ] [Add to Cart]

[ Bookshelf ]                           [ Table Lamp ]                          [ Backpack ]
 $180   ★★★☆☆ (40)                    $40   ★★★★☆ (75)                     $60   ★★★★☆ (110)
 [ View Details ] [Add to Cart]         [ View Details ] [Add to Cart]          [ View Details ] [Add to Cart]

[ Sneakers ]
 $120   ★★★★☆ (95)
 [ View Details ] [Add to Cart]
---------------------------------------------------------------
```

##### 📄 **Product Details Page**
```
-------------------------------------------------------------------------------------------------
Header:  [ Logo ]   [ Search Bar: ______________________ ]   [ 🛒 Cart (2) ]   [ Profile v ]
-------------------------------------------------------------------------------------------------

Main Content:
-----------------------------------------------------------------------------------------
Product Info:
  Name: Laptop Pro 15
  Category: Electronics
  Price: $1200
  Stock: 50 Available
  Tags: laptop, tech, computer
  Description:
    High performance laptop with Retina display,
    16GB RAM, 512GB SSD, and long battery life.

  Rating: ★★★★☆ (120 reviews)

-----------------------------------------------------------------------------------------
Add to Cart Section:
  Quantity: [ - 1 + ]
  [ Add to Cart Button ]

-----------------------------------------------------------------------------------------
Reviews Section:
  -----------------------------------------------------
  John Doe    ★★★★★   "Amazing laptop!"    (2024-05-12)
  Alice W.    ★★★★☆   "Good value, but heavy." (2024-06-02)
  Mark T.     ★★★☆☆   "Average performance."   (2024-07-18)
  -----------------------------------------------------

  Add Review (if purchased):
    Rating: [ ★ ★ ★ ★ ★ ]
    Comment: [______________________________]
             [______________________________]
    [ Submit Review ]
-----------------------------------------------------------------------------------------
```
* 👉 This gives a clear **browse → filter → view → purchase → review** flow.
* Limit users to one review per product (optional).

### 3. **Shopping Cart**
* Remove items
* Update quantity
* Display total amount
* Proceed to checkout

#### **Shopping Cart Page**
```
Header
Main Content:
  - Table/List of Items:
      Columns: Product | Price | Quantity | Total | Actions
      Actions: [Remove] [Change Quantity]
  - Total Amount Summary
  - [Proceed to Checkout Button]
```

### 4. **Checkout & Orders**
* Enter shipping details (street, city, zip prefilled)
* Review order summary
* Place order 
* View past orders and status
* Cancel order (if pending)

#### **Checkout Page**
```
Header
Main Content:
  - Shipping Info Form:
      - Name, Street, City, Zip
      - Pre-fill if logged in
  - Order Summary:
      - List of Items + Price + Quantity
      - Total Amount
  - Payment Section (simulate for now)
  - [Place Order Button]
```

#### **Order History Page**
```
Header
Main Content:
  - Filter by Status: All / Pending / Shipped / Delivered
  - Table/List of Orders:
      Columns: Order ID | Items Summary | Total Amount | Status | Date | Actions
      Actions: [View Details]
  - View Order Modal:
      - List of Items + Quantity + Price
      - Total Amount
      - Shipping Info
```
#### **wireframes (Text UI)**

##### 🛒 **Shopping Cart Page**

```
-------------------------------------------------------------------------------------------------
Header: [ Logo ]   [ Search Bar ]   [ 🛒 Cart (3) ]   [ Profile v ]
-------------------------------------------------------------------------------------------------

Shopping Cart
-------------------------------------------------------------------------------------------------
| Product         | Price    | Quantity       | Total    | Actions                |
-------------------------------------------------------------------------------------------------
| Laptop Pro 15   | $1200    | [ - 1 + ]      | $1200    | [ Change Qty ] [Remove]|
| Wireless Mouse  | $25      | [ - 2 + ]      | $50      | [ Change Qty ] [Remove]|
| Coffee Mug      | $10      | [ - 3 + ]      | $30      | [ Change Qty ] [Remove]|
-------------------------------------------------------------------------------------------------
                                          Total:      $1320
-------------------------------------------------------------------------------------------------

[ Proceed to Checkout ]
-------------------------------------------------------------------------------------------------
```

##### 📦 **Checkout Page**

```
-------------------------------------------------------------------------------------------------
Header: [ Logo ]   [ 🛒 Cart ]   [ Profile v ]
-------------------------------------------------------------------------------------------------

Checkout
-------------------------------------------------------------------------------------------------
Shipping Information:
  Name:   [ John Doe              ]
  Street: [ 123 Main St           ]
  City:   [ New York              ]
  Zip:    [ 10001                 ]

-------------------------------------------------------------------------------------------------
Order Summary:
  ---------------------------------------------------------
  Laptop Pro 15    $1200 x 1   = $1200
  Wireless Mouse   $25   x 2   = $50
  Coffee Mug       $10   x 3   = $30
  ---------------------------------------------------------
  Total:     $1320
  ---------------------------------------------------------

-------------------------------------------------------------------------------------------------
Payment Section (Simulated):
  [ ] Cash on Delivery
  [ ] Credit/Debit Card (Mock Input)
  [ ] UPI / Wallet (Simulated)

-------------------------------------------------------------------------------------------------
[ Place Order ]
-------------------------------------------------------------------------------------------------
```

---

## 📜 **3. Order History Page**

```
-------------------------------------------------------------------------------------------------
Header: [ Logo ]   [ 🛒 Cart ]   [ Profile v ]
-------------------------------------------------------------------------------------------------

My Orders
Filter: ( All | Pending | Shipped | Delivered )
-------------------------------------------------------------------------------------------------
| Order ID | Items Summary            | Total  | Status   | Date       | Actions       |
-------------------------------------------------------------------------------------------------
| ORD-1001 | Laptop Pro 15, Mouse     | $1250  | Pending  | 2025-08-10 | [View Details]|
| ORD-1002 | Coffee Mug x3, Lamp      | $70    | Shipped  | 2025-08-05 | [View Details]|
| ORD-1003 | Sneakers, Backpack       | $180   | Delivered| 2025-07-28 | [View Details]|
-------------------------------------------------------------------------------------------------

(View Order Modal - When clicking "View Details")
-----------------------------------------------------------
Order ID: ORD-1001
Date: 2025-08-10
Status: Pending

Items:
- Laptop Pro 15  | $1200 | Qty: 1 | Total: $1200
- Wireless Mouse | $25   | Qty: 2 | Total: $50

-----------------------------------------------------------
Shipping Info:
Name: John Doe
Street: 123 Main St
City: New York
Zip: 10001
-----------------------------------------------------------
Total Amount: $1250
[ Cancel Order ] (only if Pending)
-----------------------------------------------------------
```

##### ✨ This flow covers:
* **Cart** → manage items, quantities, total
* **Checkout** → confirm shipping + payment
* **Orders** → track past orders, view details, cancel pending

### **Optional Modules for Both Apps**

* **Search & Filter Module**: reusable component for product listing/search
* **Analytics Module**: reusable charting components
* **Authentication Module**: reusable login/register forms with JWT/session handling

---

✅ This structure separates **Admin responsibilities** (management & analytics) from **Customer functionalities** (shopping & reviewing) and is React-friendly.

