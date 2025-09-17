# 🛒 E-Commerce Platform : Datasets

## 1. 📂 `users.csv`
```csv
_id,name,email,password,street,city,zip,createdAt
1,Alice Johnson,alice@example.com,alice123,123 Main St,New York,10001,2024-01-10
2,Bob Smith,bob@example.com,bob456,456 Oak Ave,Los Angeles,90001,2024-02-15
3,Charlie Brown,charlie@example.com,charlie789,789 Pine Rd,Chicago,60601,2024-03-12
4,David Miller,david@example.com,david321,321 Maple Blvd,Houston,77001,2024-04-20
5,Eva Green,eva@example.com,eva654,654 Cedar Ln,San Francisco,94101,2024-05-05
```

## 2. 📂 `products.csv`
```csv
_id,name,description,category,price,stock,tags
101,Laptop Pro 15,High performance laptop,Electronics,1200,50,"laptop,tech,computer"
102,Wireless Mouse,Ergonomic wireless mouse,Electronics,25,200,"mouse,accessories"
103,Office Chair,Comfortable office chair,Furniture,150,80,"chair,office"
104,Coffee Mug,Ceramic coffee mug,Home,10,500,"mug,kitchen"
105,Smartphone X,Latest generation smartphone,Electronics,900,100,"phone,tech,mobile"
106,Headphones,Noise cancelling headphones,Electronics,200,120,"audio,music"
107,Bookshelf,Wooden bookshelf,Furniture,180,30,"wood,furniture,storage"
108,Table Lamp,LED desk lamp,Home,40,150,"lamp,lighting"
109,Backpack,Waterproof backpack,Fashion,60,75,"bag,travel"
110,Sneakers,Running sneakers,Fashion,120,60,"shoes,running"
```

## 3. 📂 `orders.csv`
```csv
_id,userId,items,totalAmount,status,createdAt
201,1,"[{""productId"":101,""qty"":1,""price"":1200},{""productId"":102,""qty"":2,""price"":25}]",1250,shipped,2024-06-10
202,2,"[{""productId"":105,""qty"":1,""price"":900},{""productId"":106,""qty"":1,""price"":200}]",1100,pending,2024-06-15
203,3,"[{""productId"":103,""qty"":1,""price"":150},{""productId"":104,""qty"":4,""price"":10}]",190,delivered,2024-06-18
204,1,"[{""productId"":109,""qty"":1,""price"":60}]",60,shipped,2024-07-01
205,5,"[{""productId"":110,""qty"":2,""price"":120}]",240,delivered,2024-07-05
```

## 4. 📂 `reviews.csv`
```csv
_id,productId,userId,rating,comment,createdAt
301,101,1,5,Excellent laptop,2024-06-12
302,102,2,4,Good mouse but battery drains fast,2024-06-16
303,105,3,5,Amazing smartphone,2024-06-20
304,103,4,3,Chair is okay but a bit pricey,2024-06-22
305,106,5,4,Sound quality is great,2024-06-25
```