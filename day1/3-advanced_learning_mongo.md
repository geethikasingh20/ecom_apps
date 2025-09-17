# 🛒 E-Commerce Platform : Mongo DB Advanced Learning by solving Tasks

## 📌 Tasks - Statement

### 1. CRUD Operations

1. Insert **5 users**, **10 products**, and at least **3 orders** with multiple items.
2. Update the stock of a product after an order is placed.
3. Delete all products in a category `"outdated"`.
4. Find all users from a given `city`.
5. Update a user’s email and password.

---

### 2. Aggregation

1. Find the **total amount spent per user** (group by `userId`).
2. Find the **top 3 best-selling products** by quantity ordered.
3. Calculate the **average rating per product** from the `reviews` collection.
4. Find the **total sales per category** from all orders.
5. Find the **users who placed more than 2 orders**.

---

### 3. Transactions

1. Implement a transaction that:

   * Creates a new order for a user.
   * Deducts stock from products included in that order.
   * Rolls back if stock is insufficient.

2. Add a transaction that:

   * Inserts a new review for a product.
   * Updates product’s average rating field.

---

### 4. Indexing

1. Create an index on `email` field of `users` collection to speed up login queries.
2. Create a **compound index** on `{ category, price }` in products for category-based filtering.
3. Create a **text index** on `name` and `description` fields in products to enable search.
4. Create a **2dsphere index** on `users.address` if storing latitude/longitude for delivery optimization.
5. Use `.explain("executionStats")` to compare query performance before and after indexing.

```
```

## 📌 Tasks - Solution

### 1. CRUD Operations

#### 1. **Insert `5 users`, `10 products`, and at least `3 orders` with multiple items**.

##### 1. Insert Users (`users.csv`)

```javascript
use ecom_app_db

db.users.insertMany([
  {_id:1,name:"Alice Johnson",email:"alice@example.com",password:"alice123",street:"123 Main St",city:"New York",zip:"10001",createdAt:ISODate("2024-01-10")},
  {_id:2,name:"Bob Smith",email:"bob@example.com",password:"bob456",street:"456 Oak Ave",city:"Los Angeles",zip:"90001",createdAt:ISODate("2024-02-15")},
  {_id:3,name:"Charlie Brown",email:"charlie@example.com",password:"charlie789",street:"789 Pine Rd",city:"Chicago",zip:"60601",createdAt:ISODate("2024-03-12")},
  {_id:4,name:"David Miller",email:"david@example.com",password:"david321",street:"321 Maple Blvd",city:"Houston",zip:"77001",createdAt:ISODate("2024-04-20")},
  {_id:5,name:"Eva Green",email:"eva@example.com",password:"eva654",street:"654 Cedar Ln",city:"San Francisco",zip:"94101",createdAt:ISODate("2024-05-05")}
])
```

##### 2. Insert Products (`products.csv`)

```javascript
db.products.insertMany([
  { _id: 101, name: "Laptop Pro 15", description: "High performance laptop", category: "Electronics", price: 1200, stock: 50, tags: ["laptop", "tech", "computer"] },
  { _id: 102, name: "Wireless Mouse", description: "Ergonomic wireless mouse", category: "Electronics", price: 25, stock: 200, tags: ["mouse", "accessories"] },
  { _id: 103, name: "Office Chair", description: "Comfortable office chair", category: "Furniture", price: 150, stock: 80, tags: ["chair", "office"] },
  { _id: 104, name: "Coffee Mug", description: "Ceramic coffee mug", category: "Home", price: 10, stock: 500, tags: ["mug", "kitchen"] },
  { _id: 105, name: "Smartphone X", description: "Latest generation smartphone", category: "Electronics", price: 900, stock: 100, tags: ["phone", "tech", "mobile"] },
  { _id: 106, name: "Headphones", description: "Noise cancelling headphones", category: "Electronics", price: 200, stock: 120, tags: ["audio", "music"] },
  { _id: 107, name: "Bookshelf", description: "Wooden bookshelf", category: "Furniture", price: 180, stock: 30, tags: ["wood", "furniture", "storage"] },
  { _id: 108, name: "Table Lamp", description: "LED desk lamp", category: "Home", price: 40, stock: 150, tags: ["lamp", "lighting"] },
  { _id: 109, name: "Backpack", description: "Waterproof backpack", category: "Fashion", price: 60, stock: 75, tags: ["bag", "travel"] },
  { _id: 110, name: "Sneakers", description: "Running sneakers", category: "Fashion", price: 120, stock: 60, tags: ["shoes", "running"] }
])
```

##### 3. Insert Orders (`orders.csv`)

