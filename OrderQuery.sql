SELECT 
    o.id AS OrderID,
    a.username AS CustomerName,
    p.name AS Product,
    od.quantity AS Quantity,
    (od.quantity * p.price) AS TotalPrice,
    o.createAt AS OrderDate
FROM [Order] o
JOIN 
    [Account] a ON o.accountId = a.id
JOIN 
    [OrderDetails] od ON o.id = od.orderId
JOIN 
    [Product] p ON od.productId = p.id
WHERE 
    a.id = 1;


SELECT 
    o.id AS OrderID,
    a.username AS CustomerName,
    p.name AS Product,
    od.quantity AS Quantity,
    (od.quantity * p.price) AS TotalPrice,
    o.createAt AS OrderDate,
FROM 
    [Order] o
JOIN 
    [Account] a ON o.accountId = a.id
JOIN 
    [OrderDetails] od ON o.id = od.orderId
JOIN 
    [Product] p ON od.productId = p.id;