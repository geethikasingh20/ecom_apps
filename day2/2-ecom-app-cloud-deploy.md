# 🚀 Step-by-Step: Deploy Spring Boot App on **Render** (Free Tier)

## 1. **Prepare Your Spring Boot App**

1. In Eclipse (or VS Code/Eclipse), build your app with Maven:

   ```bash
   mvn clean package
   ```
2. You’ll get a JAR file inside `target/`, e.g. `target/people_app-0.0.1-SNAPSHOT.jar`.

✅ Make sure your app runs locally:

```bash
java -jar target\people_app-0.0.1-SNAPSHOT.jar
```

## Prepare `Dockerfile` to the project root
* filename: Dockerfile
```
# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
```

## 2. **Push Code to GitHub**

Render deploys directly from GitHub.
1. Create a new GitHub repo.
2. Push your Spring Boot project:
   * URL : 
   ```
   https://github.com/<your-username>/<repo-name>.git
   ```

## 3. **Create Render Account**
1. Go to 👉 [https://render.com](https://render.com)
2. Sign up with **Google** or **GitHub** (no credit card needed).

## 4. **Create a New Web Service**
1. In Render Dashboard → **New + → Web Service**.
2. Connect your GitHub repo.
3. Fill in details:
   * **Name**: `ecom_app_server` i.e. `spring-boot-app-name` (or any name).
   * **Region**: pick closest to you.
   * **Branch**: `main`.
   * **Runtime**: **Docker**.

### 4b. **Add Environment Variables**
* On Render, do not use .env files. Instead:
* Go to your Spring Boot service in Render dashboard.
* Add Environment Variables with the same names (`MONGO_URI`).
* **Deploy**:  Spring Boot will read them automatically.
* **Re-Deploy**: Manual Deploy -> Deploy latest commit 

## 5. **Using Render Build & Deploy**
* Render will:
  * Build Docker image (./mvnw clean package -DskipTests)
  * Create runtime image with app.jar
* Expose on Free Tier Web Service (usually `https://<your-app>.onrender.com`)
* Verify Deployment
  * Open the deployed URL → Your Spring Boot app should load 🎉
  * Logs can be viewed in Render Dashboard → Service → Logs
* Render can run Java apps directly.

* You’ll get a public URL like:
```
https://ecom-app-server-xxxx.onrender.com
```

## 6. **Test Your API**
If your Spring Boot app has a REST endpoint like `/products`, visit:
```
https://ecom-app-server-xxxx.onrender.com/products
```

## 7. **(Optional) Free Tier Limits**

* **Free plan = 750 hours/month per service** (enough to run 24/7).
* If unused, service auto-sleeps → wakes up on first request.

✅ Done! You now have a **Spring Boot backend running free on Render**.
You can later connect your **React frontend (on GitHub Pages/Netlify)** to call this backend.