```javascript
db.orders.insertMany([
  {_id:201,userId:1,items:[{productId:101,qty:1,price:1200},{productId:102,qty:2,price:25}],totalAmount:1250,status:"shipped",createdAt:ISODate("2024-06-10")},
  {_id:202,userId:2,items:[{productId:105,qty:1,price:900},{productId:106,qty:1,price:200}],totalAmount:1100,status:"pending",createdAt:ISODate("2024-06-15")},
  {_id:203,userId:3,items:[{productId:103,qty:1,price:150},{productId:104,qty:4,price:10}],totalAmount:190,status:"delivered",createdAt:ISODate("2024-06-18")},
  {_id:204,userId:1,items:[{productId:109,qty:1,price:60}],totalAmount:60,status:"shipped",createdAt:ISODate("2024-07-01")},
  {_id:205,userId:5,items:[{productId:110,qty:2,price:120}],totalAmount:240,status:"delivered",createdAt:ISODate("2024-07-05")}
])
```

##### 4. Insert Reviews (`reviews.csv`)

```javascript
db.reviews.insertMany([
  { _id: 301, productId: 101, userId: 1, rating: 5, comment: "Excellent laptop", createdAt: ISODate("2024-06-12") },
  { _id: 302, productId: 102, userId: 2, rating: 4, comment: "Good mouse but battery drains fast", createdAt: ISODate("2024-06-16") },
  { _id: 303, productId: 105, userId: 3, rating: 5, comment: "Amazing smartphone", createdAt: ISODate("2024-06-20") },
  { _id: 304, productId: 103, userId: 4, rating: 3, comment: "Chair is okay but a bit pricey", createdAt: ISODate("2024-06-22") },
  { _id: 305, productId: 106, userId: 5, rating: 4, comment: "Sound quality is great", createdAt: ISODate("2024-06-25") }
])
```


#### **2. Update the stock of a product after an order is placed**

👉 Suppose order reduces stock by `qty` of the product. Example: reduce stock of product **101 by 1**

```javascript
db.products.updateOne(
  { _id: 101 },
  { $inc: { stock: -1 } }
)
```


#### **3. Delete all products in a category `"outdated"`**

```javascript
db.products.deleteMany({ category: "outdated" })
```


#### **4. Find all users from a given `city`**

👉 Example: find all users from `"New York"`

```javascript
db.users.find({ city: "New York" })
```


#### **5. Update a user’s email and password**

👉 Example: update user with `_id: 2`

```javascript
db.users.updateOne(
  { _id: 2 },
  { $set: { email: "newbob@example.com", password: "newPass456" } }
)
```



### 2. Aggregation


#### **1. Find the total amount spent per user (group by `userId`)**

```javascript
db.orders.aggregate([
  { $group: { _id: "$userId", totalSpent: { $sum: "$totalAmount" } } }
])
```

---

#### **2. Find the top 3 best-selling products by quantity ordered**

```javascript
db.orders.aggregate([
  { $unwind: "$items" },
  { $group: { _id: "$items.productId", totalQty: { $sum: "$items.qty" } } },
  { $sort: { totalQty: -1 } },
  { $limit: 3 }
])
```


#### **3. Calculate the average rating per product from the `reviews` collection**

```javascript
db.reviews.aggregate([
  { $group: { _id: "$productId", avgRating: { $avg: "$rating" } } }
])
```


#### **4. Find the total sales per category from all orders**

👉 Need to join **orders → products**

```javascript
db.orders.aggregate([
  { $unwind: "$items" },
  {
    $lookup: {
      from: "products",
      localField: "items.productId",
      foreignField: "_id",
      as: "productDetails"
    }
  },
  { $unwind: "$productDetails" },
  {
    $group: {
      _id: "$productDetails.category",
      totalSales: { $sum: { $multiply: ["$items.qty", "$items.price"] } }
    }
  }
])
```

#### **5. Find the users who placed more than 2 orders**

```javascript
db.orders.aggregate([
  { $group: { _id: "$userId", orderCount: { $sum: 1 } } },
  { $match: { orderCount: { $gt: 2 } } }
])
```


---

### 3. Transactions


#### **1. Transaction – Create Order + Deduct Stock + Rollback if Insufficient**

```javascript
const session = await client.startSession();

try {
  await session.withTransaction(async () => {
    const order = {
      _id: 206,
      userId: 2,
      items: [
        { productId: 101, qty: 2, price: 1200 },
        { productId: 102, qty: 1, price: 25 }
      ],
      totalAmount: 2425,
      status: "pending",
      createdAt: new Date()
    };

    // Check stock first
    for (let item of order.items) {
      const product = await db.collection("products").findOne(
        { _id: item.productId },
        { session }
      );
      if (!product || product.stock < item.qty) {
        throw new Error(`Insufficient stock for product ${item.productId}`);
      }
    }

    // Insert order
    await db.collection("orders").insertOne(order, { session });

    // Deduct stock
    for (let item of order.items) {
      await db.collection("products").updateOne(
        { _id: item.productId },
        { $inc: { stock: -item.qty } },
        { session }
      );
    }
  });

  console.log("✅ Transaction committed.");
} catch (error) {
  console.error("❌ Transaction aborted: ", error.message);
} finally {
  await session.endSession();
}
```


