
# Book Store API

This README provides instructions on how to run the Book Store API application and test its endpoints. 

## Prequisites

Before running the application, ensure you have the following installed on your system:

 - [Java development kit(JDK)](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)
 


## Setup Instructions

- Create a MySQL database named BookStroeDb.
- Open src/main/resources/application.properties and update the database connection properties:
```
 spring.datasource.url=jdbc:h2:mem:BookStoreDB
 spring.datasource.driverClassName=org.h2.Driver
 spring.datasource.username=sa
 spring.datasource.password=dilshan 
 ```
 ## Dependencies
 - Spring Boot
- Spring Data JPA
- ModelMapper
- Lombok
- h2 database
## Testing Endpoints
Once the application is running, you can test the API endpoints using a tool like Postman or URL. Here are some sample requests:

### User
#### Sign Up user
- Endpoint: POST http://localhost:8080/api/v1/user/signUp
- Request Body:

```
{
    "e_mail": "user@example.com",
    "password": "password123",
    "user_LName": "Dilshan "
    "user_FName": "Thanushka"
}
```
#### Login
- Endpoint: POST http://localhost:8080/api/v1/user/login
- Request Body:
```
{
    "e_mail": "user@example.com",
    "password": "password123"
}
```
### Book
#### Add book
- Endpoint: POST http://localhost:8080/api/v1/book/addBook
- Request Body:
```
{
    "Book_Name": "The Great Gatsby",
    "Book_ID": 12345,
    "Author": "F. Scott Fitzgerald",
    "Price": 19.99,
    "Stock": 100,
    "Details": "Classic American novel"
}
```
#### Update book
- Endpoint: PUT http://localhost:8080/api/v1/book/updateBook
- Request Body:
```
{
    "Book_Name": "The Great Gatsby",
    "Book_ID": 12345,
    "Author": "F. Scott Fitzgerald",
    "Price": 22.99,
    "Stock": 120,
    "Details": "Classic American novel, updated edition"
}
```
#### Get all Books 
- Endpoint: GET http://localhost:8080/api/v1/book/getAllBooks
#### Search Book
- Endpoint: GET http://localhost:8080/api/v1/book/searchBook/{BookName}
- Replace {BookName} with the actual book name.
### Cart Item
#### Add to Cart
- Endpoint: POST /api/v1/cart/addToCart
- Description: Adds an item to the user's cart.
- Request Body:
```
{
  "quantity": 2,
  "book_name": "ExampleBook",
  "e_mail": "user@example.com"
}
```
#### Get All Cart Items
- Endpoint: GET /api/v1/cart/GetCartItem
- Description: Retrieves all items in the user's cart.
#### Delete Cart Item
- Endpoint: DELETE /api/v1/cart/DeleteCartItem/{cartItemId}
- Description: Deletes an item from the cart based on the provided cartItemId.
#### Update Cart Item
- Endpoint: PUT /api/v1/cart/UpdateCartItem
- Description: Updates the quantity of an item in the cart.
- Request Body:
```
{
  "cartItemId": 1,
  "quantity": 3,
  "book_name": "ExampleBook",
  "e_mail": "user@example.com"
}
```
### Order
#### Place Order
- Endpoint: POST /api/v1/order/placeOrder
- Description: Places a new order based on the provided OrderDTO.
- Request Body:
```
{
  "item_name": "ExampleBook",
  "order_date": "2024-02-02",
  "shipping_address": "123 Main St",
  "quantity": 2,
  "e_mail": "user@example.com"
}
