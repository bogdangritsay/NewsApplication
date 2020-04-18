# **_SpringBoot RESTful News Application_** 
Spring boot RESTful додаток, який отримує дані про новини з сайту https://newsapi.org/ (для України https://newsapi.org/v2/top-headlines?country=ua&apiKey=...) та повертає у вибраному форматі (json/xml/docx(word)). 

Mappings:

** новини по дефолту - це новини для України з категорією "health"(здоров'я)"

http://localhost:8989/data/json - новини по дефолту в форматі JSON

http://localhost:8989/data/xml - новини по дефолту в форматі XML

http://localhost:8989/data/word - новини по дефолту, які зберігаються на пристрій клієнта у форматі .docx

Приклад одночасного отримання новин для декількох країн із різними категоріями: 

http://localhost:8989/data/json?country=ua&category=health&country=us&category=business  - параметри (Україна, здоровя) та (США, бізнес) у форматі JSON





Інформація про додаток:

Парсинг результату отриманого від сервіса зроблено у ручному режимі.
Передбачено можливість збереження інформації у форматі MS Word (*.docx) за допомогою Apache POI.

Файл MS Word включає в себе:

•	зображення (якщо URL зобрадення можливо розпізнати в іншому випадку в консоль виводитсья повідомлення про помилку)

•	заголовок новини

•	автор (якщо є)

•	короткий опис

•	посилання на новину

Шаблон файлу розроблено заздалегідь (template.docx)

Передбачено можливість отримання новин декількох категорій та країн одночасно (дані отримуються в різних потоках).

Налаштування додатку (API key, директорія для завантаження, кількість потоків асинхронного завантаження, ...) 
винесені в окремий файл application.properties.

Налаштвано кешування за допомогою Spring Cache.