#### **2. Transaction – Insert Review + Update Product’s Average Rating**

```javascript
const session = await client.startSession();

try {
  await session.withTransaction(async () => {
    const newReview = {
      _id: 306,
      productId: 101,
      userId: 3,
      rating: 4,
      comment: "Solid laptop, worth the price.",
      createdAt: new Date()
    };

    // Insert review
    await db.collection("reviews").insertOne(newReview, { session });

    // Recalculate average rating
    const result = await db.collection("reviews").aggregate([
      { $match: { productId: newReview.productId } },
      { $group: { _id: "$productId", avgRating: { $avg: "$rating" } } }
    ], { session }).toArray();

    const avgRating = result.length > 0 ? result[0].avgRating : newReview.rating;

    // Update product document
    await db.collection("products").updateOne(
      { _id: newReview.productId },
      { $set: { avgRating: avgRating } },
      { session }
    );
  });

  console.log("✅ Review transaction committed.");
} catch (error) {
  console.error("❌ Review transaction aborted: ", error.message);
} finally {
  await session.endSession();
}
```

---

### 4. Indexing


#### **1. Index on `email` field of users (for login)**

```javascript
db.users.createIndex({ email: 1 }, { unique: true })
```

* `1` → ascending order.
* `unique: true` ensures no duplicate emails.
* Speeds up login queries like:

```javascript
db.users.find({ email: "alice@example.com", password: "alice123" })
```


#### **2. Compound index on `{ category, price }` (products)**

```javascript
db.products.createIndex({ category: 1, price: -1 })
```

* Optimizes queries like:

```javascript
db.products.find({ category: "Electronics" }).sort({ price: -1 })
```


#### **3. Text index on `name` + `description` (products search)**

```javascript
db.products.createIndex({ name: "text", description: "text" })
```

* Enables full-text search:

```javascript
db.products.find({ $text: { $search: "laptop high performance" } })
```


#### **4. 2dsphere index for geospatial queries (user addresses)**

👉 Assuming `users` collection has field:

```json
address: { type: "Point", coordinates: [ -74.006, 40.7128 ] }
```

(Format: `[longitude, latitude]`)

```javascript
db.users.createIndex({ address: "2dsphere" })
```

* Enables geo-queries like:

```javascript
db.users.find({
  address: {
    $near: {
      $geometry: { type: "Point", coordinates: [ -74.0, 40.71 ] },
      $maxDistance: 5000  // within 5km
    }
  }
})
```


### **5. Compare performance with `.explain("executionStats")`**

Before creating index:

```javascript
db.users.find({ email: "alice@example.com" }).explain("executionStats")
```

After creating index:

```javascript
db.users.find({ email: "alice@example.com" }).explain("executionStats")
```

🔍 Compare:

executionStages.totalDocsExamined (should drop drastically).

executionStages.totalKeysExamined (uses index instead).

executionTimeMillis (faster after indexing).

---

# Walk through an **example query plan comparison** using the dataset.
- We’ll use the **`users` collection** with an index on `email`.

---

## **Step 1 – Run query without index**

```javascript
db.users.find({ email: "alice@example.com" }).explain("executionStats")
```

👉 Example output (before index):

```json
{
  "executionStats": {
    "executionTimeMillis": 5,
    "totalDocsExamined": 5,
    "totalKeysExamined": 0,
    "nReturned": 1,
    "executionStages": {
      "stage": "COLLSCAN",   // Collection Scan
      "docsExamined": 5
    }
  }
}
```

* **No index → Full Collection Scan (COLLSCAN)**
* It examined **all 5 documents** before finding Alice.

---

## **Step 2 – Create index on `email`**

```javascript
db.users.createIndex({ email: 1 }, { unique: true })
```

---

## **Step 3 – Run the same query after indexing**

```javascript
db.users.find({ email: "alice@example.com" }).explain("executionStats")
```

👉 Example output (after index):

```json
{
  "executionStats": {
    "executionTimeMillis": 1,
    "totalDocsExamined": 1,
    "totalKeysExamined": 1,
    "nReturned": 1,
    "executionStages": {
      "stage": "IXSCAN",     // Index Scan
      "keysExamined": 1,
      "docsExamined": 1
    }
  }
}
```

* **Now using index → Index Scan (IXSCAN)**
* Examined **only 1 document** (direct lookup).
* Much faster for large collections.

---

## **Summary of Performance Gain**

| Metric                | Before Index | After Index |
| --------------------- | ------------ | ----------- |
| `stage`               | COLLSCAN     | IXSCAN      |
| `totalDocsExamined`   | 5            | 1           |
| `totalKeysExamined`   | 0            | 1           |
| `executionTimeMillis` | \~5ms        | \~1ms       |

---

⚡Now imagine scaling this: with **millions of users**, the difference between **COLLSCAN** vs **IXSCAN** becomes HUGE.
